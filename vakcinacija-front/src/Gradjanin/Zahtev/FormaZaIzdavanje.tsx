import { yupResolver } from "@hookform/resolvers/yup";
import axios from "axios";
import { useEffect, useState, useRef } from "react";
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
import { ZahtevXML } from "../../Models/Zahtev";
import GradjaninNavbar from "../../Navbars/GradjaninNavbar";
import { getJMBG } from "../../Auth/AuthService";
import { zahtevSchema } from "./Validation/ZahtevSchema";
import {EditorState } from "draft-js";
import {Editor} from "react-draft-wysiwyg";
import "draft-js/dist/Draft.css";
import { XMLParser } from "fast-xml-parser";
import { Saglasnost } from "../../Models/Saglasnost/Saglasnost";
import { Potvrda } from "../../Models/Potvrda";

const Zahtev = () => {
	const customId = "zahtev";
	const [postoji, setPostoji] = useState(false);
	const [postojiPotvrda, setPostojiPotvrda] = useState(false);

	const [editorState, setEditorState] = useState(() =>
		EditorState.createEmpty()
	);

	const editor = useRef<any>(null);

	const navigate = useNavigate();

	useEffect(() => {
		pronadjiZahtev();
		pronadjiPotvrdu();
	}, []);

	const {
		register,
		handleSubmit,
		formState: { errors },
	} = useForm({
		resolver: yupResolver(zahtevSchema),
		mode: "onChange",
	});

	const pronadjiZahtev = () => {
		axios.get('http://localhost:8080/zahtev/pronadjiZahtevPoJmbg/' + getJMBG())
				.then((res: any) => {
					const parser = new XMLParser();
					const result= parser.parse(res.data);
					const zahtev: ZahtevXML = result["za:Zahtev"];
					if (!zahtev){
						
						setPostoji(false);
					
					} else {

						setPostoji(true);

					}
				}).catch((err: any) => {
					setPostoji(false);
				})
	}
	
	const pronadjiPotvrdu = () => {
		axios.get('http://localhost:8080/potvrda/dobaviPoslednjuPotvrduPoJmbg/' + getJMBG())
				.then((res: any) => {
					const parser = new XMLParser();
					const result= parser.parse(res.data);
					const potvrda: Potvrda = result["po:Potvrda"];
					if (!potvrda){
						
						setPostojiPotvrda(false);
					
					} else {
						const poslednjaVakcina = potvrda["po:InformacijeOVakcinama"][potvrda["po:InformacijeOVakcinama"].length - 1]

						if(poslednjaVakcina["po:BrojDoze"] >= 2){
							setPostojiPotvrda(true);
						}

					}
				}).catch((err: any) => {
					setPostojiPotvrda(false);
				})
	}	

	const podnosenjeZahteva = (zahtev: ZahtevXML) => {
		let xml = `<za:Zahtev
						xmlns:xs="http://www.w3.org/2001/XMLSchema"
						xmlns:za="http:///www.ftn.uns.ac.rs/vakcinacija/zahtev" xmlns:ct="http:///www.ftn.uns.ac.rs/vakcinacija/commonTypes"
						xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http:///www.ftn.uns.ac.rs/vakcinacija/zahtev ../xsd/zahtev.xsd"
						xmlns:addr="http://www.ftn.uns.ac.rs/rdf/zahtev"
						xmlns:pred="http://www.ftn.uns.ac.rs/rdf/zahtev/predicate/"
						vocab="http://www.ftn.uns.ac.rs/rdf/zahtev/" 
						about="http://www.ftn.uns.ac.rs/rdf/zahtev/${getJMBG()}">
						<za:Podnosilac>
							<za:PunoIme>
								<ct:Ime></ct:Ime>
								<ct:Prezime></ct:Prezime>
							</za:PunoIme>
							<za:DatumRodjenja>${zahtev.DatumRodjenja}</za:DatumRodjenja>
							<za:Pol>Muski</za:Pol>
							<za:JMBG property = "pred:jmbg" datatype = "xs:string">${getJMBG()}</za:JMBG>
							<za:BrojPasosa property = "pred:BrojPasosa" datatype = "xs:string">${
								zahtev.BrojPasosa
							}</za:BrojPasosa>
						</za:Podnosilac>
						<za:RazlogPodnosenja>${editorState
							.getCurrentContent()
							.getPlainText("\u0001")}</za:RazlogPodnosenja>
						<za:Odobren></za:Odobren>
					</za:Zahtev>`;
		axios
			.post("http://localhost:8080/zahtev/dodajNoviZahtev", xml, {
				headers: {
					"Content-Type": "application/xml",
					"Access-Control-Allow-Origin": "*",
				},
			})
			.then((res: any) => {
				toast.success("Uspesno poslat zahtev", {
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
	};

	return (
		<>
			<GradjaninNavbar />
			<Card
				className="card-login-registracija"
				style={{ backgroundColor: "#DEEDE6", borderColor: "black" }}
			>
				<CardBody>
					<CardTitle tag="h2">Zahtev za zeleni sertifikat</CardTitle>

					{
						!postoji && postojiPotvrda &&
						<Form className="form-login-registracija">
						<FormGroup>
							<Label>Broj pasosa</Label>
							<Input
								type="text"
								name="BrojPasosa"
								invalid={errors.BrojPasosa?.message}
								innerRef={register}
							/>
							<FormFeedback>{errors.BrojPasosa?.message}</FormFeedback>
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
							<div
								style={{
									border: "1px solid black",
									minHeight: "6em",
									cursor: "text",
								}}
							>
								<div className='editor'>
								<Editor
									ref={editor}
									editorState={editorState}
									onEditorStateChange={setEditorState}
									placeholder="Write something!"
									toolbar={{
										options: ['inline', 'fontSize', 'list', 'textAlign', 'history'],
										inline: { inDropdown: true },
										list: { inDropdown: true },
										textAlign: { inDropdown: true },
										link: { inDropdown: true },
										history: { inDropdown: true },
									  }}
								/>
								</div>
								
							</div>
						</FormGroup>
						<Button
							className="registruj-login-btn"
							color="primary"
							type="button"
							style={{ display: "block" }}
							onClick={handleSubmit(podnosenjeZahteva)}
						>
							Podnesi zahtev
						</Button>
					</Form>
					}

					{
						postoji && 
						<Label>Vec ste podneli zahtev!</Label>
					}

					{
						!postoji && !postojiPotvrda &&
						<Label>Niste jos primili dve vakcine!</Label>
					}
					
				</CardBody>
			</Card>
		</>
	);
};

export default Zahtev;
