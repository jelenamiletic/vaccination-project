import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { XMLParser } from "fast-xml-parser";
import axios from "axios";
import { ListaZahteva } from "../../Models/ListaZahteva";
import { useEffect, useState } from "react";
import { Zahtev } from "../../Models/Zahtev";
import SluzbenikNavbar from "../../Navbars/SluzbenikNavbar";
import { zahtevSchema } from "./ZahteviSchema";
import {
	Button,
	Card,
	CardBody,
	CardTitle,
	Col,
	Container,
	Form,
	FormFeedback,
	FormGroup,
	Input,
	Label,
	ListGroup,
	ListGroupItem,
	Modal,
	ModalBody,
	ModalFooter,
	ModalHeader,
	Row,
} from "reactstrap";
import "./Zahtevi.css";
import { useForm } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import { Potvrda } from "../../Models/Potvrda";

toast.configure();
const Zahtevi = () => {
	const customId = "zahtevi";

	const [neodobreniZahtevi, setNeodobreniZahtevi] = useState<
		Array<Zahtev> | null | undefined
	>(null);

	const [selektovaniZahtev, setSelectovaniZahtev] = useState<Zahtev | null>(
		null
	);

	const [poslednjaPotvrda, setPoslednjaPotvrda] = useState<Potvrda | null>(
		null
	);

	const [modal, setModal] = useState(false);

	const {
		register,
		handleSubmit,
		formState: { errors },
	} = useForm({
		resolver: yupResolver(zahtevSchema),
		mode: "onChange",
	});

	const toggle = () => setModal(!modal);

	useEffect(() => {
		dobaviSveNeodobreneZahteve();
	}, []);

	const dobaviSveNeodobreneZahteve = () => {
		axios
			.get(`http://localhost:8081/odgovorNaZahtev/dobaviSveNeodobreneZahteve`, {
				headers: {
					"Content-Type": "application/xml",
					"Access-Control-Allow-Origin": "*",
				},
			})
			.then((res: any) => {
				const parser = new XMLParser();
				const result = parser.parse(res.data);
				const lista: ListaZahteva = result["za:ListaZahteva"];
				setNeodobreniZahtevi(lista["za:Zahtev"]);
			})
			.catch((err: any) => {
				toast.error(err.response.data, {
					position: toast.POSITION.TOP_CENTER,
					autoClose: false,
					toastId: customId,
				});
			});
	};

	const prikaziZahtev = (zahtev: Zahtev) => {
		setSelectovaniZahtev(zahtev);
		dobaviPoslednjuPotvrdu(zahtev["za:Podnosilac"]["za:JMBG"]);
	};

	const dobaviPoslednjuPotvrdu = (jmbg: string) => {
		axios
			.get(
				`http://localhost:8081/odgovorNaZahtev/dobaviPoslednjuPotvrduPoJmbg/${jmbg}`,
				{
					headers: {
						"Content-Type": "application/xml;charset=utf-8",
						"Access-Control-Allow-Origin": "*",
					},
				}
			)
			.then((res: any) => {
				const parser = new XMLParser();
				const result = parser.parse(res.data);
				const potvrda: Potvrda = result["po:Potvrda"];
				setPoslednjaPotvrda(potvrda);
			})
			.catch((err: any) => {
				toast.error(err.response.data, {
					position: toast.POSITION.TOP_CENTER,
					autoClose: false,
					toastId: customId,
				});
			});
	};

	const dodajNoviSertifikat = () => {
		let dateNow = new Date(new Date().toString().split("GMT")[0] + " UTC")
			.toISOString()
			.split(".")[0];
		let brojSertifikata = Math.floor(Math.random() * 100000);
		let xml = `<?xml version="1.0" encoding="UTF-8"?>
			<se:Sertifikat
			xmlns:xs="http://www.w3.org/2001/XMLSchema"
			xmlns:se="http:///www.ftn.uns.ac.rs/vakcinacija/sertifikat" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xmlns:ct="http:///www.ftn.uns.ac.rs/vakcinacija/commonTypes"
			xsi:schemaLocation="http:///www.ftn.uns.ac.rs/vakcinacija/sertifikat  ../xsd/sertifikat.xsd"
			xmlns:addr="http://www.ftn.uns.ac.rs/rdf/sertifikat"
			xmlns:pred="http://www.ftn.uns.ac.rs/rdf/sertifikat/predicate/"
			vocab="http://www.ftn.uns.ac.rs/rdf/sertifikat/"
			about="http://www.ftn.uns.ac.rs/rdf/sertifikat/${
				selektovaniZahtev!["za:Podnosilac"]["za:JMBG"]
			}">
			<se:BrojSertifikata>${"SERT-" + brojSertifikata}</se:BrojSertifikata>
			<se:DatumVremeIzdavanja>${dateNow}</se:DatumVremeIzdavanja>
			<se:QR>http://www.ftn.uns.ac.rs/</se:QR>
			<se:LicneInformacije>
				<se:PunoIme>
					<ct:Ime>${selektovaniZahtev!["za:Podnosilac"]["za:PunoIme"]["ct:Ime"]}</ct:Ime>
					<ct:Prezime>${
						selektovaniZahtev!["za:Podnosilac"]["za:PunoIme"]["ct:Prezime"]
					}</ct:Prezime>
				</se:PunoIme>
				<se:DatumRodjenja>${
					selektovaniZahtev!["za:Podnosilac"]["za:DatumRodjenja"]
				}</se:DatumRodjenja>
				<se:Pol>${selektovaniZahtev!["za:Podnosilac"]["za:Pol"]}</se:Pol>
				<se:JMBG property = "pred:jmbg" datatype = "xs:string">${
					selektovaniZahtev!["za:Podnosilac"]["za:JMBG"]
				}</se:JMBG>
				<se:BrojPasosa property = "pred:brojPasosa" datatype = "xs:string">${
					selektovaniZahtev!["za:Podnosilac"]["za:BrojPasosa"]
				}</se:BrojPasosa>
			</se:LicneInformacije>
			<se:Vakcinacija>
				<se:BrojDoze>${
					poslednjaPotvrda!["po:InformacijeOVakcinama"][0]["po:BrojDoze"]
				}</se:BrojDoze>
				<se:TipVakcine property = "pred:tipVakcine" datatype = "xs:string">${
					poslednjaPotvrda!["po:VakcinaPrveDveDoze"]
				}</se:TipVakcine>
				<se:DatumDavanja>${
					poslednjaPotvrda!["po:InformacijeOVakcinama"][0]["po:DatumDavanja"]
				}</se:DatumDavanja>
				<se:Proizvodjac>${poslednjaPotvrda!["po:VakcinaPrveDveDoze"]}</se:Proizvodjac>
				<se:Serija>${
					poslednjaPotvrda!["po:InformacijeOVakcinama"][0]["po:Serija"]
				}</se:Serija>
				<se:ZdravstvenaUstanova>${
					poslednjaPotvrda!["po:ZdravstvenaUstanova"]
				}</se:ZdravstvenaUstanova>
			</se:Vakcinacija>
			<se:Vakcinacija>
				<se:BrojDoze>${
					poslednjaPotvrda!["po:InformacijeOVakcinama"][1]["po:BrojDoze"]
				}</se:BrojDoze>
				<se:TipVakcine property = "pred:tipVakcine" datatype = "xs:string">${
					poslednjaPotvrda!["po:VakcinaPrveDveDoze"]
				}</se:TipVakcine>
				<se:DatumDavanja>${
					poslednjaPotvrda!["po:InformacijeOVakcinama"][1]["po:DatumDavanja"]
				}</se:DatumDavanja>
				<se:Proizvodjac>${poslednjaPotvrda!["po:VakcinaPrveDveDoze"]}</se:Proizvodjac>
				<se:Serija>${
					poslednjaPotvrda!["po:InformacijeOVakcinama"][1]["po:Serija"]
				}</se:Serija>
				<se:ZdravstvenaUstanova>${
					poslednjaPotvrda!["po:ZdravstvenaUstanova"]
				}</se:ZdravstvenaUstanova>
			</se:Vakcinacija>
			<se:Test>
				<se:ImeTesta>SARS-CoV-2 RT</se:ImeTesta>
				<se:VrstaUzorka>Krv</se:VrstaUzorka>
				<se:ProizvodjacTesta>BIONTECH MANUFACTURING GMBH</se:ProizvodjacTesta>
				<se:DatumVremeUzorkovanja>2021-11-02T21:32:52</se:DatumVremeUzorkovanja>
				<se:DatumVremeIzdavanjaRezultata>2021-11-02T21:32:52</se:DatumVremeIzdavanjaRezultata>
				<se:Rezultat property = "pred:rezultat" datatype = "xs:string">Negativan</se:Rezultat>
				<se:Laboratorija>Dom Zdravlja Zvezdara</se:Laboratorija>
			</se:Test>  
		</se:Sertifikat>`;
		axios
			.post("http://localhost:8081/odgovorNaZahtev/dodajNoviSertifikat", xml, {
				headers: {
					"Content-Type": "application/xml",
					"Access-Control-Allow-Origin": "*",
				},
			})
			.then((res: any) => {
				setPoslednjaPotvrda(null);
				odgovoriNaZahtev(null);
			})
			.catch((err: any) => {
				toast.error(err.response.data, {
					position: toast.POSITION.TOP_CENTER,
					autoClose: false,
					toastId: customId,
				});
			});
	};

	const odobriZahtev = () => {
		dodajNoviSertifikat();
	};

	const odgovoriNaZahtev = (odgovorNaZahtev: any) => {
		let xml = "";
		if (odgovorNaZahtev !== null) {
			xml = `<OdgovorNaZahtev>
						<JMBG>${selektovaniZahtev!["za:Podnosilac"]["za:JMBG"]}</JMBG>
						<RazlogOdbijanja>${odgovorNaZahtev.RazlogOdbijanja}</RazlogOdbijanja>
					</OdgovorNaZahtev>`;
		} else {
			xml = `<OdgovorNaZahtev>
						<JMBG>${selektovaniZahtev!["za:Podnosilac"]["za:JMBG"]}</JMBG>
					</OdgovorNaZahtev>`;
		}

		axios
			.put(`http://localhost:8081/odgovorNaZahtev/promeniStatusZahteva`, xml, {
				headers: {
					"Content-Type": "application/xml",
					"Access-Control-Allow-Origin": "*",
				},
			})
			.then((res: any) => {
				if (odgovorNaZahtev !== null) {
					toggle();
				}
				setSelectovaniZahtev(null);
				dobaviSveNeodobreneZahteve();
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
			<div>
				{(neodobreniZahtevi == null || neodobreniZahtevi.length === 0) && (
					<Container style={{ paddingTop: "55px" }}>
						<h1>Trenutno nemaju zahtevi za obradu.</h1>
					</Container>
				)}
				{neodobreniZahtevi != null && neodobreniZahtevi.length > 0 && (
					<Container style={{ paddingTop: "55px" }}>
						<ListGroup>
							{neodobreniZahtevi.map((zahtev: Zahtev) => {
								return (
									<ListGroupItem
										className="list-group-item"
										key={zahtev["za:Podnosilac"]["za:JMBG"]}
										tag="button"
										action
										onClick={() => prikaziZahtev(zahtev)}
									>
										{zahtev["za:Podnosilac"]["za:PunoIme"]["ct:Ime"] +
											" " +
											zahtev["za:Podnosilac"]["za:PunoIme"]["ct:Prezime"]}
									</ListGroupItem>
								);
							})}
						</ListGroup>
					</Container>
				)}
				{selektovaniZahtev !== null && (
					<Card
						className="zahtev-card"
						style={{ backgroundColor: "#DEEDE6", borderColor: "black" }}
					>
						<CardBody>
							<CardTitle tag="h2">Zahtev</CardTitle>
							<Form className="form-login-registracija">
								<FormGroup>
									<Label>Ime i prezime</Label>
									<Input
										value={
											selektovaniZahtev["za:Podnosilac"]["za:PunoIme"][
												"ct:Ime"
											] +
											" " +
											selektovaniZahtev["za:Podnosilac"]["za:PunoIme"][
												"ct:Prezime"
											]
										}
										readOnly
									/>
								</FormGroup>
								<FormGroup>
									<Label>Datum rodjenja</Label>
									<Input
										value={
											selektovaniZahtev["za:Podnosilac"]["za:DatumRodjenja"]
										}
										readOnly
									/>
								</FormGroup>
								<FormGroup>
									<Label>Datum rodjenja</Label>
									<Input
										value={
											selektovaniZahtev["za:Podnosilac"]["za:DatumRodjenja"]
										}
										readOnly
									/>
								</FormGroup>
								<FormGroup>
									<Label>Pol</Label>
									<Input
										value={selektovaniZahtev["za:Podnosilac"]["za:Pol"]}
										readOnly
									/>
								</FormGroup>
								<FormGroup>
									<Label>Jedinstveni maticni broj gradjanina (JMBG)</Label>
									<Input
										value={selektovaniZahtev["za:Podnosilac"]["za:JMBG"]}
										readOnly
									/>
								</FormGroup>
								<FormGroup>
									<Label>Broj pasosa</Label>
									<Input
										value={selektovaniZahtev["za:Podnosilac"]["za:BrojPasosa"]}
										readOnly
									/>
								</FormGroup>
								<FormGroup>
									<Label>Razlog podnosenja</Label>
									<Container className="text-expandable">
										{selektovaniZahtev["za:RazlogPodnosenja"]}
									</Container>
								</FormGroup>
								<Container>
									<Row>
										<Col
											md="6"
											style={{
												paddingTop: "15px",
											}}
										>
											<Button
												color="success"
												type="button"
												onClick={odobriZahtev}
											>
												Odobri zahtev
											</Button>
										</Col>
										<Col
											md="6"
											style={{
												paddingTop: "15px",
											}}
										>
											<Button color="danger" type="button" onClick={toggle}>
												Odbij zahtev
											</Button>
										</Col>
									</Row>
								</Container>
							</Form>
							<Modal isOpen={modal} toggle={toggle}>
								<ModalHeader toggle={toggle}>
									Razlog odbijanja zahteva
								</ModalHeader>
								<ModalBody>
									<Form>
										<FormGroup>
											<Label>Odgovor</Label>
											<Input
												type="textarea"
												name="RazlogOdbijanja"
												invalid={errors.RazlogOdbijanja?.message}
												innerRef={register}
											/>
											<FormFeedback>
												{errors.RazlogOdbijanja?.message}
											</FormFeedback>
										</FormGroup>
									</Form>
								</ModalBody>
								<ModalFooter>
									<Button
										color="success"
										onClick={handleSubmit(odgovoriNaZahtev)}
									>
										Posalji
									</Button>
								</ModalFooter>
							</Modal>
						</CardBody>
					</Card>
				)}
			</div>
		</div>
	);
};

export default Zahtevi;
