import * as yup from "yup";

export const zahtevSchema = yup.object().shape({
	BrojPasosa: yup
		.string()
		.required("Broj pasosa je obavezno polje!")
		.test("len", "broj pasosa mora imati 9 karaktera!", (val) => val?.length === 9)
		.matches(/^\d+$/, "broj pasosa mora da sadrzi samo brojevne vrednosti!"),
	Ime: yup
		.string()
		.required("Ime je obavezno polje!"),
    Prezime: yup
		.string()
		.required("Prezime je obavezno polje!"),
    DatumRodjenja: yup
        .string()
        .required("Datum rodjenja je obavezno polje!")
        .matches(/^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d$/, "broj pasosa mora biti u formatu dd/mm/yyyy!"),     
});
