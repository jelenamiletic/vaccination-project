import axios from "axios";

import * as authService from "./AuthService";

export function jwtInterceptor() {
	axios.interceptors.request.use((request) => {
		const token = authService.getAccessToken();
		if (token) {
			request.headers.common.Authorization = `Bearer ${token}`;
		}
		return request;
	});
}
