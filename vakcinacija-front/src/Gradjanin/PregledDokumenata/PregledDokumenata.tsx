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
    Dropdown,
    DropdownToggle,
    DropdownMenu,
    DropdownItem
} from "reactstrap";
import GradjaninNavbar from "../../Navbars/GradjaninNavbar";
import { getJMBG } from "../../Auth/AuthService";
import { Editor, EditorState } from "draft-js";
import "draft-js/dist/Draft.css";
import { saveAs } from "file-saver";

const PregledDokumenata = () => {
	const customId = "pregled-dokumenata";

    const [vecIma, setVecIma] = useState(false);
	const navigate = useNavigate();

	useEffect(() => {
	}, [])

	const {
		register,
		handleSubmit,
		formState: { errors },
	} = useForm({
		resolver: yupResolver(null),
		mode: "onChange",
	});

    const proveriDalIma = (name) => {
        if(name != "saglasnost"){
		axios.get(`http://localhost:8080/${name}/pronadji${name.replace(/^\w/, (c) => c.toUpperCase())}PoJmbg/` + getJMBG())
		.then((res: any) => {
			setVecIma(true);
		}).catch((err: any) => {
			setVecIma(false);
		})}
	}

    const [dropdownOpen, setDropdownOpen] = useState(false);
    const toggle = () => setDropdownOpen(prevState => !prevState);


    const handleChange = event => {
        let name = event.target.name;
        proveriDalIma(name);
        downloadXHTML(name);
        downloadPDF(name);
    };

	const downloadXHTML = (name) => {
		axios
			.get(
				`http://localhost:8080/${name}/generisiXHTML/${getJMBG()}`,
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

    const downloadPDF = (name) => {
		axios
			.get(
				`http://localhost:8080/${name}/generisiPdf/${getJMBG()}`,
				{
					headers: {
						"Access-Control-Allow-Origin": "*",
					},
					responseType: "arraybuffer",
				}
			)
			.then((res: any) => {
				let blob = new Blob([res.data], {
					type: "application/pdf",
				});
				saveAs(
					blob,
					getJMBG()
				);
                // var fileURL = URL.createObjectURL(blob);
                // window.open(fileURL);
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
					<CardTitle tag="h2">Pregled dokumenata</CardTitle>
					<Dropdown isOpen={dropdownOpen} toggle={toggle} >
                        <DropdownToggle caret>Izaberite vrstu dokumenta</DropdownToggle>
                        <DropdownMenu>
                            <DropdownItem header>Tip dokumenta</DropdownItem>
                            <DropdownItem onClick={handleChange} name="zahtev">Zahtev</DropdownItem>
                            <DropdownItem onClick={handleChange} name="sertifikat">Sertifikat</DropdownItem>
                            <DropdownItem onClick={handleChange} name="interesovanje">Interesovanje</DropdownItem>
                            <DropdownItem onClick={handleChange} name="saglasnost">Saglasnost</DropdownItem>
                        </DropdownMenu>
                    </Dropdown>
				</CardBody>
			</Card>
		</>
	);
};

export default PregledDokumenata;