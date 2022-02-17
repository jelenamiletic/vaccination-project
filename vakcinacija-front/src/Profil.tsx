import GradjaninNavbar from "./Navbars/GradjaninNavbar";
import ZdravstveniRadnikNavbar from "./Navbars/ZdravstveniRadnikNavbar";
import * as authService from "./Auth/AuthService";
import { Card, CardBody, CardTitle, Label } from "reactstrap";
import axios from "axios";
import { XMLParser } from "fast-xml-parser";
import { useState } from "react";
import { Gradjanin } from "./Models/Gradjanin";

const Profil = () => {

	const navbarBasedOnRole = () => {
		if (authService.getRole() === "ROLE_GRADJANIN") {
			return <GradjaninNavbar />;
		} else if (authService.getRole() === "ROLE_ZDRAVSTVENI_RADNIK") {
			return <ZdravstveniRadnikNavbar />;
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
