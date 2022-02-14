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
				const result: Saglasnost = parser.parse(res.data);
				console.log(result)
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

	const podnosenjeEvidencijeVakcinacije = (saglasnost: any) => {

		let prethodneVakcineXML = "";

		console.log(pronadjenaSaglasnost)

		if(pronadjenaSaglasnost!["sa:ZdravstveniRadnikSaglasnost"])
			pronadjenaSaglasnost!["sa:ZdravstveniRadnikSaglasnost"]["sa:Obrazac"]["sa:VakcineInfo"].forEach(VakcineInfo => {
				prethodneVakcineXML +=
					`<sa:VakcineInfo>
						<sa:NazivVakcine>${VakcineInfo["sa:NazivVakcine"]}</sa:NazivVakcine>
						<sa:DatumDavanjaVakcine>${VakcineInfo["sa:DatumDavanjaVakcine"]}</sa:DatumDavanjaVakcine>
						<sa:NacinDavanjeVakcine />
						<sa:Ekstremitet>${VakcineInfo["sa:Ekstremitet"]}</sa:Ekstremitet>
						<sa:SerijaVakcine>${VakcineInfo["sa:SerijaVakcine"]}</sa:SerijaVakcine>
						<sa:Proizvodjac>${VakcineInfo["sa:Proizvodjac"]}</sa:Proizvodjac>
						<sa:NezeljanaReakcija>${VakcineInfo["sa:NezeljanaReakcija"]}</sa:NezeljanaReakcija>
					</sa:VakcineInfo>`
			});

		

		let xml = `<?xml version="1.0" encoding="UTF-8"?>
		<sa:Saglasnost
			xmlns:xs="http://www.w3.org/2001/XMLSchema"
			xmlns:sa="http:///www.ftn.uns.ac.rs/vakcinacija/saglasnost"
			xmlns:ct="http:///www.ftn.uns.ac.rs/vakcinacija/commonTypes" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
			xsi:schemaLocation="http:///www.ftn.uns.ac.rs/vakcinacija/saglasnost ../xsd/saglasnost.xsd"
			xmlns:addr="http://www.ftn.uns.ac.rs/rdf/saglasnost"
			xmlns:pred="http://www.ftn.uns.ac.rs/rdf/saglasnost/predicate/"
			vocab="http://www.ftn.uns.ac.rs/rdf/saglasnost/" 
			about="http://www.ftn.uns.ac.rs/rdf/saglasnost/${pronadjenaSaglasnost!["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:Drzavljanstvo"]["sa:RepublikaSrbija"]["sa:JMBG"]}"
			>
			<sa:PacijentSaglasnost>
				<sa:LicneInformacije>
					<sa:Drzavljanstvo>
						<sa:RepublikaSrbija>
							<sa:JMBG property = "pred:jmbg" datatype = "xs:string">${pronadjenaSaglasnost!["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:Drzavljanstvo"]["sa:RepublikaSrbija"]["sa:JMBG"]}</sa:JMBG>
						</sa:RepublikaSrbija>
					</sa:Drzavljanstvo>
					<sa:PunoIme>
						<ct:Ime>${pronadjenaSaglasnost!["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:PunoIme"]["ct:Ime"]}</ct:Ime>
						<ct:Prezime>${pronadjenaSaglasnost!["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:PunoIme"]["ct:Ime"]}</ct:Prezime>
					</sa:PunoIme>
					<sa:ImeRoditelja>${pronadjenaSaglasnost!["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:ImeRoditelja"]}</sa:ImeRoditelja>
					<sa:Pol property = "pred:pol" datatype = "xs:string">${pronadjenaSaglasnost!["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:Pol"]}</sa:Pol>
					<sa:DatumRodjenja>${pronadjenaSaglasnost!["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:DatumRodjenja"]}</sa:DatumRodjenja>
					<sa:MestoRodjenja>${pronadjenaSaglasnost!["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:MestoRodjenja"]}</sa:MestoRodjenja>
					<sa:Adresa property = "pred:adresa" datatype = "xs:string">${pronadjenaSaglasnost!["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:Adresa"]}</sa:Adresa>
					<sa:Mesto property = "pred:mesto" datatype = "xs:string">${pronadjenaSaglasnost!["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:Mesto"]}</sa:Mesto>
					<sa:Opstina>${pronadjenaSaglasnost!["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:Opstina"]}</sa:Opstina>
					<sa:BrojFiksnogTelefona>${pronadjenaSaglasnost!["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:BrojFiksnogTelefona"]}</sa:BrojFiksnogTelefona>
					<sa:BrojMobilnogTelefona>${pronadjenaSaglasnost!["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:BrojMobilnogTelefona"]}</sa:BrojMobilnogTelefona>
					<sa:Email property = "pred:email" datatype = "xs:string">${pronadjenaSaglasnost!["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:Email"]}</sa:Email>
					<sa:RadniStatus>${pronadjenaSaglasnost!["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:RadniStatus"]}</sa:RadniStatus>
				</sa:LicneInformacije>
				<sa:Imunizacija>
					<sa:NazivImunoloskogLeka>${pronadjenaSaglasnost!["sa:PacijentSaglasnost"]["sa:Imunizacija"]["sa:NazivImunoloskogLeka"]}</sa:NazivImunoloskogLeka>
				</sa:Imunizacija>
			</sa:PacijentSaglasnost>
			<sa:ZdravstveniRadnikSaglasnost>
				<sa:ZdravstvenaUstanova>${saglasnost.ZdravstvenaUstanova}</sa:ZdravstvenaUstanova>
				<sa:VakcinacijskiPunkt>${saglasnost.VakcinacijskiPunkt}</sa:VakcinacijskiPunkt>
				<sa:LicneInformacijeLekara>
					<sa:PunoIme>
						<ct:Ime>${saglasnost.ImeLekara}</ct:Ime>
						<ct:Prezime>${saglasnost.PrezimeLekara}</ct:Prezime>
					</sa:PunoIme>
					<sa:BrojTelefona>${saglasnost.BrojTelefonaLekara}</sa:BrojTelefona>
				</sa:LicneInformacijeLekara>
				<sa:Obrazac>

					${prethodneVakcineXML}

					<sa:VakcineInfo>
						<sa:NazivVakcine>${pronadjenaSaglasnost["sa:PacijentSaglasnost"]["sa:Imunizacija"]["sa:NazivImunoloskogLeka"]}</sa:NazivVakcine>
						<sa:DatumDavanjaVakcine>${(new Date()).toISOString()}</sa:DatumDavanjaVakcine>
						<sa:NacinDavanjeVakcine />
						<sa:Ekstremitet>${saglasnost.Ekstremitet}</sa:Ekstremitet>
						<sa:SerijaVakcine>${saglasnost.SerijaVakcine}</sa:SerijaVakcine>
						<sa:Proizvodjac>${pronadjenaSaglasnost["sa:PacijentSaglasnost"]["sa:Imunizacija"]["sa:NazivImunoloskogLeka"]}</sa:Proizvodjac>
						<sa:NezeljanaReakcija>${saglasnost.NezeljanaReakcija}</sa:NezeljanaReakcija>
					</sa:VakcineInfo>

					<sa:PrivremeneKontraindikacije>
						<sa:DatumUtvrdjivanja>${saglasnost.DatumUtvrdjivanja}</sa:DatumUtvrdjivanja>
						<sa:Dijagnoza>${saglasnost.Dijagnoza}</sa:Dijagnoza>
					</sa:PrivremeneKontraindikacije>
					<sa:TrajneKontraindikacije>${saglasnost.TrajneKontraindikacije}</sa:TrajneKontraindikacije>
				</sa:Obrazac>
			</sa:ZdravstveniRadnikSaglasnost>
			<sa:DatumPodnosenja>${pronadjenaSaglasnost!["sa:DatumPodnosenja"]}</sa:DatumPodnosenja>
		</sa:Saglasnost>`;
		axios
			.put("http://localhost:8080/saglasnost/promeniSaglasnost", xml, {
				headers: {
					"Content-Type": "application/xml",
					"Access-Control-Allow-Origin": "*",
				},
			})
			.then((res: any) => {
				toast.success("Uspesno evidentirana vakcinacija", {
					position: toast.POSITION.TOP_CENTER,
					autoClose: false,
					toastId: customId,
				});
				//navigate("/profil");
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
										placeholder=""
										innerRef={register}
									/>
								</FormGroup>
								<FormGroup>
									<Label>Vakcinacijski Punkt</Label>
									<Input
										type="text"
										name="VakcinacijskiPunkt"
										placeholder=""
										innerRef={register}
									/>
								</FormGroup>


								<CardTitle tag="h3">Lekar:</CardTitle>
								<FormGroup>
									<Label>Broj telefona</Label>
									<Input
										type="text"
										name="BrojTelefonaLekara"
										placeholder=""
										innerRef={register}
									/>
								</FormGroup>


								<CardTitle tag="h3">Vakcina:</CardTitle>
								<FormGroup>
									<Label>Ekstremitet</Label>
									<Input type="select" name="Ekstremitet" innerRef={register}>
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
										innerRef={register}
									/>
								</FormGroup>
								<FormGroup>
									<Label>Nezeljana reakcija</Label>
									<Input
										type="text"
										name="NezeljanaReakcija"
										placeholder=""
										innerRef={register}
									/>
								</FormGroup>


								<CardTitle tag="h3">Kontraindikacije:</CardTitle>
								<FormGroup>
									<Label>Datum utvrdjivanja</Label>
									<Input
										type="text"
										name="DatumUtvrdjivanja"
										placeholder=""
										innerRef={register}
									/>
								</FormGroup>
								<FormGroup>
									<Label>Dijagnoza</Label>
									<Input
										type="text"
										name="Dijagnoza"
										placeholder=""
										innerRef={register}
									/>
								</FormGroup>
								<FormGroup>
									<Label>Trajne kontraindikacije</Label>
									<Input
										type="text"
										name="TrajneKontraindikacije"
										placeholder=""
										innerRef={register}
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
