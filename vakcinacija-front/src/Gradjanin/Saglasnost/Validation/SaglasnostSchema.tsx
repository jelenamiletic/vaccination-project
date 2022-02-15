import * as yup from "yup";

export const saglasnostSchema = yup.object().shape({
	ImeRoditelja: yup
		.string()
		.required("ime roditelja je obavezno polje!"),
	ZdravstvenaUstanova: yup
		.string()
		.required("Zdravstvena ustanova je obavezno polje!"),
	VakcinacijskiPunkt: yup
		.string()
		.required("Vakcinacijski punkt je obavezno polje!")
		.matches(/^\d+$/, "Vakcinacijski punkt mora da sadrzi samo brojevne vrednosti!"),
	BrojTelefonaLekara: yup
		.string()
		.required("Broj telefona lekara je obavezno polje!")
		.test("len", "broj mobilnog mora imati 10 karaktera!", (val) => val?.length === 10)
		.matches(/^\d+$/, "broj telefona mora da sadrzi samo brojevne vrednosti!"),
	SerijaVakcine: yup
		.string()
		.required("Serija vakcine je obavezno polje!"),
	DatumUtvrdjivanja: yup
		.string()
		.matches(/^\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$/, "broj pasosa mora biti u formatu yyyy-mm-dd!"),
});