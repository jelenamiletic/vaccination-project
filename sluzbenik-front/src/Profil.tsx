import SluzbenikNavbar from "./Navbars/SluzbenikNavbar";
import * as authService from "./Auth/AuthService";
import { Card, CardBody, CardTitle } from "reactstrap";

const Profil = () => {
	const navbarBasedOnRole = () => {
		if (authService.getRole() === "ROLE_SLUZBENIK") {
			return <SluzbenikNavbar />;
		}
	};

	return (
		<div>
			<div>{navbarBasedOnRole()}</div>
			<div>
					<Card
						className="card-login-registracija"
						style={{ backgroundColor: "#DEEDE6", borderColor: "black" }}
					>
						<CardBody>
							<CardTitle tag="h2">Dobrodosli na sistem za vakcinaciju</CardTitle>
						</CardBody>
					</Card>
			</div>
		</div>
	);
};

export default Profil;
