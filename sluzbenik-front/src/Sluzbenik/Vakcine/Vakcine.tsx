import axios from "axios";
import { XMLParser } from "fast-xml-parser";
import { useEffect, useState } from "react";
import { ListaVakcina } from "../../Models/ListaVakcina";
import { Vakcina } from "../../Models/Vakcina";
import SluzbenikNavbar from "../../Navbars/SluzbenikNavbar";
import { Table, Column, HeaderCell, Cell } from "rsuite-table";
import { EditCell } from "./Components/EditCell";
import { ActionCell } from "./Components/ActionCell";
import "./Vakcine.css";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "rsuite-table/dist/css/rsuite-table.css";

toast.configure();
const Vakcine = () => {
	const customId = "vakcine";

	const [vakcine, setVakcine] = useState<Array<Vakcina>>([]);

	useEffect(() => {
		dobaviVakcine();
	}, []);

	const dobaviVakcine = () => {
		axios
			.get("http://localhost:8081/vakcina/dobaviSve", {
				headers: {
					"Content-Type": "application/xml",
					"Access-Control-Allow-Origin": "*",
				},
			})
			.then((res: any) => {
				const parser = new XMLParser();
				const result = parser.parse(res.data);
				const lista: ListaVakcina = result["va:ListaVakcina"];
				for (let v of lista["va:Vakcina"]) {
					v.status = "";
				}
				setVakcine(lista["va:Vakcina"]);
			});
	};

	const izmeniKolicnu = (naziv: string, novaKolicina: number) => {
		const nextData = Object.assign([], vakcine);
		const vakcina: Vakcina = nextData.filter(
			(v: Vakcina) => v["va:Naziv"] === naziv
		)[0];
		vakcina["va:Kolicina"] = novaKolicina;
		setVakcine(nextData);
	};

	const izmeniKolicinuDb = (vakcina: Vakcina) => {
		if (vakcina["va:Kolicina"] < 0) {
			toast.error("Kolicina mora biti pozitivan broj!", {
				position: toast.POSITION.TOP_CENTER,
				autoClose: false,
				toastId: customId,
			});
		} else {
			let xml = `<va:Vakcina 
							xmlns:ct="http:///www.ftn.uns.ac.rs/vakcinacija/commonTypes" 
							xmlns:pred="http://ftn.uns.ac.rs/vakcinacija/predicate/" 
							xmlns:xs="http://www.w3.org/2001/XMLSchema" 
							xmlns:va="http:///www.ftn.uns.ac.rs/vakcinacija/vakcina">
							<va:Naziv>${vakcina["va:Naziv"]}</va:Naziv>
							<va:Kolicina>${vakcina["va:Kolicina"]}</va:Kolicina>
					   </va:Vakcina>`;
			axios.put("http://localhost:8081/vakcina/azurirajKolicinu", xml, {
				headers: {
					"Content-Type": "application/xml",
					"Access-Control-Allow-Origin": "*",
				},
			});
		}
	};

	const promeniStanjeCelije = (naziv: string) => {
		const vakcina: Vakcina = vakcine.filter(
			(item) => item["va:Naziv"] === naziv
		)[0];
		if (vakcina.status !== "") {
			vakcina.status = "";
			izmeniKolicinuDb(vakcina);
		} else {
			vakcina.status = "IZMENI";
		}
		setVakcine(Object.assign([], vakcine));
	};

	return (
		<div>
			<SluzbenikNavbar />
			<div className="div-tabela" style={{ backgroundColor: "#DEEDE6" }}>
				<Table
					style={{ backgroundColor: "#DEEDE6" }}
					height={320}
					data={vakcine}
				>
					<Column width={500}>
						<HeaderCell className="tabela-header">Naziv</HeaderCell>
						<Cell dataKey="va:Naziv" />
					</Column>

					<Column width={150}>
						<HeaderCell className="tabela-header">Kolicina</HeaderCell>
						<EditCell dataKey="va:Kolicina" onChange={izmeniKolicnu} />
					</Column>

					<Column width={320}>
						<HeaderCell className="tabela-header">Izmeni kolicinu</HeaderCell>
						<ActionCell dataKey="va:Naziv" onClick={promeniStanjeCelije} />
					</Column>
				</Table>
			</div>
		</div>
	);
};

export default Vakcine;
