import { yupResolver } from "@hookform/resolvers/yup";
import axios from "axios";
import { Chart, registerables } from "chart.js";
import { XMLParser } from "fast-xml-parser";
import saveAs from "file-saver";
import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
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
import { ZahteviZaDigitalniSertifikat } from "../../Models/CommonTypes/Izvestaj/ZahteviZaDigitalniSertifikat";
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
				setPronadjenePotvrde(result.Dokumenti.Potvrde["po:Potvrda"]);
				setPronadjeneSaglasnosti(result.Dokumenti.Saglasnosti["sa:Saglasnost"]);
			});
	};

	const handleTipDokumentaIzmena = (selectedOption: any) => {
		setSelektovanTipDokumenta(selectedOption.target.value);
		console.log(`Option selected:`, selectedOption.target.value);
	};

	const preuzmiRDFPotvrda = (jmbg: string, brojDoze: number) => {
		console.log(brojDoze);
		axios
			.get(
				`http://localhost:8081/pretraga/nabaviMetaPodatkePotvrdaRDFPoJmbg/${jmbg}/${brojDoze}`,
				{
					headers: {
						"Content-Type": "application/xml",
						"Access-Control-Allow-Origin": "*",
					},
					responseType: "blob",
				}
			)
			.then((res: any) => {
				let blob = new Blob([res.data], {
					type: "application/xml;charset=utf-8",
				});
				saveAs(blob, "RDF_" + jmbg + "_" + brojDoze + ".rdf");
			});
	};

	const preuzmiJSONPotvrda = (jmbg: string, brojDoze: number) => {
		console.log(brojDoze);
		axios
			.get(
				`http://localhost:8081/pretraga/nabaviMetaPodatkePotvrdaJSONPoJmbg/${jmbg}/${brojDoze}`,
				{
					headers: {
						"Content-Type": "application/xml",
						"Access-Control-Allow-Origin": "*",
					},
					responseType: "blob",
				}
			)
			.then((res: any) => {
				let blob = new Blob([res.data], {
					type: "application/json;charset=utf-8",
				});
				saveAs(blob, "JSON_" + jmbg + "_" + brojDoze + ".json");
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
										<Button variant="primary" onClick={handleShowModal}>
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
												preuzmiRDFPotvrda(
													potvrda["po:LicneInformacije"]["po:JMBG"],
													Array.isArray(potvrda["po:InformacijeOVakcinama"])
														? (potvrda["po:LicneInformacije"]["po:JMBG"],
														  potvrda["po:InformacijeOVakcinama"][
																["po:InformacijeOVakcinama"].length - 1
														  ]["po:BrojDoze"])
														: potvrda["po:InformacijeOVakcinama"]["po:BrojDoze"]
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
												preuzmiJSONPotvrda(
													potvrda["po:LicneInformacije"]["po:JMBG"],
													Array.isArray(potvrda["po:InformacijeOVakcinama"])
														? (potvrda["po:LicneInformacije"]["po:JMBG"],
														  potvrda["po:InformacijeOVakcinama"][
																["po:InformacijeOVakcinama"].length - 1
														  ]["po:BrojDoze"])
														: potvrda["po:InformacijeOVakcinama"]["po:BrojDoze"]
												)
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
										<Button variant="primary" onClick={handleShowModal}>
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
								</tr>
							);
						})}
				</tbody>
			</Table>

			<Modal isOpen={showModal} toggle={toggle}>
				<ModalHeader toggle={toggle}>Dokument</ModalHeader>
				<ModalBody></ModalBody>
				<ModalFooter>
					<Button color="primary">Preuzmi PDF</Button>{" "}
					<Button color="primary">Preuzmi XHTML</Button>
				</ModalFooter>
			</Modal>
		</div>
	);
};

export default Pretraga;
