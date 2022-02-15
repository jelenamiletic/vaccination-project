
import { yupResolver } from "@hookform/resolvers/yup";
import { Chart, registerables } from "chart.js";
import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { Button, Card, CardBody, CardTitle, Form, FormFeedback, FormGroup, Input, Label, Modal, ModalBody, ModalFooter, ModalHeader, Table } from "reactstrap";
import SluzbenikNavbar from "../../Navbars/SluzbenikNavbar";
import "./Pretraga.css";

Chart.register(...registerables);

toast.configure();
const Pretraga = () => {
    const customId = "pretraga";

    const [pronadjeniDokumenti, setPronadjeniDokumenti] = useState<Array<any>>();

    const [showModal, setShowModal] = useState(false);

    const handleCloseModal = () => setShowModal(false);
    const handleShowModal = () => {
        console.log("doso");
        setShowModal(true);
    }

    useEffect(() => {
    }, [])

    const {
        register,
        handleSubmit,
        formState: { errors },
    } = useForm({
        mode: "onChange",
    });

    const toggle = () => setShowModal(!showModal);

    const pretraziArhivu = (unos: any) => {

    }

    return (
        <div>
            <SluzbenikNavbar />
            <Card
                className="card-login-registracija"
                style={{ backgroundColor: "#DEEDE6", borderColor: "black" }}
            >
                <CardBody>
                    <Form className="form-login-registracija">
                        <CardTitle tag="h2">Pretraga arhive dokumenata</CardTitle>
                        <FormGroup>
                            <Label>Kljucne reci:</Label>
                            <Input
                                type="text"
                                name="Pretraga"
                                placeholder=""
                                innerRef={register}
                            />
                            <FormFeedback>{errors.JMBG?.message}</FormFeedback>
                        </FormGroup>

                        <FormGroup>
                            <Label>Tip dokumenta:</Label>
                            <Input type="select" name="TipDokumenta">
                                <option>Sve</option>
                                <option>Saglasnost za imunizaciju</option>
                                <option>Potvrda o vakcinaciji</option>
                                <option>Sertifikat</option>
                            </Input>
                            <FormFeedback>{errors.JMBG?.message}</FormFeedback>
                        </FormGroup>

                        <Button
                            className="registruj-login-btn"
                            color="primary"
                            type="button"
                            style={{ display: "block" }}
                            onClick={handleSubmit(pretraziArhivu)}
                        >
                            Pretraga
                        </Button>
                    </Form>
                </CardBody>
            </Card>

            <Table bordered className="div-dokumenti">
                <thead>
                    <tr>
                        <th>Dokument</th>
                        <th>Ime</th>
                        <th>Tip</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th scope="row">
                            <Button variant="primary" onClick={handleShowModal}>
                                Otvori
                            </Button>
                        </th>
                        <td>23423423442_1</td>
                        <td>Saglasnost za imunizaciju</td>
                    </tr>
                    <tr>
                        <th scope="row">
                            <Button variant="primary" onClick={handleShowModal}>
                                Otvori
                            </Button>
                        </th>
                        <td>23423427876_1</td>
                        <td>Sertifikat</td>
                    </tr>
                    <tr>
                        <th scope="row">
                            <Button variant="primary" onClick={handleShowModal}>
                                Otvori
                            </Button>
                        </th>
                        <td>23423427876_1</td>
                        <td>Potvrda o vakcinaciji</td>
                    </tr>
                </tbody>
            </Table>

            <Modal isOpen={showModal} toggle={toggle}>
                <ModalHeader toggle={toggle}>Dokument</ModalHeader>
                <ModalBody>
                    Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                </ModalBody>
                <ModalFooter>
                    <Button color="primary">Preuzmi PDF</Button>
                    {' '}
                    <Button color="primary">Preuzmi XHTML</Button>
                </ModalFooter>
            </Modal>
        </div>
    );
};

export default Pretraga;
