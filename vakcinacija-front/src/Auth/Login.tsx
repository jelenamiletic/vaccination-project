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
import { yupResolver } from "@hookform/resolvers/yup";
import axios from "axios";
import { useForm } from "react-hook-form";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import NeautentifikovanNavbar from "../Navbars/NeautentifikovanNavbar";
import { JwtAuthenticationRequest } from "./JwtAuthenticationRequest";
import { loginSchema } from "./LoginSchema";
import { Token } from "./Token";
import * as authService from "./AuthService";
import { useNavigate } from "react-router-dom";
import { XMLParser } from "fast-xml-parser";

const Login = () => {
	const customId = "login";

	const navigate = useNavigate();

	const {
		register,
		handleSubmit,
		formState: { errors },
	} = useForm({
		resolver: yupResolver(loginSchema),
		mode: "onChange",
	});

	const login = (jwtRequest: JwtAuthenticationRequest) => {
		let xml = `<JwtAuthenticationRequest>
						<Email>${jwtRequest.Email}</Email>
						<Password>${jwtRequest.Lozinka}</Password>
				    </JwtAuthenticationRequest>`;
		axios
			.post("http://localhost:8080/auth/login", xml, {
				headers: {
					"Content-Type": "application/xml",
					"Access-Control-Allow-Origin": "*",
				},
			})
			.then((res: any) => {
				const parser = new XMLParser();
				const result = parser.parse(res.data);
				const token: Token = result.Token;
				authService.storeToken(token);
				if (
					authService.getRole() === "ROLE_GRADJANIN" ||
					authService.getRole() === "ROLE_ZDRAVSTVENI_RADNIK"
				) {
					navigate("/profil");
				}
			})
			.catch((err: any) => {
				let message = "";
				if (err.response.status === 404) {
					message = "Pogresni kredencijali!";
				}
				toast.error(message, {
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
					<CardTitle tag="h2">Login</CardTitle>
					<Form className="form-login-registracija">
						<FormGroup>
							<Label>Email</Label>
							<Input
								type="email"
								name="Email"
								invalid={errors.Email?.message}
								innerRef={register}
							/>
							<FormFeedback>{errors.Email?.message}</FormFeedback>
						</FormGroup>
						<FormGroup>
							<Label>Password</Label>
							<Input
								type="password"
								name="Lozinka"
								invalid={errors.Lozinka?.message}
								innerRef={register}
							/>
							<FormFeedback>{errors.Lozinka?.message}</FormFeedback>
						</FormGroup>
						<Button
							className="registruj-login-btn"
							color="primary"
							type="button"
							onClick={handleSubmit(login)}
						>
							Login
						</Button>
					</Form>
				</CardBody>
			</Card>
		</div>
	);
};

export default Login;
