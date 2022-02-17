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
import GradjaninNavbar from "../../Navbars/GradjaninNavbar";
import { getJMBG, getEmail } from "../../Auth/AuthService";
import { Termin } from "../../Models/Termin";
import { XMLParser } from "fast-xml-parser";
import { saglasnostSchema } from "./Validation/SaglasnostSchema";
import saveAs from "file-saver";
import { Saglasnost } from "../../Models/Saglasnost/Saglasnost";

const SaglasnostGradjanin = () => {
	const customId = "saglasnost";
	const [termin, setTermin] = useState<Termin | null>(null);
	const [pronadjenaSaglasnost, setPronadjenaSaglasnost] = useState<Saglasnost | null>(null);
	const [drzavljanstvo, setDrzavljanstvo] = useState<string>("");
	const [zaposlen, setZaposlen] = useState<string>("Zaposlen");
	const [vecIzvrseno, setIzvrseno] = useState(false);
	const [postoji, setPostoji] = useState(false);

	const navigate = useNavigate();

	useEffect(() => {
		getNajnovijiTermin();
	}, [])

	const {
		register,
		handleSubmit,
		formState: { errors },
	} = useForm({
		resolver: yupResolver(saglasnostSchema),
		mode: "onChange",
	});

	const getNajnovijiTermin = () => {
		axios.get('http://localhost:8080/termin/pronadjiAktuelniTerminPoJmbg/' + getJMBG())
		.then((res: any) => {
			const parser = new XMLParser();
			const result = parser.parse(res.data);
			const t: Termin = result.termin;
			setPostoji(true);
			setTermin(t);
			if(t.popunjenaSaglasnost){
				setIzvrseno(true);
			}else{
				axios.get('http://localhost:8080/saglasnost/pronadjiNajnovijuSaglasnostPoJmbgIliBrPasosa/' + getJMBG())
				.then((res: any) => {
					const parser = new XMLParser();
					const result: Saglasnost = parser.parse(res.data);
					const saglasnost: Saglasnost = result["sa:Saglasnost"];
					if (!saglasnost){
						
						setIzvrseno(false);
					
					} else if(saglasnost!["sa:ZdravstveniRadnikSaglasnost"]){
						
						setPronadjenaSaglasnost(saglasnost);
						setIzvrseno(false);

					} else {

						setPronadjenaSaglasnost(saglasnost);
						setIzvrseno(true);

					}
				}).catch((err: any) => {
					setPronadjenaSaglasnost(null);
				})
			}
		})
		.catch((err: any) => {
			setPostoji(false);
		})

		axios.get('http://localhost:8080/gradjanin/getUlogovaniGradjanin')
		.then((res: any) => {
			const parser = new XMLParser();
			const result = parser.parse(res.data);
			setDrzavljanstvo(result["gr:Gradjanin"]["gr:Drzavljanstvo"]);
		})
	}

	const slanjeSaglasnosti = (saglasnost : any) => {
		let d = ''
		if(drzavljanstvo === 'Drzavljanin Republike Srbije'){
			d = `<sa:RepublikaSrbija>
								<sa:JMBG property = "pred:jmbg" datatype = "xs:string">${getJMBG()}</sa:JMBG>
							 </sa:RepublikaSrbija>`
		}else{
			d = `<sa:StranoDrzavljanstvo>
									<sa:NazivDrzave>${saglasnost.NazivDrzave}</sa:NazivDrzave>
									<sa:BrojPasosa>${saglasnost.BrojPasosa}</sa:BrojPasosa>
							 </sa:StranoDrzavljanstvo>`
		}

		const xml = `<sa:Saglasnost
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
					<sa:Drzavljanstvo>
						${d}
					</sa:Drzavljanstvo>
					<sa:PunoIme>
						<ct:Ime></ct:Ime>
						<ct:Prezime></ct:Prezime>
					</sa:PunoIme>
					<sa:ImeRoditelja>${saglasnost.ImeRoditelja}</sa:ImeRoditelja>
					<sa:Pol property = "pred:pol" datatype = "xs:string">Muski</sa:Pol>
					<sa:DatumRodjenja>${saglasnost.DatumRodjenja}</sa:DatumRodjenja>
					<sa:MestoRodjenja>${saglasnost.MestoRodjenja}</sa:MestoRodjenja>
					<sa:Adresa property = "pred:adresa" datatype = "xs:string">${saglasnost.Adresa}</sa:Adresa>
					<sa:Mesto property = "pred:mesto" datatype = "xs:string">${saglasnost.Mesto}</sa:Mesto>
					<sa:Opstina>${saglasnost.Opstina}</sa:Opstina>
					<sa:BrojFiksnogTelefona>${saglasnost.BrojFiksnog}</sa:BrojFiksnogTelefona>
					<sa:BrojMobilnogTelefona>${saglasnost.BrojMobilnog}</sa:BrojMobilnogTelefona>
					<sa:Email property = "pred:email" datatype = "xs:string">${getEmail()}</sa:Email>
					<sa:RadniStatus>${saglasnost.RadniStatus}</sa:RadniStatus>
					${zaposlen === 'Zaposlen' ? `<sa:ZanimanjeZaposlenog>${saglasnost.ZanimanjeZaposlenog}</sa:ZanimanjeZaposlenog>` : ''}
				</sa:LicneInformacije>
				<sa:Imunizacija>
					<sa:NazivImunoloskogLeka>${termin?.vakcina}</sa:NazivImunoloskogLeka>
				</sa:Imunizacija>
			</sa:PacijentSaglasnost>
			<sa:DatumPodnosenja>${termin?.datum}</sa:DatumPodnosenja> 
		</sa:Saglasnost>`

			axios
			.post("http://localhost:8080/saglasnost/dodajNovuSaglasnost", xml, {
				headers: {
					"Content-Type": "application/xml",
					"Access-Control-Allow-Origin": "*",
				},
			})
			.then((res: any) => {
				toast.success("Uspesno popunjena saglasnost", {
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
			<GradjaninNavbar />
			{
				!vecIzvrseno && postoji && termin &&
				<Card
					className="card-login-registracija"
					style={{ backgroundColor: "#DEEDE6", borderColor: "black" }}
					>
						<CardBody>
							<CardTitle tag="h2">Saglasnost</CardTitle>
							<Form className="form-login-registracija">

								{drzavljanstvo !== 'Drzavljanin Republike Srbije' &&
									<div>
										<FormGroup>
											<Label>Naziv strane drzave</Label>
											<Input
												type="text"
												name="NazivDrzave"
												placeholder="NazivDrzave"
												innerRef={register}
											/>
										</FormGroup>
										
										<FormGroup>
											<Label>Broj pasosa</Label>
											<Input
												type="text"
												name="BrojPasosa"
												innerRef={register}
											/>
										</FormGroup>
									</div>
								}

								<FormGroup>
									<Label>Ime roditelja</Label>
									<Input
										type="text"
										name="ImeRoditelja"
										placeholder="Ime roditelja"
										invalid={errors.ImeRoditelja?.message}
										innerRef={register}
									/>
									<FormFeedback>{errors.ImeRoditelja?.message}</FormFeedback>
								</FormGroup>
								
								<FormGroup>
									<Label>Broj mobilnog telefona</Label>
									<Input
										type="text"
										name="BrojMobilnog"
										placeholder="06xxxxxxxx"
										invalid={errors.BrojMobilnog?.message}
										innerRef={register}
									/>
									<FormFeedback>{errors.BrojMobilnog?.message}</FormFeedback>
								</FormGroup>
								
								<FormGroup>
									<Label>Broj fiksnog telefona</Label>
									<Input
										type="text"
										name="BrojFiksnog"
										placeholder="0xxxxxxxx"
										invalid={errors.BrojFiksnog?.message}
										innerRef={register}
									/>
									<FormFeedback>{errors.BrojFiksnog?.message}</FormFeedback>
								</FormGroup>
								
								<FormGroup>
									<Label>Datum rodjenja</Label>
									<Input
										type="text"
										name="DatumRodjenja"
										placeholder="YYYY-MM-DD"
										invalid={errors.DatumRodjenja?.message}
										innerRef={register}
									/>
									<FormFeedback>{errors.DatumRodjenja?.message}</FormFeedback>
								</FormGroup>

								<FormGroup>
									<Label>Mesto rodjenja</Label>
									<Input
										type="text"
										name="MestoRodjenja"
										placeholder="Mesto rodjenja"
										invalid={errors.MestoRodjenja?.message}
										innerRef={register}
									/>
									<FormFeedback>{errors.MestoRodjenja?.message}</FormFeedback>
								</FormGroup>
								
								<FormGroup>
									<Label>Mesto</Label>
									<Input
										type="text"
										name="Mesto"
										placeholder="Mesto"
										invalid={errors.Mesto?.message}
										innerRef={register}
									/>
									<FormFeedback>{errors.Mesto?.message}</FormFeedback>
								</FormGroup>
								
								<FormGroup>
									<Label>Opstina</Label>
									<Input
										type="text"
										name="Opstina"
										placeholder="Opstina"
										invalid={errors.Opstina?.message}
										innerRef={register}
									/>
									<FormFeedback>{errors.Opstina?.message}</FormFeedback>
								</FormGroup>
								
								<FormGroup>
									<Label>Adresa</Label>
									<Input
										type="text"
										name="Adresa"
										placeholder="Adresa"
										invalid={errors.Adresa?.message}
										innerRef={register}
									/>
									<FormFeedback>{errors.Adresa?.message}</FormFeedback>
								</FormGroup>

								<FormGroup>
									<Label>Radni Status</Label>
									<Input type="select" name="RadniStatus" innerRef={register} onChange={(event) => setZaposlen(event.target.value)}>
										<option value="Zaposlen">Zaposlen</option>
										<option value="Nezaposlen">Nezaposlen</option>
										<option value="Penzioner">Penzioner</option>
										<option value="Ucenik">Ucenik</option>
										<option value="Student">Student</option>
										<option value="Dete">Dete</option>
									</Input>
								</FormGroup>

								{
									zaposlen === 'Zaposlen' &&
									<FormGroup>
										<Label>ZanimanjeZaposlenog</Label>
										<Input type="select" name="ZanimanjeZaposlenog" innerRef={register}>
											<option>Drugo</option>
											<option>Zdravstvena zastita</option>
											<option>Socijalna zastita</option>
											<option>Prosveta</option>
											<option>MUP</option>
											<option>Vojska RS</option>
										</Input>
									</FormGroup>
								}

								<Button
									className="registruj-login-btn"
									color="primary"
									type="button"
									onClick={handleSubmit(slanjeSaglasnosti)}
									>
										Podnosi saglasnost
								</Button>
							</Form>
						</CardBody>
				</Card>
			}
			{
				vecIzvrseno && postoji &&
				<Card
					className="card-login-registracija"
					style={{ backgroundColor: "#DEEDE6", borderColor: "black" }}
					>
						<CardBody>
							<CardTitle tag="h2">Saglasnost</CardTitle>
							<Label style={{ display: "block" }}>Vec ste popunili sve saglasnosti</Label>
							{/* {drzavljanstvo === 'Drzavljanin Republike Srbije' &&
								<div>
									<Button
										className="registruj-login-btn"
										color="primary"
										type="button"
										style={{ marginRight: "1em" }}
										onClick={() => downloadXHTML()}
										>
										Skidanje XHTML
									</Button>
									<Button
										className="registruj-login-btn"
										color="primary"
										type="button"
										onClick={() => downloadPdf()}
									>
									Skidanje PDF
								</Button>
								</div>
							} */}
						</CardBody>
				</Card>
			}
			{
				!postoji &&
				<Card
					className="card-login-registracija"
					style={{ backgroundColor: "#DEEDE6", borderColor: "black" }}
					>
						<CardBody>
							<CardTitle tag="h2" >Saglasnost</CardTitle>
							<Label>Nemate saglasnost, popunite prvo interesovanje</Label>
						</CardBody>
				</Card>
			}
		</div>
	);
};

export default SaglasnostGradjanin;
