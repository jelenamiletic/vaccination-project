import SluzbenikNavbar from "./Navbars/SluzbenikNavbar";
import * as authService from "./Auth/AuthService";

const Profil = () => {
	const navbarBasedOnRole = () => {
		if (authService.getRole() === "ROLE_SLUZBENIK") {
			return <SluzbenikNavbar />;
		}
	};

	return <div>{navbarBasedOnRole()}</div>;
};

export default Profil;
