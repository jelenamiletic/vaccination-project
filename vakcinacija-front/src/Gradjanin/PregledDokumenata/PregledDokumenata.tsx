import axios from "axios";
import { useEffect, useState } from "react";
import { XMLParser } from "fast-xml-parser";
import { toast } from "react-toastify";
import {
	Card,
	CardBody,
	CardTitle,
	Dropdown,
	DropdownToggle,
	DropdownMenu,
	DropdownItem,
} from "reactstrap";
import "rsuite-table/dist/css/rsuite-table.css";
import { Table, Column, HeaderCell, Cell } from "rsuite-table";
import GradjaninNavbar from "../../Navbars/GradjaninNavbar";
import { getJMBG } from "../../Auth/AuthService";
import "draft-js/dist/Draft.css";
import { saveAs } from "file-saver";
import { ZahtevXML } from "../../Models/Zahtev";
import { SertifikatXML } from "../../Models/Sertifikat";
import { Saglasnost } from "../../Models/Saglasnost/Saglasnost";
import { InteresovanjeXML } from "../../Models/Interesovanje";
import "./PregledDokumenata.css";

const PregledDokumenata = () => {
	const customId = "pregled-dokumenata";

	const [vecIma, setVecIma] = useState(false);
	const [documentType, setDocumentType] = useState(null);
	const [documents, setDocuments] = useState<
		Array<ZahtevXML | SertifikatXML | Saglasnost | InteresovanjeXML>
	>([]);

	const proveriDalIma = (name) => {
		axios
			.get(
				name == "saglasnost"
					? "http://localhost:8080/saglasnost/pronadjiSaglasnostPoJmbgIliBrPasosa/" +
							getJMBG()
					: `http://localhost:8080/${name}/pronadji${name.replace(/^\w/, (c) =>
							c.toUpperCase()
					  )}PoJmbg/` + getJMBG()
			)
			.then((res: any) => {
				setVecIma(true);
			})
			.catch((err: any) => {
				setVecIma(false);
			});
	};

	const [dropdownOpen, setDropdownOpen] = useState(false);
	const toggle = () => setDropdownOpen((prevState) => !prevState);

	const pronadjiSveDokumente = (name) => {
		if (name !== "saglasnost" && name !== "potvrda") {
			axios
				.get(
					`http://localhost:8080/${name}/pronadji${name.replace(/^\w/, (c) =>
						c.toUpperCase()
					)}PoJmbg/` + getJMBG()
				)
				.then((res: any) => {
					const parser = new XMLParser();
					const result = parser.parse(res.data);
					switch (name) {
						case "zahtev":
							let temp1: ZahtevXML = result[Object.keys(result)[1]];
							setDocuments([temp1]);
							break;
						case "interesovanje":
							let temp2: InteresovanjeXML = result[Object.keys(result)[1]];
							setDocuments([temp2]);
							break;
						case "sertifikat":
							let temp3: SertifikatXML = result[Object.keys(result)[1]];
							setDocuments([temp3]);
							break;
					}
				})
				.catch((err: any) => {
					setDocuments([]);
				});
		} else if (name === "saglasnost") {
			//TODO proveri dal je dodjos
			axios
				.get(
					`http://localhost:8080/saglasnost/pronadjiSaglasnostPoJmbgIliBrPasosa/` +
						getJMBG()
				)
				.then((res: any) => {
					const parser = new XMLParser();
					const result = parser.parse(res.data);
					let temp4: Saglasnost = result[Object.keys(result)[1]];
					if (temp4["sa:Saglasnost"].length == null) {
						setDocuments([temp4["sa:Saglasnost"]]);
					} else {
						setDocuments(temp4["sa:Saglasnost"]);
					}
				})
				.catch((err: any) => {
					setDocuments([]);
				});
		} else if (name === "potvrda") {
			axios
				.get(`http://localhost:8080/potvrda/pronadjiPotvrdaPoJmbg/` + getJMBG())
				.then((res: any) => {
					const parser = new XMLParser();
					const result = parser.parse(res.data);
					let temp4 = result[Object.keys(result)[1]];
					if (temp4["po:Potvrda"].length == null) {
						setDocuments([temp4["po:Potvrda"]]);
					} else {
						setDocuments(temp4["po:Potvrda"]);
					}
				})
				.catch((err: any) => {
					setDocuments([]);
				});
		}
	};

	const handleChange = (event) => {
		let name = event.target.name;
		proveriDalIma(name);
		setDocumentType(name);
		pronadjiSveDokumente(name);
	};

	useEffect(() => {
		vecIma && pronadjiSveDokumente(documentType);
	}, []);

	const downloadXHTML = (document, name) => {
		console.log(document);
		let putanja = "";
		if (
			name === "sertifikat" ||
			name === "zahtev" ||
			name === "interesovanje"
		) {
			putanja = `http://localhost:8080/${name}/generisiXhtml/${getJMBG()}`;
		} else if (name === "saglasnost") {
			let id = "";
			if (
				document["sa:PacijentSaglasnost"]["sa:LicneInformacije"][
					"sa:Drzavljanstvo"
				]["sa:RepublikaSrbija"] == null
			) {
				id =
					document["sa:PacijentSaglasnost"]["sa:LicneInformacije"][
						"sa:Drzavljanstvo"
					]["sa:StranoDrzavljanstvo"]["sa:BrojPasosa"];
			} else {
				id =
					document["sa:PacijentSaglasnost"]["sa:LicneInformacije"][
						"sa:Drzavljanstvo"
					]["sa:RepublikaSrbija"]["sa:JMBG"];
			}

			let brojDoze = 0;
			if (document["sa:ZdravstveniRadnikSaglasnost"] != null) {
				if (
					Array.isArray(
						document["sa:ZdravstveniRadnikSaglasnost"]["sa:Obrazac"][
							"sa:VakcineInfo"
						]
					)
				) {
					brojDoze =
						document["sa:ZdravstveniRadnikSaglasnost"]["sa:Obrazac"][
							"sa:VakcineInfo"
						].length;
				} else {
					brojDoze = 1;
				}
			}
			putanja = `http://localhost:8080/${name}/generisiXhtml/${id}/${brojDoze}`;
		} else if (name === "potvrda") {
			let brojDoze = 0;
			if (Array.isArray(document["po:InformacijeOVakcinama"])) {
				brojDoze = document["po:InformacijeOVakcinama"].length;
			} else {
				brojDoze = 1;
			}
			putanja = `http://localhost:8080/${name}/generisiXhtml/${getJMBG()}/${brojDoze}`;
		}
		axios
			.get(putanja, {
				headers: {
					"Access-Control-Allow-Origin": "*",
				},
				responseType: "blob",
			})
			.then((res: any) => {
				let blob = new Blob([res.data], {
					type: "text/html;charset=utf-8",
				});
				saveAs(blob, getJMBG());
			})
			.catch((err: any) => {
				toast.error(err.response.data, {
					position: toast.POSITION.TOP_CENTER,
					autoClose: false,
					toastId: customId,
				});
			});
	};

	const downloadPDF = (document, name) => {
		let putanja = "";
		if (
			name === "sertifikat" ||
			name === "zahtev" ||
			name === "interesovanje"
		) {
			putanja = `http://localhost:8080/${name}/generisiPdf/${getJMBG()}`;
		} else if (name === "saglasnost") {
			let id = "";
			if (
				document["sa:PacijentSaglasnost"]["sa:LicneInformacije"][
					"sa:Drzavljanstvo"
				]["sa:RepublikaSrbija"] == null
			) {
				id =
					document["sa:PacijentSaglasnost"]["sa:LicneInformacije"][
						"sa:Drzavljanstvo"
					]["sa:StranoDrzavljanstvo"]["sa:BrojPasosa"];
			} else {
				id =
					document["sa:PacijentSaglasnost"]["sa:LicneInformacije"][
						"sa:Drzavljanstvo"
					]["sa:RepublikaSrbija"]["sa:JMBG"];
			}

			let brojDoze = 0;
			if (document["sa:ZdravstveniRadnikSaglasnost"] != null) {
				if (
					Array.isArray(
						document["sa:ZdravstveniRadnikSaglasnost"]["sa:Obrazac"][
							"sa:VakcineInfo"
						]
					)
				) {
					brojDoze =
						document["sa:ZdravstveniRadnikSaglasnost"]["sa:Obrazac"][
							"sa:VakcineInfo"
						].length;
				} else {
					brojDoze = 1;
				}
			}
			putanja = `http://localhost:8080/${name}/generisiPdf/${id}/${brojDoze}`;
		} else if (name === "potvrda") {
			let brojDoze = 0;
			if (Array.isArray(document["po:InformacijeOVakcinama"])) {
				brojDoze = document["po:InformacijeOVakcinama"].length;
			} else {
				brojDoze = 1;
			}
			putanja = `http://localhost:8080/${name}/generisiPdf/${getJMBG()}/${brojDoze}`;
		}
		axios
			.get(putanja, {
				headers: {
					"Access-Control-Allow-Origin": "*",
				},
				responseType: "arraybuffer",
			})
			.then((res: any) => {
				let blob = new Blob([res.data], {
					type: "application/pdf",
				});
				saveAs(blob, getJMBG());
			})
			.catch((err: any) => {
				toast.error(err.response.data, {
					position: toast.POSITION.TOP_CENTER,
					autoClose: false,
					toastId: customId,
				});
			});
	};

	const preuzmiRDFMetapodatke = (dokument: any, name) => {
		let putanja = "";
		let nazivFajla = "";
		if (name === "saglasnost") {
			let id = "";
			if (
				dokument["sa:PacijentSaglasnost"]["sa:LicneInformacije"][
					"sa:Drzavljanstvo"
				]["sa:RepublikaSrbija"] == null
			) {
				id =
					dokument["sa:PacijentSaglasnost"]["sa:LicneInformacije"][
						"sa:Drzavljanstvo"
					]["sa:StranoDrzavljanstvo"]["sa:BrojPasosa"];
			} else {
				id =
					dokument["sa:PacijentSaglasnost"]["sa:LicneInformacije"][
						"sa:Drzavljanstvo"
					]["sa:RepublikaSrbija"]["sa:JMBG"];
			}
			let brojDoze = Array.isArray(
				dokument["sa:ZdravstveniRadnikSaglasnost"]["sa:Obrazac"][
					"sa:VakcineInfo"
				]
			)
				? dokument["sa:ZdravstveniRadnikSaglasnost"]["sa:Obrazac"][
						"sa:VakcineInfo"
				  ].length
				: 1;
			putanja = `http://localhost:8080/${name}/nabaviMetaPodatkeRDFPoId/${id}/${brojDoze}`;
			nazivFajla = "RDF_Saglasnost" + id + "_" + brojDoze + ".rdf";
		} else if (name === "potvrda") {
			let jmbg = dokument["po:LicneInformacije"]["po:JMBG"];
			let brojDoze = Array.isArray(dokument["po:InformacijeOVakcinama"])
				? dokument["po:InformacijeOVakcinama"][
						dokument["po:InformacijeOVakcinama"].length - 1
				  ]["po:BrojDoze"]
				: dokument["po:InformacijeOVakcinama"]["po:BrojDoze"];
			putanja = `http://localhost:8080/${name}/nabaviMetaPodatkeRDFPoJmbg/${jmbg}/${brojDoze}`;
			nazivFajla = "RDF_Potvrda" + jmbg + "_" + brojDoze + ".rdf";
		} else if (name === "sertifikat") {
			putanja = `http://localhost:8080/${name}/nabaviMetaPodatkeRDFPoJmbg/${dokument["se:LicneInformacije"]["se:JMBG"]}`;
			nazivFajla =
				"RDF_Sertifikat" + dokument["se:LicneInformacije"]["se:JMBG"] + ".rdf";
		} else if (name === "zahtev") {
			putanja = `http://localhost:8080/${name}/nabaviMetaPodatkeRDFPoJmbg/${dokument["za:Podnosilac"]["za:JMBG"]}`;
			nazivFajla = "RDF_Zahtev" + dokument["za:Podnosilac"]["za:JMBG"] + ".rdf";
		} else if (name === "interesovanje") {
			putanja = `http://localhost:8080/${name}/nabaviMetaPodatkeRDFPoJmbg/${dokument["in:LicneInformacije"]["in:JMBG"]}`;
			nazivFajla =
				"RDF_Interesovanje" +
				dokument["in:LicneInformacije"]["in:JMBG"] +
				".rdf";
		}
		axios
			.get(putanja, {
				headers: {
					"Content-Type": "application/xml",
					"Access-Control-Allow-Origin": "*",
				},
				responseType: "blob",
			})
			.then((res: any) => {
				let blob = new Blob([res.data], {
					type: "application/xml;charset=utf-8",
				});
				saveAs(blob, nazivFajla);
			});
	};

	const preuzmiJSONMetapodatke = (dokument: any, name) => {
		let putanja = "";
		let nazivFajla = "";
		if (name === "saglasnost") {
			let id = "";
			if (
				dokument["sa:PacijentSaglasnost"]["sa:LicneInformacije"][
					"sa:Drzavljanstvo"
				]["sa:RepublikaSrbija"] == null
			) {
				id =
					dokument["sa:PacijentSaglasnost"]["sa:LicneInformacije"][
						"sa:Drzavljanstvo"
					]["sa:StranoDrzavljanstvo"]["sa:BrojPasosa"];
			} else {
				id =
					dokument["sa:PacijentSaglasnost"]["sa:LicneInformacije"][
						"sa:Drzavljanstvo"
					]["sa:RepublikaSrbija"]["sa:JMBG"];
			}
			let brojDoze = Array.isArray(
				dokument["sa:ZdravstveniRadnikSaglasnost"]["sa:Obrazac"][
					"sa:VakcineInfo"
				]
			)
				? dokument["sa:ZdravstveniRadnikSaglasnost"]["sa:Obrazac"][
						"sa:VakcineInfo"
				  ].length
				: 1;
			putanja = `http://localhost:8080/${name}/nabaviMetaPodatkeJSONPoId/${id}/${brojDoze}`;
			nazivFajla = "JSON_Saglasnost" + id + "_" + brojDoze + ".json";
		} else if (name === "potvrda") {
			let jmbg = dokument["po:LicneInformacije"]["po:JMBG"];
			let brojDoze = Array.isArray(dokument["po:InformacijeOVakcinama"])
				? dokument["po:InformacijeOVakcinama"][
						dokument["po:InformacijeOVakcinama"].length - 1
				  ]["po:BrojDoze"]
				: dokument["po:InformacijeOVakcinama"]["po:BrojDoze"];
			putanja = `http://localhost:8080/${name}/nabaviMetaPodatkeJSONPoJmbg/${jmbg}/${brojDoze}`;
			nazivFajla = "JSON_Potvrda" + jmbg + "_" + brojDoze + ".json";
		} else if (name === "sertifikat") {
			putanja = `http://localhost:8080/${name}/nabaviMetaPodatkeJSONPoJmbg/${dokument["se:LicneInformacije"]["se:JMBG"]}`;
			nazivFajla =
				"JSON_Sertifikat" +
				dokument["se:LicneInformacije"]["se:JMBG"] +
				".json";
		} else if (name === "zahtev") {
			putanja = `http://localhost:8080/${name}/nabaviMetaPodatkeJSONPoJmbg/${dokument["za:Podnosilac"]["za:JMBG"]}`;
			nazivFajla =
				"JSON_Zahtev" + dokument["za:Podnosilac"]["za:JMBG"] + ".json";
		} else if (name === "interesovanje") {
			putanja = `http://localhost:8080/${name}/nabaviMetaPodatkeJSONPoJmbg/${dokument["in:LicneInformacije"]["in:JMBG"]}`;
			nazivFajla =
				"JSON_Interesovanje" +
				dokument["in:LicneInformacije"]["in:JMBG"] +
				".json";
		}
		axios
			.get(putanja, {
				headers: {
					"Content-Type": "application/xml",
					"Access-Control-Allow-Origin": "*",
				},
				responseType: "blob",
			})
			.then((res: any) => {
				let blob = new Blob([res.data], {
					type: "application/json;charset=utf-8",
				});
				saveAs(blob, nazivFajla);
			});
	};

	const datumIspis = (): string => {
		if (documentType !== "potvrda" && documentType !== "sertifikat") {
			return `${documentType.toLowerCase().substring(0, 2)}:DatumPodnosenja`;
		} else if (documentType === "potvrda") {
			return `${documentType.toLowerCase().substring(0, 2)}:DatumIzdavanja`;
		} else if (documentType === "sertifikat") {
			return `${documentType
				.toLowerCase()
				.substring(0, 2)}:DatumVremeIzdavanja`;
		}
	};

	return (
		<>
			<GradjaninNavbar />
			<Card
				className="pregled-card"
				style={{ backgroundColor: "#DEEDE6", borderColor: "black" }}
			>
				<CardBody>
					<CardTitle tag="h2">Pregled dokumenata</CardTitle>
					<Dropdown isOpen={dropdownOpen} toggle={toggle}>
						<DropdownToggle caret>Izaberite vrstu dokumenta</DropdownToggle>
						<DropdownMenu>
							<DropdownItem header>Tip dokumenta</DropdownItem>
							<DropdownItem onClick={handleChange} name="zahtev">
								Zahtev
							</DropdownItem>
							<DropdownItem onClick={handleChange} name="sertifikat">
								Sertifikat
							</DropdownItem>
							<DropdownItem onClick={handleChange} name="interesovanje">
								Interesovanje
							</DropdownItem>
							<DropdownItem onClick={handleChange} name="saglasnost">
								Saglasnost
							</DropdownItem>
							<DropdownItem onClick={handleChange} name="potvrda">
								Potvrda
							</DropdownItem>
						</DropdownMenu>
					</Dropdown>
				</CardBody>
				{documents && vecIma && (
					<div>
						<Table height={320} data={documents}>
							<Column width={200} align="center" fixed>
								<HeaderCell>Vrsta dokumenta</HeaderCell>
								<Cell>{documentType}</Cell>
							</Column>

							<Column width={300} fixed>
								<HeaderCell>Datum podnosenja</HeaderCell>
								<Cell dataKey={datumIspis()} />
							</Column>
							<Column width={500} fixed="right">
								<HeaderCell>Download</HeaderCell>
								<Cell dataKey="sa:Saglasnost">
									{(rowData) => {
										return (
											<span>
												<a
													onClick={() => {
														downloadPDF(rowData, documentType);
													}}
												>
													{" "}
													PDF{" "}
												</a>{" "}
												|{" "}
												<a
													onClick={() => {
														downloadXHTML(rowData, documentType);
													}}
												>
													{" "}
													XHTML{" "}
												</a>
												|{" "}
												<a
													onClick={() => {
														preuzmiRDFMetapodatke(rowData, documentType);
													}}
												>
													{" "}
													Metapodaci - RDF{" "}
												</a>
												|{" "}
												<a
													onClick={() => {
														preuzmiJSONMetapodatke(rowData, documentType);
													}}
												>
													{" "}
													Metapodaci - JSON{" "}
												</a>
											</span>
										);
									}}
								</Cell>
							</Column>
						</Table>
					</div>
				)}
			</Card>
		</>
	);
};

export default PregledDokumenata;
