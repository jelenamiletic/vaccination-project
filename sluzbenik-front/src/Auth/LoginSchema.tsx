import * as yup from "yup";

export const loginSchema = yup.object().shape({
	Email: yup.string().required("Email je obavezno polje!"),
	Lozinka: yup.string().required("Lozinka je obavezno polje!"),
});
