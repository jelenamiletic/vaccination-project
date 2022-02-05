import { Navigate, Outlet } from "react-router-dom";
import * as authService from "./Auth/AuthService";

const ProtectedRoute = ({ role }: any) => {
	let currentUserRole = authService.getRole();
	let accessToken = authService.getAccessToken();

	return currentUserRole === role && accessToken ? (
		<Outlet />
	) : (
		<Navigate to="/login" />
	);
};

export default ProtectedRoute;
