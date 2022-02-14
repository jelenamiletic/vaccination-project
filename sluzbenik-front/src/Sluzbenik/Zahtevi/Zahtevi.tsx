import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { XMLParser } from "fast-xml-parser";
import axios from "axios";
import { ListaZahteva } from "../../Models/ListaZahteva";
import { useEffect, useState } from "react";
import { Zahtev } from "../../Models/Zahtev";
import SluzbenikNavbar from "../../Navbars/SluzbenikNavbar";
import {
	Card,
	CardBody,
	CardTitle,
	Container,
	Form,
	FormGroup,
	Input,
	Label,
	ListGroup,
	ListGroupItem,
} from "reactstrap";
import "./Zahtevi.css";

toast.configure();
const Zahtevi = () => {
	const customId = "zahtevi";

	const [neodobreniZahtevi, setNeodobreniZahtevi] =
		useState<Array<Zahtev> | null>(null);

	const [selektovaniZahtev, setSelectovaniZahtev] = useState<Zahtev | null>(
		null
	);

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
	};

	return (
		<div>
			<SluzbenikNavbar />
			<div>
				{neodobreniZahtevi !== null && (
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
									<Label>Razlog podnosenja</Label>
									<Container className="text-expandable">
										{selektovaniZahtev["za:RazlogPodnosenja"]}
									</Container>
								</FormGroup>
							</Form>
						</CardBody>
					</Card>
				)}
			</div>
		</div>
	);
};

export default Zahtevi;
