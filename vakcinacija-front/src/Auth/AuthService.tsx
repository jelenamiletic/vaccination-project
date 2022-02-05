import { Token } from "./Token";

export const getAccessToken = () => {
	return localStorage.getItem("accessToken");
};

export const getEmail = () => {
	return localStorage.getItem("email");
};

export const getRole = () => {
	return localStorage.getItem("role");
};

export const removeToken = () => {
	localStorage.removeItem("accessToken");
	localStorage.removeItem("email");
	localStorage.removeItem("role");
};

export const storeToken = (token: Token) => {
	localStorage.setItem("accessToken", token.accessToken);
	localStorage.setItem("email", token.email);
	localStorage.setItem("role", token.role);
};
