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
import { getJMBG, getEmail } from "../../Auth/AuthService";

const Saglasnost = () => {
	const customId = "saglasnost";

	const navigate = useNavigate();

	useEffect(() => {
	}, [])

	

	return (
		<div>
			<GradjaninNavbar />
			
		</div>
	);
};

export default Saglasnost;
