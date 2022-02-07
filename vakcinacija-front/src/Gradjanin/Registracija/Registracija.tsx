import {
	Button,
	Form,
	FormGroup,
	Label,
	Input,
	Card,
	CardTitle,
	CardBody,
	FormFeedback,
} from "reactstrap";
import { registracijaSchema } from "./Validation/RegistracijaSchema";
import { useForm } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import { Gradjanin } from "../../Models/Gradjanin";
import { useState } from "react";
import axios from "axios";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useNavigate } from "react-router-dom";
import NeautentifikovanNavbar from "../../Navbars/NeautentifikovanNavbar";

toast.configure();
const Registracija = () => {
	const customId = "registracija";
	const [polValue, setPolValue] = useState("Muski");

	const navigate = useNavigate();

	const {
		register,
		handleSubmit,
		formState: { errors },
	} = useForm({
		resolver: yupResolver(registracijaSchema),
		mode: "onChange",
	});

	const registracija = (gradjanin: Gradjanin) => {
		let xml = `<gr:Gradjanin xmlns:ct="http:///www.ftn.uns.ac.rs/vakcinacija/commonTypes" 
						xmlns:gr="http:///www.ftn.uns.ac.rs/vakcinacija/gradjanin" xmlns:xs="http://www.w3.org/2001/XMLSchema">
						<ct:PunoIme>
							<ct:Ime>${gradjanin.Ime}</ct:Ime>
							<ct:Prezime>${gradjanin.Prezime}</ct:Prezime>
						</ct:PunoIme>
						<ct:Email>${gradjanin.Email}</ct:Email>
						<ct:Lozinka>${gradjanin.Lozinka}</ct:Lozinka>
						<ct:JMBG>${gradjanin.JMBG}</ct:JMBG>
						<gr:Pol>${gradjanin.Pol}</gr:Pol>
						<gr:Drzavljanstvo>${gradjanin.Drzavljanstvo}</gr:Drzavljanstvo>
					</gr:Gradjanin>`;
		axios
			.post("http://localhost:8080/gradjanin/registracija", xml, {
				headers: {
					"Content-Type": "application/xml",
					"Access-Control-Allow-Origin": "*",
				},
			})
			.then((res: any) => {
				navigate("/login");
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
			<NeautentifikovanNavbar />
			<Card
				className="card-login-registracija"
				style={{ backgroundColor: "#DEEDE6", borderColor: "black" }}
			>
				<CardBody>
					<CardTitle tag="h2">Registracija</CardTitle>
					<Form className="form-login-registracija">
						<FormGroup>
							<Label>Ime</Label>
							<Input
								type="text"
								name="Ime"
								placeholder="Ime"
								invalid={errors.Ime?.message}
								innerRef={register}
							/>
							<FormFeedback>{errors.Ime?.message}</FormFeedback>
						</FormGroup>
						<FormGroup>
							<Label>Prezime</Label>
							<Input
								type="text"
								name="Prezime"
								placeholder="Prezime"
								invalid={errors.Prezime?.message}
								innerRef={register}
							/>
							<FormFeedback>{errors.Prezime?.message}</FormFeedback>
						</FormGroup>
						<FormGroup>
							<Label>Email</Label>
							<Input
								type="email"
								placeholder="exmaple@email.com"
								name="Email"
								invalid={errors.Email?.message}
								innerRef={register}
							/>
							<FormFeedback>{errors.Email?.message}</FormFeedback>
						</FormGroup>
						<FormGroup>
							<Label>Lozinka</Label>
							<Input
								type="password"
								name="Lozinka"
								placeholder="Lozinka"
								invalid={errors.Lozinka?.message}
								innerRef={register}
							/>
							<FormFeedback>{errors.Lozinka?.message}</FormFeedback>
						</FormGroup>
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
						<Label>Muski</Label>
						<Input
							className="ml-2"
							type="radio"
							name="Pol"
							checked={polValue === "Muski"}
							value={polValue}
							innerRef={register}
							onChange={() => setPolValue("Muski")}
						>
							Muski
						</Input>
						<span className="pl-5">
							<Label>Zenski</Label>
							<Input
								className="ml-2"
								type="radio"
								name="Pol"
								checked={polValue === "Zenski"}
								value={polValue}
								innerRef={register}
								onChange={() => setPolValue("Zenski")}
							>
								Zenski
							</Input>
						</span>
						<FormGroup>
							<Label>Drzavljanstvo</Label>
							<Input type="select" name="Drzavljanstvo" innerRef={register}>
								<option>Drzavljanin Republike Srbije</option>
								<option>Strani drzavljanin sa boravkom u RS</option>
								<option>Strani drzavljanin bez boravka u RS</option>
							</Input>
						</FormGroup>
						<Button
							className="registruj-login-btn"
							color="primary"
							type="button"
							onClick={handleSubmit(registracija)}
						>
							Registruj se
						</Button>
					</Form>
				</CardBody>
			</Card>
		</div>
	);
};

export default Registracija;
