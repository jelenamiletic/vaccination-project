import * as yup from "yup";

export const registracijaSchema = yup.object().shape({
	Ime: yup
		.string()
		.max(30, "Maksimalan broj karaktera za ime je 30!")
		.required("Ime je obavezno polje!"),
	Prezime: yup
		.string()
		.max(30, "Maksimalan broj karaktera za prezime je 30!")
		.required("Prezime je obavezno polje!"),
	Email: yup
		.string()
		.email("Format email-a nije validan!")
		.required("Email je obavezno polje!"),
	Lozinka: yup.string().required("Lozinka je obavezno polje!"),
	JMBG: yup
		.string()
		.required("JMBG je obavezno polje!")
		.test("len", "JMBG mora imati 13 karaktera!", (val) => val?.length === 13)
		.matches(/^\d+$/, "JMBG mora da sadrzi samo brojevne vrednosti!"),
});
