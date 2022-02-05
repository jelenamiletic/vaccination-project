import { Link } from "react-router-dom";
import { Collapse, Navbar, Nav, NavItem, NavbarToggler } from "reactstrap";
import { useState } from "react";

const NeautentifikovanNavbar = () => {
	const [collapsed, setCollapsed] = useState(true);
	const toggleNavbar = () => setCollapsed(!collapsed);

	return (
		<div className="nav-bar">
			<Navbar color="dark" light expand="md">
				<Link to="/">Glavna</Link>
				<NavbarToggler onClick={toggleNavbar} className="mr-2" />
				<Collapse isOpen={!collapsed} navbar>
					<Nav navbar>
						<NavItem>
							<Link to="/registracija">Registracija</Link>
						</NavItem>
						<NavItem>
							<Link to="/login">Login</Link>
						</NavItem>
					</Nav>
				</Collapse>
			</Navbar>
		</div>
	);
};

export default NeautentifikovanNavbar;
