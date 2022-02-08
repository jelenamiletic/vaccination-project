import * as yup from "yup";

export const interesovanjeSchema = yup.object().shape({
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
	OpstinaPrimanja: yup
		.string()
		.max(30, "Maksimalan broj karaktera za opstinu je 30!")
		.required("Opstina je obavezno polje!"),
});
