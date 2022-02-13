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
import { Editor, EditorState } from "draft-js";
import "draft-js/dist/Draft.css";

const Zahtev = () => {
	const customId = "zahtev";

	const [editorState, setEditorState] = useState(() =>
    EditorState.createEmpty()
  	);

	const editor =  useRef<any>(null);
	function focusEditor() {
		if (editor && editor.current) {
			editor.current.focus();
		}
	}

	const navigate = useNavigate();

	useEffect(() => {
	}, [])

	const {
		register,
		handleSubmit,
		formState: { errors },
	} = useForm({
		resolver: yupResolver(zahtevSchema),
		mode: "onChange",
	});

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
							<za:Pol>${zahtev.Pol}</za:Pol>
							<za:JMBG property = "pred:jmbg" datatype = "xs:string">${getJMBG()}</za:JMBG>
							<za:BrojPasosa property = "pred:BrojPasosa" datatype = "xs:string">${zahtev.BrojPasosa}</za:BrojPasosa>
						</za:Podnosilac>
						<za:RazlogPodnosenja>${editorState.getCurrentContent().getPlainText('\u0001')}</za:RazlogPodnosenja>
					</za:Zahtev>`;
		axios
			.post("http://localhost:8080/zahtev/dodajNoviZahtev", xml, {
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
		<>
			<GradjaninNavbar />
			<Card>
				<CardBody>
					<CardTitle tag="h2">Zahtev za zeleni sertifikat</CardTitle>
					<Form className="form-login-registracija">
						<FormGroup>
							<Label>Pol</Label>
							<Input type="select" name="Pol" innerRef={register}>
								<option>Muski</option>
								<option>Zenski</option>
							</Input>
						</FormGroup>
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
							style={{ border: "1px solid black", minHeight: "6em", cursor: "text" }}
							onClick={focusEditor}
							>
							<Editor
								ref={editor}
								editorState={editorState}
								onChange={setEditorState}
								placeholder="Write something!"
							/>
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
				</CardBody>
			</Card>
		</>
	);
};

export default Zahtev;