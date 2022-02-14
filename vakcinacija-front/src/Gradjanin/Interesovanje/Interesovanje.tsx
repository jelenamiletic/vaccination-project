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
import GradjaninNavbar from "../../Navbars/GradjaninNavbar";
import { interesovanjeSchema } from "./Validation/InteresovanjeSchema";
import { getJMBG, getEmail } from "../../Auth/AuthService";
import { saveAs } from "file-saver";

const Interesovanje = () => {
	const customId = "interesovanje";
	const [davalacValue, setDavalacValue] = useState(false);
	const [vecIma, setVecIma] = useState(false);

	const navigate = useNavigate();

	useEffect(() => {
		proveriDalIma();
	}, [])

	const {
		register,
		handleSubmit,
		formState: { errors },
	} = useForm({
		resolver: yupResolver(interesovanjeSchema),
		mode: "onChange",
	});

	const proveriDalIma = () => {
		axios.get('http://localhost:8080/interesovanje/pronadjiInteresovanjePoJmbg/' + getJMBG())
		.then((res: any) => {
			setVecIma(true);
		}).catch((err: any) => {
			setVecIma(false);
		})
	}

	const downloadXHTML = () => {
		axios
			.get(
				`http://localhost:8081/interesovanje/generisiXHTML/${ getJMBG }`,
				{
					headers: {
						"Access-Control-Allow-Origin": "*",
					},
					responseType: "blob",
				}
			)
			.then((res: any) => {
				let blob = new Blob([res.data], {
					type: "text/html;charset=utf-8",
				});
				saveAs(
					blob,
					getJMBG()
				);
			})
			.catch((err: any) => {
				toast.error(err.response.data, {
					position: toast.POSITION.TOP_CENTER,
					autoClose: false,
					toastId: customId,
				});
			});
	};

	const podnosenjeInteresovanja = (interesovanje: InteresovanjeXML) => {
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
							<in:BrojMobilnogTelefona property="pred:brojMobilnogTelefona" datatype="xs:string">${
								interesovanje.BrojMobilnog
							}</in:BrojMobilnogTelefona>
							<in:BrojFiksnogTelefona property="pred:brojFiksongTelefona" datatype="xs:string">${
								interesovanje.BrojFiksnog
							}</in:BrojFiksnogTelefona>
						</in:LicneInformacije>
						<in:OpstinaPrimanja property="pred:opstinaPrimanja" datatype="xs:string">${
							interesovanje.OpstinaPrimanja
						}</in:OpstinaPrimanja>
						<in:Vakcina property="pred:vakcina" datatype="xs:string">${
							interesovanje.Vakcina
						}</in:Vakcina>
						<in:DavalacKrvi>${davalacValue}</in:DavalacKrvi>
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
			<GradjaninNavbar />
			{
				!vecIma &&
				<Card
				className="card-login-registracija"
				style={{ backgroundColor: "#DEEDE6", borderColor: "black" }}
				>
				<CardBody>
					<CardTitle tag="h2">Interesovanje</CardTitle>
					<Form className="form-login-registracija">
						<FormGroup>
							<Label>Opstina primanja</Label>
							<Input
								type="text"
								name="OpstinaPrimanja"
								placeholder="Opstina Primanja"
								invalid={errors.OpstinaPrimanja?.message}
								innerRef={register}
							/>
							<FormFeedback>{errors.OpstinaPrimanja?.message}</FormFeedback>
						</FormGroup>
						<FormGroup>
							<Label>Tip vakcine</Label>
							<Input type="select" name="Vakcina" innerRef={register}>
								<option>Pfizer-BioNTech</option>
								<option>Sputnik V (Gamaleya истраживачки центар)</option>
								<option>Sinopharm</option>
								<option>AstraZeneca</option>
								<option>Moderna</option>
							</Input>
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
						<Label style={{ display: "block" }}>Davalac krvi</Label>
						<Label>Da</Label>
						<Input
							className="ml-2"
							type="radio"
							name="DavalacKrvi"
							checked={davalacValue}
							value="1"
							innerRef={register}
							onChange={() => setDavalacValue(true)}
						></Input>
						<span className="pl-5">
							<Label>Ne</Label>
							<Input
								className="ml-2"
								type="radio"
								name="DavalacKrvi"
								checked={!davalacValue}
								value="0"
								innerRef={register}
								onChange={() => setDavalacValue(false)}
							></Input>
						</span>
						<Button
							className="registruj-login-btn"
							color="primary"
							type="button"
							style={{ display: "block" }}
							onClick={handleSubmit(podnosenjeInteresovanja)}
						>
							Podnosi interesovanje
						</Button>
					</Form>
				</CardBody>
			</Card>
			}
			{	vecIma && 
					<Card
					className="card-login-registracija"
					style={{ backgroundColor: "#DEEDE6", borderColor: "black" }}
					>
						<CardBody>
						<CardTitle tag="h2">Interesovanje</CardTitle>
						<Label>Vec ste popunili formu za interesovanje</Label>
						<Button
							className="registruj-login-btn"
							color="primary"
							type="button"
							style={{ display: "block" }}
							onClick={() => downloadXHTML()}
						>
							Skidanje XHTML
						</Button>
						</CardBody>
					</Card>
			}
		</div>
	);
};

export default Interesovanje;
