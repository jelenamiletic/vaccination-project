import { yupResolver } from "@hookform/resolvers/yup";
import axios from "axios";
import { useEffect, useState, useRef } from "react";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { XMLParser } from "fast-xml-parser";
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
import 'rsuite-table/dist/css/rsuite-table.css'; // or 'rsuite-table/dist/css/rsuite-table.css'
import { Table, Column, HeaderCell, Cell } from "rsuite-table";
import GradjaninNavbar from "../../Navbars/GradjaninNavbar";
import { getJMBG } from "../../Auth/AuthService";
import { Editor, EditorState } from "draft-js";
import "draft-js/dist/Draft.css";
import { saveAs } from "file-saver";
import { ZahtevXML } from "../../Models/Zahtev";
import { SertifikatXML } from "../../Models/Sertifikat";
import { Saglasnost } from "../../Models/Saglasnost/Saglasnost";
import { InteresovanjeXML } from "../../Models/Interesovanje";

const PregledDokumenata = () => {
	const customId = "pregled-dokumenata";

    const [vecIma, setVecIma] = useState(false);
	const [jmbg, setJmbg] = useState(null);
	const navigate = useNavigate();
	const [documentType, setDocumentType] = useState(null);
	const [documents, setDocuments] = useState<Array<ZahtevXML|SertifikatXML|Saglasnost|InteresovanjeXML>>([]);

	useEffect(() => {
		setJmbg(getJMBG());
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
		axios.get(name == "saglasnost"? 'http://localhost:8080/saglasnost/pronadjiSaglasnostPoJmbgIliBrPasosa/' + getJMBG():`http://localhost:8080/${name}/pronadji${name.replace(/^\w/, (c) => c.toUpperCase())}PoJmbg/` + getJMBG())
		.then((res: any) => {
			setVecIma(true);
		}).catch((err: any) => {
			setVecIma(false);
		})
	}

    const [dropdownOpen, setDropdownOpen] = useState(false);
    const toggle = () => setDropdownOpen(prevState => !prevState);

	const pronadjiSveDokumente = (name) => {
        if(name != "saglasnost"){
		axios.get(`http://localhost:8080/${name}/pronadji${name.replace(/^\w/, (c) => c.toUpperCase())}PoJmbg/` + getJMBG())
		.then((res: any) => {
			const parser = new XMLParser();
			const result = parser.parse(res.data);
			switch(name){
				case "zahtev":
					let temp1: ZahtevXML = result[Object.keys(result)[1]];
					setDocuments([temp1]);
					break;
				case "interesovanje":
					let temp2: InteresovanjeXML = result[Object.keys(result)[1]];
					setDocuments([temp2]);
					break;
				case "sertifikat":
					let temp3: SertifikatXML = result[Object.keys(result)[1]];
					setDocuments([temp3]);
					break;
			}	
			
		}).catch((err: any) => {
			setDocuments([]);
		})}else{
			axios.get(`http://localhost:8080/saglasnost/pronadjiSaglasnostPoJmbgIliBrPasosa/` + getJMBG())
			.then((res: any) => {
				const parser = new XMLParser();
				const result = parser.parse(res.data);
				let temp1: Saglasnost = result[Object.keys(result)[1]];
				setDocuments([temp1]);
			
		}).catch((err: any) => {
			setDocuments([]);
		})
		}
	}

    const handleChange =  event => {
        let name = event.target.name;
        proveriDalIma(name);
        setDocumentType(name);
    };

	useEffect(()=>{
		vecIma && pronadjiSveDokumente(documentType);
	}, [vecIma]);



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
				{documents && documents.length !== 0 && vecIma &&
				<div>
					<Table
						height={320}
						data={documents}
					>
						<Column width={200} align="center" fixed>
							<HeaderCell>Vrsta dokumenta</HeaderCell>
							<Cell>{documentType}</Cell>
						</Column>

						<Column width={300} fixed>
							<HeaderCell>Datum podnosenja</HeaderCell>
							<Cell dataKey={`${documentType.toLowerCase().substring(0,2)}:DatumPodnosenja`}/>
						</Column>
						<Column width={120} fixed="right">
							<HeaderCell>Download</HeaderCell>
							<Cell>
							{rowData => {
								return (
								<span>
									<a onClick={()=>{downloadPDF(documentType)}}> PDF </a> | <a onClick={()=>{downloadXHTML(documentType)}}> XHTML </a>
								</span>
								);
							}}
							</Cell>
						</Column>
					</Table>	
				</div>
				}
			</Card>
		</>
	);
};

export default PregledDokumenata;