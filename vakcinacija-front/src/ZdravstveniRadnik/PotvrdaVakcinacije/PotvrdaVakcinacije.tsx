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
import { getJMBG, getEmail } from "../../Auth/AuthService";

const PotvrdaVakcinacije = () => {
	const customId = "PotvrdaVakcinacije";

	const navigate = useNavigate();

	useEffect(() => {
	}, [])

	

	return (
		<div>
			<ZdravstveniRadnikNavbar />
			
		</div>
	);
};

export default PotvrdaVakcinacije;
