import * as yup from "yup";

export const EvidencijaVakcinacijeSchema = yup.object().shape({
	JMBG: yup
		.string()
		.test("len", "JMBG mora imati 13 karaktera!", (val) => val?.length === 13)
		.matches(/^\d+$/, "JMBG mora da sadrzi samo brojevne vrednosti!"),
});