import { yupResolver } from "@hookform/resolvers/yup";
import axios from "axios";
import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
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
} from "reactstrap";
import { InteresovanjeXML } from "../../Models/Interesovanje";
import ZdravstveniRadnikNavbar from "../../Navbars/ZdravstveniRadnikNavbar";
import { XMLParser } from "fast-xml-parser";
import { getJMBG, getEmail } from "../../Auth/AuthService";
import { EvidencijaVakcinacijeSchema } from "./Validation/EvidencijaVakcinacijeSchema";
import { ListaSaglasnosti } from "../../Models/Saglasnost/ListaSaglasnosti";
import { Saglasnost } from "../../Models/Saglasnost/Saglasnost";

const EvidencijaVakcinacije = () => {
	const customId = "EvidencijaVakcinacije";
	const [pronadjenaSaglasnost, setPronadjenaSaglasnost] = useState<Saglasnost | null>(null);

	const navigate = useNavigate();

	useEffect(() => {
	}, [])

	const {
		register,
		handleSubmit,
		formState: { errors },
	} = useForm({
		resolver: yupResolver(EvidencijaVakcinacijeSchema),
		mode: "onChange",
	});

	const pretragaSaglasnosti = (unos: any) => {
		axios.get('http://localhost:8080/saglasnost/pronadjiNajnovijuSaglasnostPoJmbgIliBrPasosa/' + unos.JMBG)
			.then((res: any) => {
				const parser = new XMLParser();
				const result = parser.parse(res.data);
				const saglasnost: Saglasnost = result["sa:Saglasnost"];

				if (saglasnost == null)
					toast.error("Nepostoji saglasnost pacijenta", {
						position: toast.POSITION.TOP_CENTER,
						autoClose: false,
						toastId: customId,
					});

				setPronadjenaSaglasnost(saglasnost);
			}).catch((err: any) => {
				setPronadjenaSaglasnost(null);
			})
	}

	const podnosenjeEvidencijeVakcinacije = (interesovanje: InteresovanjeXML) => {
		let xml = `<in:Interesovanje
						xmlns:xs="http://www.w3.org/2001/XMLSchema"
						xmlns:in="http:///www.ftn.uns.ac.rs/vakcinacija/interesovanje"
						xmlns:ct="http:///www.ftn.uns.ac.rs/vakcinacija/commonTypes" 
						xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
						xmlns:addr="http://www.ftn.uns.ac.rs/rdf/interesovanje"
						xmlns:pred="http://www.ftn.uns.ac.rs/rdf/interesovanje/predicate/"
						xsi:schemaLocation="http:///www.ftn.uns.ac.rs/vakcinacija/interesovanje ../xsd/interesovanje.xsd"
						vocab="http://www.ftn.uns.ac.rs/rdf/interesovanje/" 
						about="http://www.ftn.uns.ac.rs/rdf/interesovanje/${getJMBG()}">
						<in:LicneInformacije>
							<in:Drzavljanstvo  property="pred:drzavljanstvo" datatype="xs:string">Drzavljanin Republike Srbije</in:Drzavljanstvo>
							<in:JMBG property="pred:jmbg" datatype="xs:string">${getJMBG()}</in:JMBG>
							<in:PunoIme>
								<ct:Ime></ct:Ime>
								<ct:Prezime></ct:Prezime>
							</in:PunoIme>
							<in:AdresaElektronskePoste property="pred:email" datatype="xs:string">${getEmail()}</in:AdresaElektronskePoste>
							<in:BrojMobilnogTelefona property="pred:brojMobilnogTelefona" datatype="xs:string">${interesovanje.BrojMobilnog
			}</in:BrojMobilnogTelefona>
							<in:BrojFiksnogTelefona property="pred:brojFiksongTelefona" datatype="xs:string">${interesovanje.BrojFiksnog
			}</in:BrojFiksnogTelefona>
						</in:LicneInformacije>
						<in:OpstinaPrimanja property="pred:opstinaPrimanja" datatype="xs:string">${interesovanje.OpstinaPrimanja
			}</in:OpstinaPrimanja>
						<in:Vakcina property="pred:vakcina" datatype="xs:string">${interesovanje.Vakcina
			}</in:Vakcina>
					</in:Interesovanje>`;
		axios
			.post("http://localhost:8080/interesovanje/dodajNovoInteresovanje", xml, {
				headers: {
					"Content-Type": "application/xml",
					"Access-Control-Allow-Origin": "*",
				},
			})
			.then((res: any) => {
				navigate("/profil");
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
			<ZdravstveniRadnikNavbar />

			<Card
				className="card-login-registracija"
				style={{ backgroundColor: "#DEEDE6", borderColor: "black" }}
			>
				<CardBody>
					<Form className="form-login-registracija">
						<FormGroup>
							<Label>JMBG</Label>
							<Input
								type="text"
								name="JMBG"
								placeholder="JMBG"
								invalid={errors.JMBG?.message}
								innerRef={register}
							/>
							<FormFeedback>{errors.JMBG?.message}</FormFeedback>
						</FormGroup>

						<Button
							className="registruj-login-btn"
							color="primary"
							type="button"
							style={{ display: "block" }}
							onClick={handleSubmit(pretragaSaglasnosti)}
						>
							Pretraga
						</Button>
					</Form>
				</CardBody>
			</Card>
			{
				pronadjenaSaglasnost &&
				<div>
					<Card
						className="card-login-registracija"
						style={{ backgroundColor: "#DEEDE6", borderColor: "black" }}
					>
						<CardBody>
							<CardTitle tag="h2">Saglasnost</CardTitle>
							<Label>Ime:</Label>
							<Input
								type="text"
								readOnly
								placeholder={pronadjenaSaglasnost["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:PunoIme"]["ct:Ime"]}
							/>
							<Label>Prezime:</Label>
							<Input
								type="text"
								readOnly
								placeholder={pronadjenaSaglasnost["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:PunoIme"]["ct:Prezime"]}
							/>
							<Label>Datum rodjenja:</Label>
							<Input
								type="text"
								readOnly
								placeholder={pronadjenaSaglasnost["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:DatumRodjenja"]}
							/>
							<Label>Pol:</Label>
							<Input
								type="text"
								readOnly
								placeholder={pronadjenaSaglasnost["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:Pol"]}
							/>
							<Label>Imunoloski lek:</Label>
							<Input
								type="text"
								readOnly
								placeholder={pronadjenaSaglasnost["sa:PacijentSaglasnost"]["sa:Imunizacija"]["sa:NazivImunoloskogLeka"]}
							/>
						</CardBody>
					</Card>

					<Card
						className="card-login-registracija"
						style={{ backgroundColor: "#DEEDE6", borderColor: "black" }}
					>
						<CardBody>
							<CardTitle tag="h2">Evidencija o vakcinaciji</CardTitle>
							<Form className="form-login-registracija">
								<FormGroup>
									<Label>Zdravstvena ustanova</Label>
									<Input
										type="text"
										name="ZdravstvenaUstanova"
										placeholder="Zdravstvena ustanova"
									/>
								</FormGroup>
								<FormGroup>
									<Label>Vakcinacijski Punkt</Label>
									<Input
										type="text"
										name="VakcinacijskiPunkt"
										placeholder=""
									/>
								</FormGroup>


								<CardTitle tag="h3">Lekar:</CardTitle>
								<FormGroup>
									<Label>Ime</Label>
									<Input
										type="text"
										name="ImeLekara"
										placeholder=""
									/>
								</FormGroup>
								<FormGroup>
									<Label>Prezime</Label>
									<Input
										type="text"
										name="PrezimeLekara"
										placeholder=""
									/>
								</FormGroup>
								<FormGroup>
									<Label>Broj telefona</Label>
									<Input
										type="text"
										name="BrojTelefonaLekara"
										placeholder=""
									/>
								</FormGroup>


								<CardTitle tag="h3">Vakcina:</CardTitle>
								<FormGroup>
									<Label>Naziv Vakcine</Label>
									<Input type="select" name="NazivVakcine">
										<option>Pfizer-BioNTech</option>
										<option>Sputnik V (Gamaleya истраживачки центар)</option>
										<option>Sinopharm</option>
										<option>AstraZeneca</option>
										<option>Moderna</option>
									</Input>
								</FormGroup>
								<FormGroup>
									<Label>Datum davanja vakcine</Label>
									<Input
										type="text"
										name="DatumDavanjaVakcine"
										placeholder=""
									/>
								</FormGroup>
								<FormGroup>
									<Label>Extremitet</Label>
									<Input type="select" name="Extremitet">
										<option>DR</option>
										<option>LR</option>
									</Input>
								</FormGroup>
								<FormGroup>
									<Label>Serija vakcine</Label>
									<Input
										type="text"
										name="SerijaVakcine"
										placeholder=""
									/>
								</FormGroup>
								<FormGroup>
									<Label>Proizvodjac vakcine</Label>
									<Input
										type="text"
										name="ProizvodjacVakcine"
										placeholder=""
									/>
								</FormGroup>
								<FormGroup>
									<Label>Nezeljana reakcija</Label>
									<Input
										type="text"
										name="NezeljanaReakcija"
										placeholder=""
									/>
								</FormGroup>


								<CardTitle tag="h3">Kontraindikacije:</CardTitle>
								<FormGroup>
									<Label>Datum utvrdjivanja</Label>
									<Input
										type="text"
										name="DatumUtvrdjivanja"
										placeholder=""
									/>
								</FormGroup>
								<FormGroup>
									<Label>Dijagnoza</Label>
									<Input
										type="text"
										name="Dijagnoza"
										placeholder=""
									/>
								</FormGroup>
								<FormGroup>
									<Label>Trajne kontraindikacije</Label>
									<Input
										type="text"
										name="TrajneKontraindikacije"
										placeholder=""
									/>
								</FormGroup>

								<Button
									className="registruj-login-btn"
									color="primary"
									type="button"
									style={{ display: "block" }}
									onClick={handleSubmit(podnosenjeEvidencijeVakcinacije)}
								>
									Evidentiraj vakcinu
								</Button>
							</Form>
						</CardBody>
					</Card>
				</div>
			}
		</div >
	);
};

export default EvidencijaVakcinacije;
