import * as yup from "yup";

export const zahtevSchema = yup.object().shape({
	RazlogOdbijanja: yup.string().required("Morate uneti razlog odbijanja!"),
});
