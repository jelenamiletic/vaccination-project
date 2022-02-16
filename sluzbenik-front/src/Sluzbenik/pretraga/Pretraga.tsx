import axios from "axios";
import { Chart, registerables } from "chart.js";
import { XMLParser } from "fast-xml-parser";
import saveAs from "file-saver";
import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import {
	Button,
	Card,
	CardBody,
	CardTitle,
	Form,
	FormFeedback,
	FormGroup,
	Input,
	Label,
	Modal,
	ModalBody,
	ModalFooter,
	ModalHeader,
	Table,
} from "reactstrap";
import { Potvrda } from "../../Models/Potvrda";
import { Saglasnost } from "../../Models/Saglasnost/Saglasnost";
import SluzbenikNavbar from "../../Navbars/SluzbenikNavbar";
import "./Pretraga.css";

Chart.register(...registerables);

enum TipDokumenta {
	Saglasnost = "Saglasnost",
	Potvrda = "Potvrda",
	Sertifikat = "Sertifikat",
}

const tipoviDokumenta: Array<TipDokumenta> = new Array<TipDokumenta>();
tipoviDokumenta.push(TipDokumenta.Saglasnost);
tipoviDokumenta.push(TipDokumenta.Potvrda);
tipoviDokumenta.push(TipDokumenta.Sertifikat);

toast.configure();
const Pretraga = () => {
	const customId = "pretraga";

	const [pronadjenePotvrde, setPronadjenePotvrde] = useState<Array<Potvrda>>();
	const [pronadjeneSaglasnosti, setPronadjeneSaglasnosti] =
		useState<Array<Saglasnost>>();

	const [showModal, setShowModal] = useState(false);
	const [selektovanTipDokumenta, setSelektovanTipDokumenta] = useState(
		TipDokumenta.Saglasnost
	);
	const [selektovanaSaglasnost, setSelektovanaSaglasnost] =
		useState<Saglasnost | null>();
	const [selektovanaPotvrda, setSelektovanaPotvrda] =
		useState<Potvrda | null>();

	const handleCloseModal = () => setShowModal(false);
	const handleShowModal = () => {
		setShowModal(true);
	};

	useEffect(() => {}, []);

	const {
		register,
		handleSubmit,
		formState: { errors },
	} = useForm({
		mode: "onChange",
	});

	const toggle = () => setShowModal(!showModal);

	const pretraziKljucneReci = (unos: any) => {
		axios
			.get("http://localhost:8081/pretraga/osnovnaPretraga/" + unos.Pretraga, {
				headers: {
					"Content-Type": "application/xml",
					"Access-Control-Allow-Origin": "*",
				},
			})
			.then((res: any) => {
				const parser = new XMLParser();
				const result = parser.parse(res.data);
				console.log(result);
				if (!Array.isArray(result.Dokumenti.Potvrde["po:Potvrda"]))
					setPronadjenePotvrde([
						result.Dokumenti.Potvrde["po:Potvrda"] as Potvrda,
					]);
				else setPronadjenePotvrde(result.Dokumenti.Potvrde["po:Potvrda"]);

				if (!Array.isArray(result.Dokumenti.Saglasnosti["sa:Saglasnost"])) {
					let Arr: Array<Saglasnost> = new Array<Saglasnost>();
					Arr!.push(result.Dokumenti.Saglasnosti["sa:Saglasnost"]);
					setPronadjeneSaglasnosti(Arr!);
				} else
					setPronadjeneSaglasnosti(
						result.Dokumenti.Saglasnosti["sa:Saglasnost"]
					);
			});
	};

	const pretraziNapredno = (unos: any) => {
		const xml = `<PretragaDokumenata>
                        <TipDokumenta>${unos.TipDokumenta}</TipDokumenta>
                        <Ime>${unos.Ime}</Ime>
                        <Prezime>${unos.Prezime}</Prezime>
                        <JMBG>${unos.JMBG}</JMBG>
                        <Pol>${unos.Pol}</Pol>
                    </PretragaDokumenata>`;

		axios
			.put("http://localhost:8081/pretraga/naprednaPretraga", xml, {
				headers: {
					"Content-Type": "application/xml",
					"Access-Control-Allow-Origin": "*",
				},
			})
			.then((res: any) => {
				const parser = new XMLParser();
				const result = parser.parse(res.data);
				console.log(result);
				if (result.Potvrde) {
					setPronadjenePotvrde(result.Potvrde["po:Potvrda"]);
				}
				if (result.Saglasnosti) {
					setPronadjeneSaglasnosti(result.Saglasnosti["sa:Saglasnost"]);
				}
			});
	};

	const handleTipDokumentaIzmena = (selectedOption: any) => {
		setSelektovanTipDokumenta(selectedOption.target.value);
		console.log(`Option selected:`, selectedOption.target.value);
	};

	const preuzmiRDFMetapodatke = (dokument: any, tip: TipDokumenta) => {
		let putanja = "";
		let nazivFajla = "";
		if (tip === TipDokumenta.Saglasnost) {
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
			putanja = `http://localhost:8081/pretraga/nabaviMetaPodatkeSaglasnostRDFPoId/${id}/${brojDoze}`;
			nazivFajla = "RDF_Saglasnost" + id + "_" + brojDoze + ".rdf";
		} else if (tip === TipDokumenta.Potvrda) {
			let jmbg = dokument["po:LicneInformacije"]["po:JMBG"];
			let brojDoze = Array.isArray(dokument["po:InformacijeOVakcinama"])
				? dokument["po:InformacijeOVakcinama"][
						dokument["po:InformacijeOVakcinama"].length - 1
				  ]["po:BrojDoze"]
				: dokument["po:InformacijeOVakcinama"]["po:BrojDoze"];
			putanja = `http://localhost:8081/pretraga/nabaviMetaPodatkePotvrdaRDFPoJmbg/${jmbg}/${brojDoze}`;
			nazivFajla = "RDF_Potvrda" + jmbg + "_" + brojDoze + ".rdf";
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

	const preuzmiJSONMetapodatke = (dokument: any, tip: TipDokumenta) => {
		let putanja = "";
		let nazivFajla = "";
		if (tip === TipDokumenta.Saglasnost) {
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
			putanja = `http://localhost:8081/pretraga/nabaviMetaPodatkeSaglasnostJSONPoId/${id}/${brojDoze}`;
			nazivFajla = "JSON_Saglasnost" + id + "_" + brojDoze + ".json";
		} else if (tip === TipDokumenta.Potvrda) {
			let jmbg = dokument["po:LicneInformacije"]["po:JMBG"];
			let brojDoze = Array.isArray(dokument["po:InformacijeOVakcinama"])
				? dokument["po:InformacijeOVakcinama"][
						dokument["po:InformacijeOVakcinama"].length - 1
				  ]["po:BrojDoze"]
				: dokument["po:InformacijeOVakcinama"]["po:BrojDoze"];
			putanja = `http://localhost:8081/pretraga/nabaviMetaPodatkePotvrdaJSONPoJmbg/${jmbg}/${brojDoze}`;
			nazivFajla = "JSON_Potvrda" + jmbg + "_" + brojDoze + ".json";
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

	const downloadXHTML = (dokument: any, tip: string) => {
		let putanja = "";
		if (tip === TipDokumenta.Saglasnost) {
			let brojDoze = 0;
			if (
				Array.isArray(
					dokument["sa:ZdravstveniRadnikSaglasnost"]["sa:Obrazac"][
						"sa:VakcineInfo"
					]
				)
			) {
				brojDoze =
					dokument["sa:ZdravstveniRadnikSaglasnost"]["sa:Obrazac"][
						"sa:VakcineInfo"
					].length;
			} else {
				brojDoze = 1;
			}

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

			putanja = `http://localhost:8081/dokumenti/saglasnostGenerisiXhtml/${id}/${brojDoze}`;
		} else if (tip === TipDokumenta.Potvrda) {
			let brojDoze = 0;
			if (Array.isArray(dokument["po:InformacijeOVakcinama"])) {
				brojDoze = dokument["po:InformacijeOVakcinama"].length;
			} else {
				brojDoze = 1;
			}

			putanja = `http://localhost:8081/dokumenti/potvrdaGenerisiXhtml/${dokument["po:LicneInformacije"]["po:JMBG"]}/${brojDoze}`;
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
				saveAs(blob, "SkinutFajl");
			})
			.catch((err: any) => {
				toast.error(err.response.data, {
					position: toast.POSITION.TOP_CENTER,
					autoClose: false,
					toastId: customId,
				});
			});
	};

	const downloadPdf = (dokument: any, tip: string) => {
		let putanja = "";
		if (tip === TipDokumenta.Saglasnost) {
			let brojDoze = 0;
			if (
				Array.isArray(
					dokument["sa:ZdravstveniRadnikSaglasnost"]["sa:Obrazac"][
						"sa:VakcineInfo"
					]
				)
			) {
				brojDoze =
					dokument["sa:ZdravstveniRadnikSaglasnost"]["sa:Obrazac"][
						"sa:VakcineInfo"
					].length;
			} else {
				brojDoze = 1;
			}

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

			putanja = `http://localhost:8081/dokumenti/saglasnostGenerisiPdf/${id}/${brojDoze}`;
		} else if (tip === TipDokumenta.Potvrda) {
			let brojDoze = 0;
			if (Array.isArray(dokument["po:InformacijeOVakcinama"])) {
				brojDoze = dokument["po:InformacijeOVakcinama"].length;
			} else {
				brojDoze = 1;
			}

			putanja = `http://localhost:8081/dokumenti/potvrdaGenerisiPdf/${dokument["po:LicneInformacije"]["po:JMBG"]}/${brojDoze}`;
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
					type: "application/pdf;charset=utf-8",
				});
				saveAs(blob, "SkinutFajl");
			})
			.catch((err: any) => {
				toast.error(err.response.data, {
					position: toast.POSITION.TOP_CENTER,
					autoClose: false,
					toastId: customId,
				});
			});
	};

	return (
		<div>
			<SluzbenikNavbar />
			<Card
				className="card-login-registracija"
				style={{ backgroundColor: "#DEEDE6", borderColor: "black" }}
			>
				<CardBody>
					<Form className="form-login-registracija">
						<CardTitle tag="h2">Pretraga arhive dokumenata</CardTitle>
						<FormGroup>
							<Label>Kljucne reci:</Label>
							<Input
								type="text"
								name="Pretraga"
								placeholder="npr. Moderna"
								innerRef={register}
							/>
							<FormFeedback>{errors.JMBG?.message}</FormFeedback>
						</FormGroup>

						<Button
							className="registruj-login-btn"
							color="primary"
							type="button"
							style={{ display: "block" }}
							onClick={handleSubmit(pretraziKljucneReci)}
						>
							Pretraga
						</Button>
					</Form>
				</CardBody>
			</Card>

			<Card
				className="card-login-registracija"
				style={{ backgroundColor: "#DEEDE6", borderColor: "black" }}
			>
				<CardBody>
					<Form className="form-login-registracija">
						<CardTitle tag="h2">Napredna pretraga</CardTitle>

						<FormGroup>
							<Label>Tip dokumenta:</Label>
							<Input
								type="select"
								name="TipDokumenta"
								onChange={handleTipDokumentaIzmena}
								option={selektovanTipDokumenta}
								options={TipDokumenta}
								innerRef={register}
							>
								{tipoviDokumenta.map((doc) => (
									<option value={doc}>{doc}</option>
								))}
							</Input>
						</FormGroup>

						<Label>Ime:</Label>
						<Input
							type="text"
							placeholder="Ime"
							name="Ime"
							innerRef={register}
						/>
						<Label>Prezime:</Label>
						<Input
							type="text"
							placeholder="Prezime"
							name="Prezime"
							innerRef={register}
						/>
						<Label>JMBG:</Label>
						<Input type="text" placeholder="" name="JMBG" innerRef={register} />
						<Label>Muski</Label>
						<Input
							className="ml-2"
							type="radio"
							name="Pol"
							value="Muski"
							innerRef={register}
							checked
						>
							Muski
						</Input>
						<span className="pl-5">
							<Label>Zenski</Label>
							<Input
								className="ml-2"
								type="radio"
								name="Pol"
								value="Zenski"
								innerRef={register}
								checked
							>
								Zenski
							</Input>
							<span className="pl-5">
								<Label>Nijedan</Label>
								<Input
									className="ml-2"
									type="radio"
									name="Pol"
									value=""
									innerRef={register}
									checked
								>
									Nijedan
								</Input>
							</span>
						</span>
						<Button
							className="registruj-login-btn"
							color="primary"
							type="button"
							style={{ display: "block" }}
							onClick={handleSubmit(pretraziNapredno)}
						>
							Pretraga
						</Button>
					</Form>
				</CardBody>
			</Card>

			<Table bordered className="div-dokumenti">
				<thead>
					<tr>
						<th>Dokument</th>
						<th>Tip Dokumenta</th>
						<th>Pacijent</th>
						<th>Metapodaci - RDF</th>
						<th>Metapodaci - JSON</th>
					</tr>
				</thead>
				<tbody>
					{pronadjenePotvrde &&
						pronadjenePotvrde.map((potvrda: Potvrda) => {
							return (
								<tr>
									<th scope="row">
										<Button
											variant="primary"
											onClick={() => {
												setSelektovanaSaglasnost(null);
												setSelektovanaPotvrda(potvrda);
												setShowModal(true);
											}}
										>
											Otvori
										</Button>
									</th>
									<td>Potvrda o vakcinaciji</td>
									<td>
										{potvrda["po:LicneInformacije"]["po:PunoIme"]["ct:Ime"]}{" "}
										{potvrda["po:LicneInformacije"]["po:PunoIme"]["ct:Prezime"]}
									</td>
									<td>
										<Button
											variant="primary"
											onClick={() =>
												preuzmiRDFMetapodatke(potvrda, TipDokumenta.Potvrda)
											}
										>
											Preuzmi
										</Button>
									</td>
									<td>
										<Button
											variant="primary"
											onClick={() =>
												preuzmiJSONMetapodatke(potvrda, TipDokumenta.Potvrda)
											}
										>
											Preuzmi
										</Button>
									</td>
								</tr>
							);
						})}

					{pronadjeneSaglasnosti &&
						pronadjeneSaglasnosti.map((saglasnost: Saglasnost) => {
							console.log(pronadjeneSaglasnosti);
							return (
								<tr>
									<th scope="row">
										<Button
											variant="primary"
											onClick={() => {
												setSelektovanaPotvrda(null);
												setSelektovanaSaglasnost(saglasnost);
												setShowModal(true);
											}}
										>
											Otvori
										</Button>
									</th>
									<td>Saglasnost za imunizaciju</td>
									<td>
										{
											saglasnost["sa:PacijentSaglasnost"][
												"sa:LicneInformacije"
											]["sa:PunoIme"]["ct:Ime"]
										}{" "}
										{
											saglasnost["sa:PacijentSaglasnost"][
												"sa:LicneInformacije"
											]["sa:PunoIme"]["ct:Prezime"]
										}
									</td>
									<td>
										<Button
											variant="primary"
											onClick={() =>
												preuzmiRDFMetapodatke(
													saglasnost,
													TipDokumenta.Saglasnost
												)
											}
										>
											Preuzmi
										</Button>
									</td>
									<td>
										<Button
											variant="primary"
											onClick={() =>
												preuzmiJSONMetapodatke(
													saglasnost,
													TipDokumenta.Saglasnost
												)
											}
										>
											Preuzmi
										</Button>
									</td>
								</tr>
							);
						})}

					<Modal isOpen={showModal} toggle={toggle}>
						<ModalHeader toggle={toggle}>
							{selektovanaPotvrda && <div>Potvrda o vakcinaciji</div>}
							{selektovanaSaglasnost && <div>Saglasnost za imunizaciju</div>}
						</ModalHeader>
						{selektovanaPotvrda && (
							<div>
								<ModalBody>
									<Label>Ime:</Label>
									<Input
										type="text"
										readOnly
										placeholder={
											selektovanaPotvrda["po:LicneInformacije"]["po:PunoIme"][
												"ct:Ime"
											]
										}
									/>
									<Label>Prezime:</Label>
									<Input
										type="text"
										readOnly
										placeholder={
											selektovanaPotvrda["po:LicneInformacije"]["po:PunoIme"][
												"ct:Prezime"
											]
										}
									/>
									<Label>Pol:</Label>
									<Input
										type="text"
										readOnly
										placeholder={
											selektovanaPotvrda["po:LicneInformacije"]["po:Pol"]
										}
									/>
									<Label>Datum rodjenja:</Label>
									<Input
										type="text"
										readOnly
										placeholder={
											selektovanaPotvrda["po:LicneInformacije"][
												"po:DatumRodjenja"
											]
										}
									/>
									<Label>Datum izdavanja:</Label>
									<Input
										type="text"
										readOnly
										placeholder={selektovanaPotvrda["po:DatumIzdavanja"]}
									/>
									<Label>Vakcina:</Label>
									<Input
										type="text"
										readOnly
										placeholder={selektovanaPotvrda["po:VakcinaPrveDveDoze"]}
									/>
									<br></br>
									<CardTitle tag="h4">Doze:</CardTitle>
									{Array.isArray(
										selektovanaPotvrda["po:InformacijeOVakcinama"]
									) ? (
										selektovanaPotvrda["po:InformacijeOVakcinama"].map(
											(element) => {
												<div>
													<Label>Datum:</Label>
													<Input
														type="text"
														readOnly
														placeholder={element["po:DatumDavanja"]}
													/>
													<Label>Serija:</Label>
													<Input
														type="text"
														readOnly
														placeholder={element["po:Serija"]}
													/>
												</div>;
											}
										)
									) : (
										<div>
											<Label>Datum:</Label>
											<Input
												type="text"
												readOnly
												placeholder={
													selektovanaPotvrda["po:InformacijeOVakcinama"][
														"po:DatumDavanja"
													]
												}
											/>
											<Label>Serija:</Label>
											<Input
												type="text"
												readOnly
												placeholder={
													selektovanaPotvrda["po:InformacijeOVakcinama"][
														"po:Serija"
													]
												}
											/>
										</div>
									)}
								</ModalBody>
								<ModalFooter>
									<Button
										color="primary"
										onClick={() => {
											downloadPdf(selektovanaPotvrda, TipDokumenta.Potvrda);
										}}
									>
										Preuzmi PDF
									</Button>{" "}
									<Button
										color="primary"
										onClick={() => {
											downloadXHTML(selektovanaPotvrda, TipDokumenta.Potvrda);
										}}
									>
										Preuzmi XHTML
									</Button>
								</ModalFooter>
							</div>
						)}

						{selektovanaSaglasnost && (
							<div>
								<ModalBody>
									<CardTitle tag="h4">Pacijent:</CardTitle>
									<Label>Ime:</Label>
									<Input
										type="text"
										readOnly
										placeholder={
											selektovanaSaglasnost["sa:PacijentSaglasnost"][
												"sa:LicneInformacije"
											]["sa:PunoIme"]["ct:Ime"]
										}
									/>
									<Label>Prezime:</Label>
									<Input
										type="text"
										readOnly
										placeholder={
											selektovanaSaglasnost["sa:PacijentSaglasnost"][
												"sa:LicneInformacije"
											]["sa:PunoIme"]["ct:Prezime"]
										}
									/>
									<Label>Pol:</Label>
									<Input
										type="text"
										readOnly
										placeholder={
											selektovanaSaglasnost["sa:PacijentSaglasnost"][
												"sa:LicneInformacije"
											]["sa:Pol"]
										}
									/>
									<Label>JMBG:</Label>
									<Input
										type="text"
										readOnly
										placeholder={selektovanaSaglasnost["sa:PacijentSaglasnost"][
											"sa:LicneInformacije"
										]["sa:Drzavljanstvo"]["sa:RepublikaSrbija"][
											"sa:JMBG"
										].toString()}
									/>
									<Label>Datum rodjenja:</Label>
									<Input
										type="text"
										readOnly
										placeholder={
											selektovanaSaglasnost["sa:PacijentSaglasnost"][
												"sa:LicneInformacije"
											]["sa:DatumRodjenja"]
										}
									/>
									<br></br>
									<CardTitle tag="h4">Zdravstvene informacije:</CardTitle>
									<Label>Zdravstvena ustanova:</Label>
									<Input
										type="text"
										readOnly
										placeholder={
											selektovanaSaglasnost["sa:ZdravstveniRadnikSaglasnost"][
												"sa:ZdravstvenaUstanova"
											]
										}
									/>
									<Label>Vakcinacijski punkt:</Label>
									<Input
										type="text"
										readOnly
										placeholder={
											selektovanaSaglasnost["sa:ZdravstveniRadnikSaglasnost"][
												"sa:VakcinacijskiPunkt"
											]
										}
									/>
									<Label>Naziv imunoloskog leka:</Label>
									<Input
										type="text"
										readOnly
										placeholder={
											selektovanaSaglasnost["sa:PacijentSaglasnost"][
												"sa:Imunizacija"
											]["sa:NazivImunoloskogLeka"]
										}
									/>

									<br></br>
									<CardTitle tag="h4">Lekar:</CardTitle>
									<Label>Ime lekara:</Label>
									<Input
										type="text"
										readOnly
										placeholder={
											selektovanaSaglasnost["sa:ZdravstveniRadnikSaglasnost"][
												"sa:LicneInformacijeLekara"
											]["sa:PunoIme"]["ct:Ime"]
										}
									/>
									<Label>Prezime lekara:</Label>
									<Input
										type="text"
										readOnly
										placeholder={
											selektovanaSaglasnost["sa:ZdravstveniRadnikSaglasnost"][
												"sa:LicneInformacijeLekara"
											]["sa:PunoIme"]["ct:Prezime"]
										}
									/>
								</ModalBody>
								<ModalFooter>
									<Button
										color="primary"
										onClick={() => {
											downloadPdf(
												selektovanaSaglasnost,
												TipDokumenta.Saglasnost
											);
										}}
									>
										Preuzmi PDF
									</Button>{" "}
									<Button
										color="primary"
										onClick={() => {
											downloadXHTML(
												selektovanaSaglasnost,
												TipDokumenta.Saglasnost
											);
										}}
									>
										Preuzmi XHTML
									</Button>
								</ModalFooter>
							</div>
						)}
					</Modal>
				</tbody>
			</Table>
		</div>
	);
};

export default Pretraga;
