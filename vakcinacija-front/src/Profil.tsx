import GradjaninNavbar from "./Navbars/GradjaninNavbar";
import ZdravstveniRadnikNavbar from "./Navbars/ZdravstveniRadnikNavbar";
import * as authService from "./Auth/AuthService";

const Profil = () => {
	const navbarBasedOnRole = () => {
		if (authService.getRole() === "ROLE_GRADJANIN") {
			return <GradjaninNavbar />;
		} else if (authService.getRole() === "ROLE_ZDRAVSTVENI_RADNIK") {
			return <ZdravstveniRadnikNavbar />;
		}
	};

	return <div>{navbarBasedOnRole()}</div>;
};

export default Profil;
