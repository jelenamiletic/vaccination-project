import { Navigate, Outlet } from "react-router-dom";
import * as authService from "./Auth/AuthService";

const ProtectedRoute = ({ roles }: any) => {
	let currentUserRole = authService.getRole();
	let accessToken = authService.getAccessToken();

	return roles.includes(currentUserRole) && accessToken ? (
		<Outlet />
	) : (
		<Navigate to="/login" />
	);
};

export default ProtectedRoute;
