import SluzbenikNavbar from "../../Navbars/SluzbenikNavbar";
import DateRangePicker from "react-bootstrap-daterangepicker";
import { Chart, registerables } from "chart.js";
import { Bar } from "react-chartjs-2";
import { Col, Container, Row, Spinner } from "reactstrap";
import axios from "axios";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useState } from "react";
import { XMLParser } from "fast-xml-parser";
import { Izvestaj } from "../../Models/Izvestaj";
Chart.register(...registerables);

toast.configure();
const Izvestaji = () => {
	const customId = "izvestaji";

	const [izvestaj, setIzvestaj] = useState<Izvestaj | null>(null);
	const [podaciPoProizvodjacu, setPodaciPoProizvodjacu] = useState(Object);
	const [podaciRedniBrojDoze, setPodaciRedniBrojDoze] = useState(Object);
	const [activateSpinner, setActivateSpinner] = useState(false);

	const poProizvodjacuLabele = [
		"Pfizer-BioNTech",
		"Moderna",
		"AstraZeneca",
		"Sinopharm",
		"Sputnik V",
	];
	const redniBrojDozeLabele = ["Prva doza", "Druga doza", "Treca doza"];

	const optionsRedniBrojDoze = {
		responsive: true,
		plugins: {
			legend: {
				display: false,
			},
			title: {
				display: true,
				text: "Raspodela po dozama",
			},
		},
		maintainAspectRatio: false,
	};

	const optionsPoProizvodjacu = {
		responsive: true,
		plugins: {
			legend: {
				display: false,
			},
			title: {
				display: true,
				text: "Raspodela po proizvodjacima",
			},
		},
		maintainAspectRatio: false,
	};

	const formirajIzvestaj = (start: any, end: any) => {
		const odDatum = start._d.toLocaleDateString("fr-CA");
		const doDatum = end._d.toLocaleDateString("fr-CA");

		let xml = `<PeriodIzvestaja>
						<OdDatum>${odDatum}</OdDatum>
						<DoDatum>${doDatum}</DoDatum>
					</PeriodIzvestaja>`;
		setActivateSpinner(true);
		axios
			.post("http://localhost:8081/izvestaj/dodajNoviIzvestaj", xml, {
				headers: {
					"Content-Type": "application/xml",
					"Access-Control-Allow-Origin": "*",
				},
			})
			.then((res: any) => {
				const parser = new XMLParser();
				const result = parser.parse(res.data);
				const iz: Izvestaj = result["iz:Izvestaj"];
				setPodaciRedniBrojDoze({
					labels: redniBrojDozeLabele,
					datasets: [
						{
							data: [
								iz["iz:KolicnaDozaPoRednomBroju"][0]["iz:BrojDatihDoza"],
								iz["iz:KolicnaDozaPoRednomBroju"][1]["iz:BrojDatihDoza"],
								iz["iz:KolicnaDozaPoRednomBroju"][2]["iz:BrojDatihDoza"],
							],
							backgroundColor: "rgba(53, 162, 235, 0.5)",
						},
					],
				});
				setPodaciPoProizvodjacu({
					labels: poProizvodjacuLabele,
					datasets: [
						{
							data: [
								iz["iz:RaspodelaDozaPoProizvodjacu"][0]["iz:BrojDatihDoza"],
								iz["iz:RaspodelaDozaPoProizvodjacu"][1]["iz:BrojDatihDoza"],
								iz["iz:RaspodelaDozaPoProizvodjacu"][2]["iz:BrojDatihDoza"],
								iz["iz:RaspodelaDozaPoProizvodjacu"][3]["iz:BrojDatihDoza"],
								iz["iz:RaspodelaDozaPoProizvodjacu"][4]["iz:BrojDatihDoza"],
							],
							backgroundColor: "rgba(255, 99, 132, 0.5)",
						},
					],
				});
				setActivateSpinner(false);
				setIzvestaj(iz);
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
			<Container>
				<Row>
					<Col className="pt-4">
						<DateRangePicker onCallback={formirajIzvestaj}>
							<button className="btn btn-primary">Odaberi period</button>
						</DateRangePicker>
					</Col>
				</Row>
				{activateSpinner && (
					<Row>
						<Col className="pt-4">
							<Spinner
								style={{ width: "3rem", height: "3rem" }}
								color="primary"
							/>
						</Col>
					</Row>
				)}
				{izvestaj !== null && (
					<>
						<Row>
							<Col className="pt-4" style={{ fontWeight: "600" }}>
								Broj podnetih interesovanja:{" "}
								{izvestaj["iz:BrojPodnetihDokumenata"]}
							</Col>
						</Row>
						<Row>
							<Col className="pt-4" style={{ fontWeight: "600" }}>
								Broj podnetih zahteva za digitalni sertifikat:{" "}
								{
									izvestaj["iz:ZahteviZaDigitalniSertifikat"][
										"iz:BrojPrimljenih"
									]
								}
							</Col>
							<Col className="pt-4" style={{ fontWeight: "600" }}>
								Broj izdatih digitalnih sertifikata:{" "}
								{izvestaj["iz:ZahteviZaDigitalniSertifikat"]["iz:BrojIzdatih"]}
							</Col>
						</Row>
						<Row>
							<Col className="pt-4" style={{ fontWeight: "600" }}>
								Ukupan broj datih doza: {izvestaj["iz:UkupanBrojDatihDoza"]}
							</Col>
						</Row>
						<Row>
							<Col className="pt-4" style={{ height: "500px" }}>
								<Bar
									options={optionsRedniBrojDoze}
									data={podaciRedniBrojDoze}
								/>
							</Col>
						</Row>
						<Row>
							<Col className="pt-4" style={{ height: "500px" }}>
								<Bar
									options={optionsPoProizvodjacu}
									data={podaciPoProizvodjacu}
								/>
							</Col>
						</Row>
					</>
				)}
			</Container>
		</div>
	);
};

export default Izvestaji;
