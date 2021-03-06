import { Link } from "react-router-dom";
import { Collapse, Navbar, Nav, NavItem, NavbarToggler } from "reactstrap";
import { useState } from "react";
import * as authService from "../Auth/AuthService";

const GradjaninNavbar = () => {
	const [collapsed, setCollapsed] = useState(true);
	const toggleNavbar = () => setCollapsed(!collapsed);

	const logout = () => {
		authService.removeToken();
	};

	return (
		<div className="nav-bar">
			<Navbar color="dark" light expand="md">
				<Link to="/profil">Profil</Link>
				<NavbarToggler onClick={toggleNavbar} className="mr-2" />
				<Collapse isOpen={!collapsed} navbar>
					<Nav navbar>
						<NavItem>
							<Link to="/interesovanje">Interesovanje</Link>
						</NavItem>
						<NavItem>
							<Link to="/saglasnost">Saglasnosti</Link>
						</NavItem>
						<NavItem>
							<Link to="/zahtev">Zahtev za sertifikat</Link>
						</NavItem>
						<NavItem>
							<Link to="/pregled-dokumenata">Pregled dokumenata</Link>
						</NavItem>
						<NavItem>
							<Link to="/login" onClick={logout}>
								Logout
							</Link>
						</NavItem>
					</Nav>
				</Collapse>
			</Navbar>
		</div>
	);
};

export default GradjaninNavbar;
