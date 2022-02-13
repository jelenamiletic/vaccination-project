import * as yup from "yup";

export const zahtevSchema = yup.object().shape({
	BrojPasosa: yup
		.string()
		.required("Broj pasosa je obavezno polje!")
		.test("len", "broj pasosa mora imati 9 karaktera!", (val) => val?.length === 9)
		.matches(/^\d+$/, "broj pasosa mora da sadrzi samo brojevne vrednosti!"),
    DatumRodjenja: yup
        .string()
        .required("Datum rodjenja je obavezno polje!")
        .matches(/^\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$/, "broj pasosa mora biti u formatu yyyy-mm-dd!"),     
});
