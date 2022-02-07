import { Fragment } from "react";
import "./App.css";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Login from "./Auth/Login";
import Glavna from "./Glavna";
import ProtectedRoute from "./ProtectedRoute";
import Profil from "./Profil";
import Izvestaji from "./Sluzbenik/Izvestaji";
import "bootstrap/dist/css/bootstrap.min.css";

function App() {
	return (
		<div className="App">
			<Router>
				<Fragment>
					<Routes>
						<Route path="/" element={<Glavna />} />
						<Route path="/login" element={<Login />} />
						<Route
							path="/profil"
							element={<ProtectedRoute roles={["ROLE_SLUZBENIK"]} />}
						>
							<Route path="/profil" element={<Profil />} />
						</Route>
						<Route
							path="/izvestaji"
							element={<ProtectedRoute roles={["ROLE_SLUZBENIK"]} />}
						>
							<Route path="/izvestaji" element={<Izvestaji />} />
						</Route>
					</Routes>
				</Fragment>
			</Router>
		</div>
	);
}

export default App;
