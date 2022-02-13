import { Link } from "react-router-dom";
import { Collapse, Nav, Navbar, NavbarToggler, NavItem } from "reactstrap";
import { useState } from "react";
import * as authService from "../Auth/AuthService";

const ZdravstveniRadnikNavbar = () => {
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
							<Link to="/evidencija-vakcinacije">Evidencija vakcinacije</Link>
						</NavItem>
						<NavItem>
							<Link to="/potvrda-vakcinacije">Izdavanje potvrde</Link>
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
	)
};

export default ZdravstveniRadnikNavbar;
