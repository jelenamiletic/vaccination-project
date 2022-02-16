import * as yup from "yup";

export const saglasnostSchema = yup.object().shape({
	ImeRoditelja: yup
		.string()
		.required("ime roditelja je obavezno polje!"),
	BrojFiksnog: yup
		.string()
		.required("broj fiksnog je obavezno polje!")
		.test("len", "broj fiksnog mora imati 9 karaktera!", (val) => val?.length === 9)
		.matches(/^\d+$/, "broj fiksnog mora da sadrzi samo brojevne vrednosti!"),
	BrojMobilnog: yup
		.string()
		.required("broj mobilnog je obavezno polje!")
		.test("len", "broj mobilnog mora imati 10 karaktera!", (val) => val?.length === 10)
		.matches(/^\d+$/, "broj mobilnog mora da sadrzi samo brojevne vrednosti!"),
	DatumRodjenja: yup
		.string()
		.matches(/^\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$/, "godina mora biti u formatu yyyy-mm-dd!"),
	MestoRodjenja: yup
		.string()
		.required("mesto rodjenja je obavezno polje!"),
	Adresa: yup
		.string()
		.required("adresa je obavezno polje!"),
	Mesto: yup
		.string()
		.required("mesto je obavezno polje!"),
	Opstina: yup
		.string()
		.required("opstina je obavezno polje!"),
});