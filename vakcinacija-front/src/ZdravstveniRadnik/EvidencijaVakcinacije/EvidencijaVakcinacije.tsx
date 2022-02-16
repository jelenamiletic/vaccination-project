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
import * as yup from "yup";

const EvidencijaVakcinacije = () => {
	const customId = "EvidencijaVakcinacije";
	const [pronadjenaSaglasnost, setPronadjenaSaglasnost] = useState<Saglasnost | null>(null);
	const [starijaSaglasnost, setStarijaSaglasnost] = useState<Saglasnost | null>(null);
	const [jmbgGradjanina, setJmbg] = useState<string>("");

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

	const {
		register: register2,
		formState: { errors: errors2 },
		handleSubmit: handleSubmit2,
	  } = useForm({
		resolver: yupResolver(
			yup.object().shape({
				JMBG: yup
				.string()
				.matches(/^\d+$/, "JMBG mora da sadrzi samo brojevne vrednosti!"),})),
		mode: "onChange",
	  });







	const pretragaSaglasnosti = (unos: any) => {
		axios.get('http://localhost:8080/saglasnost/pronadjiNajnovijuSaglasnostPoJmbgIliBrPasosa/' + unos.JMBG)
			.then((res: any) => {
				const parser = new XMLParser();
				const result: Saglasnost = parser.parse(res.data);

				const saglasnost: Saglasnost = result["sa:Saglasnost"];

				axios.get('http://localhost:8080/gradjanin/getGradjaninPoEmail/' + 
				saglasnost!["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:Email"])
				.then((res: any) => {
					const parser = new XMLParser();
					const result = parser.parse(res.data);
					setJmbg(result["gr:Gradjanin"]["ct:JMBG"]);
				})

				saglasnost["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:BrojFiksnogTelefona"] = saglasnost["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:BrojFiksnogTelefona"].toString();
				saglasnost["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:BrojMobilnogTelefona"] = saglasnost["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:BrojMobilnogTelefona"].toString();

				if (saglasnost["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:BrojFiksnogTelefona"].length < 9) {
					saglasnost["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:BrojFiksnogTelefona"] = saglasnost["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:BrojFiksnogTelefona"].padStart(9, '0');
				}

				if (saglasnost["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:BrojMobilnogTelefona"].length < 10) {
					saglasnost["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:BrojMobilnogTelefona"] = saglasnost["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:BrojMobilnogTelefona"].padStart(10, '0');
				}

				if (saglasnost == null){
					
					toast.error("Nepostoji saglasnost pacijenta", {
						position: toast.POSITION.TOP_CENTER,
						autoClose: false,
						toastId: customId,
					})
					setPronadjenaSaglasnost(null);
				
				} else if(saglasnost["sa:ZdravstveniRadnikSaglasnost"]){
					
					toast.error("Gradjanin je vec primio ovu dozu", {
						position: toast.POSITION.TOP_CENTER,
						autoClose: false,
						toastId: customId,
					})
					setPronadjenaSaglasnost(null);

				} else {
					setPronadjenaSaglasnost(saglasnost);

					axios.get('http://localhost:8080/saglasnost/pronadjiNajnovijuPunuSaglasnostPoJmbgIliBrPasosa/' + unos.JMBG)
					.then((res: any) => {
						const rez: Saglasnost = parser.parse(res.data);
						const s: Saglasnost = rez["sa:Saglasnost"];

						if (saglasnost == null){
							setStarijaSaglasnost(null);
						} else{
							setStarijaSaglasnost(s);
						}

					}).catch((err: any) => {
						setStarijaSaglasnost(null);
					})
				}
			}).catch((err: any) => {
				toast.error("Nepostoji saglasnost pacijenta", {
					position: toast.POSITION.TOP_CENTER,
					autoClose: false,
					toastId: customId,
				})
				setPronadjenaSaglasnost(null);
			})
	}








	const podnosenjeEvidencijeVakcinacije = (saglasnost: any) => {

		let prethodneVakcineXML = "";

		if (starijaSaglasnost && starijaSaglasnost!["sa:ZdravstveniRadnikSaglasnost"]) {
			if (Array.isArray(starijaSaglasnost!["sa:ZdravstveniRadnikSaglasnost"]["sa:Obrazac"]["sa:VakcineInfo"])) {
				starijaSaglasnost!["sa:ZdravstveniRadnikSaglasnost"]["sa:Obrazac"]["sa:VakcineInfo"].forEach(VakcineInfo => {
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
			}
			else {
				prethodneVakcineXML +=
					`<sa:VakcineInfo>
						<sa:NazivVakcine>${starijaSaglasnost!["sa:ZdravstveniRadnikSaglasnost"]["sa:Obrazac"]["sa:VakcineInfo"]["sa:NazivVakcine"]}</sa:NazivVakcine>
						<sa:DatumDavanjaVakcine>${starijaSaglasnost!["sa:ZdravstveniRadnikSaglasnost"]["sa:Obrazac"]["sa:VakcineInfo"]["sa:DatumDavanjaVakcine"]}</sa:DatumDavanjaVakcine>
						<sa:NacinDavanjeVakcine />
						<sa:Ekstremitet>${starijaSaglasnost!["sa:ZdravstveniRadnikSaglasnost"]["sa:Obrazac"]["sa:VakcineInfo"]["sa:Ekstremitet"]}</sa:Ekstremitet>
						<sa:SerijaVakcine>${starijaSaglasnost!["sa:ZdravstveniRadnikSaglasnost"]["sa:Obrazac"]["sa:VakcineInfo"]["sa:SerijaVakcine"]}</sa:SerijaVakcine>
						<sa:Proizvodjac>${starijaSaglasnost!["sa:ZdravstveniRadnikSaglasnost"]["sa:Obrazac"]["sa:VakcineInfo"]["sa:Proizvodjac"]}</sa:Proizvodjac>
						<sa:NezeljanaReakcija>${starijaSaglasnost!["sa:ZdravstveniRadnikSaglasnost"]["sa:Obrazac"]["sa:VakcineInfo"]["sa:NezeljanaReakcija"]}</sa:NezeljanaReakcija>
					</sa:VakcineInfo>`
			}
		}

		let drzavljanstvo = "";
		if(pronadjenaSaglasnost!["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:Drzavljanstvo"]["sa:RepublikaSrbija"]){
			drzavljanstvo = `<sa:Drzavljanstvo>
								<sa:RepublikaSrbija>
									<sa:JMBG property = "pred:jmbg" datatype = "xs:string">${pronadjenaSaglasnost!["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:Drzavljanstvo"]["sa:RepublikaSrbija"]["sa:JMBG"]}</sa:JMBG>
								</sa:RepublikaSrbija>
							</sa:Drzavljanstvo>`
		} else {
			drzavljanstvo = `<sa:Drzavljanstvo>
								<sa:StranoDrzavljanstvo>
									<sa:NazivDrzave>${pronadjenaSaglasnost!["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:Drzavljanstvo"]["sa:StranoDrzavljanstvo"]["sa:NazivDrzave"]}</sa:NazivDrzave>
									<sa:BrojPasosa>${pronadjenaSaglasnost!["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:Drzavljanstvo"]["sa:StranoDrzavljanstvo"]["sa:BrojPasosa"]}</sa:BrojPasosa>
								</sa:StranoDrzavljanstvo>
							</sa:Drzavljanstvo>`
		}


		let xml = `<?xml version="1.0" encoding="UTF-8"?>
		<sa:Saglasnost
			xmlns:xs="http://www.w3.org/2001/XMLSchema"
			xmlns:sa="http:///www.ftn.uns.ac.rs/vakcinacija/saglasnost"
			xmlns:ct="http:///www.ftn.uns.ac.rs/vakcinacija/commonTypes" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
			xsi:schemaLocation="http:///www.ftn.uns.ac.rs/vakcinacija/saglasnost ../xsd/saglasnost.xsd"
			xmlns:addr="http://www.ftn.uns.ac.rs/rdf/saglasnost"
			xmlns:pred="http://www.ftn.uns.ac.rs/rdf/saglasnost/predicate/"
			vocab="http://www.ftn.uns.ac.rs/rdf/saglasnost/"
			>
			<sa:PacijentSaglasnost>
				<sa:LicneInformacije>
					${drzavljanstvo}
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
					<sa:ZanimanjeZaposlenog>${pronadjenaSaglasnost!["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:ZanimanjeZaposlenog"]}</sa:ZanimanjeZaposlenog>
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
			}).then((res: any) => {
				slanjePotvrde(saglasnost.SerijaVakcine, saglasnost.ZdravstvenaUstanova);
			})
			.catch((err: any) => {
				console.log(err)
				toast.error(err.response.data, {
					position: toast.POSITION.TOP_CENTER,
					autoClose: false,
					toastId: customId,
				});
			});
	};










	const slanjePotvrde = (vakcina : any, ustanova : any) => {
		let stareVakcine = ""
		let duzina = 0;
		if(starijaSaglasnost){
			const s = starijaSaglasnost["sa:ZdravstveniRadnikSaglasnost"]["sa:Obrazac"]["sa:VakcineInfo"];
			if(Array.isArray(s)){
				s.forEach( (vakcina, index) =>{
					stareVakcine += `<po:InformacijeOVakcinama>
										<po:BrojDoze>${index}</po:BrojDoze>
										<po:DatumDavanja>${vakcina["sa:DatumDavanjaVakcine"]}</po:DatumDavanja>
										<po:Serija>${vakcina["sa:SerijaVakcine"]}</po:Serija>
									</po:InformacijeOVakcinama>`;
					duzina++;
				})
			} else {
				stareVakcine += `<po:InformacijeOVakcinama>
									<po:BrojDoze>1</po:BrojDoze>
									<po:DatumDavanja>${s["sa:DatumDavanjaVakcine"]}</po:DatumDavanja>
									<po:Serija>${s["sa:SerijaVakcine"]}</po:Serija>
								</po:InformacijeOVakcinama>`;
				duzina++;
			}
		}

		let xml_potvrda = `<?xml version="1.0" encoding="UTF-8"?>
		<po:Potvrda
			xmlns:xs="http://www.w3.org/2001/XMLSchema"
			xmlns:po="http:///www.ftn.uns.ac.rs/vakcinacija/potvrda"
			xmlns:ct="http:///www.ftn.uns.ac.rs/vakcinacija/commonTypes" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
			xsi:schemaLocation="http:///www.ftn.uns.ac.rs/vakcinacija/potvrda ../xsd/potvrda.xsd" 
			xmlns:addr="http://www.ftn.uns.ac.rs/rdf/potvrda"
			xmlns:pred="http://www.ftn.uns.ac.rs/rdf/potvrda/predicate/"
			vocab="http://www.ftn.uns.ac.rs/rdf/potvrda/">
			<po:Sifra></po:Sifra>
			<po:LicneInformacije>
				<po:PunoIme>
					<ct:Ime>${pronadjenaSaglasnost!["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:PunoIme"]["ct:Ime"]}</ct:Ime>
					<ct:Prezime>${pronadjenaSaglasnost!["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:PunoIme"]["ct:Prezime"]}</ct:Prezime>
				</po:PunoIme>
				<po:DatumRodjenja>${pronadjenaSaglasnost!["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:DatumRodjenja"]}</po:DatumRodjenja>
				<po:Pol>${pronadjenaSaglasnost!["sa:PacijentSaglasnost"]["sa:LicneInformacije"]["sa:Pol"]}</po:Pol>
				<po:JMBG property = "pred:jmbg" datatype = "xs:string">${jmbgGradjanina}</po:JMBG>
			</po:LicneInformacije>
			${stareVakcine}
			<po:InformacijeOVakcinama>
				<po:BrojDoze>${duzina + 1}</po:BrojDoze>
				<po:DatumDavanja>${new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('.')[0]}</po:DatumDavanja>
				<po:Serija>${vakcina}</po:Serija>
			</po:InformacijeOVakcinama>
			<po:ZdravstvenaUstanova>${ustanova}</po:ZdravstvenaUstanova>
			<po:VakcinaPrveDveDoze property = "pred:VakcinaPrveDveDoze" datatype = "xs:string">${pronadjenaSaglasnost!["sa:PacijentSaglasnost"]["sa:Imunizacija"]["sa:NazivImunoloskogLeka"]}</po:VakcinaPrveDveDoze>
			<po:DatumIzdavanja>${pronadjenaSaglasnost!["sa:DatumPodnosenja"]}</po:DatumIzdavanja>
			<po:QR>http://www.ftn.uns.ac.rs/</po:QR>
		</po:Potvrda>`;
		console.log(xml_potvrda)
		axios
			.post("http://localhost:8080/potvrda/dodajNovuPotvrdu", xml_potvrda, {
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
				navigate("/profil");
			})
			.catch((err: any) => {
				toast.error(err.response.data, {
					position: toast.POSITION.TOP_CENTER,
					autoClose: false,
					toastId: customId,
				});
			});
	}

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
							<Label>JMBG ili broj pasosa</Label>
							<Input
								type="text"
								name="JMBG"
								placeholder="JMBG"
								invalid={errors2.JMBG?.message}
								innerRef={register2}
							/>
							<FormFeedback>{errors2.JMBG?.message}</FormFeedback>
						</FormGroup>

						<Button
							className="registruj-login-btn"
							color="primary"
							type="button"
							style={{ display: "block" }}
							onClick={handleSubmit2(pretragaSaglasnosti)}
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
										invalid={errors.ZdravstvenaUstanova?.message}
										innerRef={register}
									/>
									<FormFeedback>{errors.ZdravstvenaUstanova?.message}</FormFeedback>
								</FormGroup>
								<FormGroup>
									<Label>Vakcinacijski Punkt</Label>
									<Input
										type="text"
										name="VakcinacijskiPunkt"
										placeholder=""
										invalid={errors.VakcinacijskiPunkt?.message}
										innerRef={register}
									/>
									<FormFeedback>{errors.VakcinacijskiPunkt?.message}</FormFeedback>
								</FormGroup>
								<CardTitle tag="h3">Lekar:</CardTitle>
								<FormGroup>
									<Label>Broj telefona</Label>
									<Input
										type="text"
										name="BrojTelefonaLekara"
										placeholder=""
										invalid={errors.BrojTelefonaLekara?.message}
										innerRef={register}
									/>
									<FormFeedback>{errors.BrojTelefonaLekara?.message}</FormFeedback>
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
										invalid={errors.SerijaVakcine?.message}
										innerRef={register}
									/>
									<FormFeedback>{errors.SerijaVakcine?.message}</FormFeedback>
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
										invalid={errors.DatumUtvrdjivanja?.message}
										innerRef={register}
									/>
									<FormFeedback>{errors.DatumUtvrdjivanja?.message}</FormFeedback>
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
