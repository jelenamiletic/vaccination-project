import { Fragment } from "react";
import "./App.css";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Login from "./Auth/Login";
import Glavna from "./Glavna";
import ProtectedRoute from "./ProtectedRoute";
import Profil from "./Profil";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-daterangepicker/daterangepicker.css";
import Izvestaji from "./Sluzbenik/Izvestaji/Izvestaji";
import Vakcine from "./Sluzbenik/Vakcine/Vakcine";
import Zahtevi from "./Sluzbenik/Zahtevi/Zahtevi";
import Pretraga from "./Sluzbenik/pretraga/Pretraga";

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
						<Route
							path="/vakcine"
							element={<ProtectedRoute roles={["ROLE_SLUZBENIK"]} />}
						>
							<Route path="/vakcine" element={<Vakcine />} />
						</Route>
						<Route
							path="/dokumenti"
							element={<ProtectedRoute roles={["ROLE_SLUZBENIK"]} />}
						>
							<Route path="/dokumenti" element={<Pretraga />} />
						</Route>
						<Route
							path="/zahtevi"
							element={<ProtectedRoute roles={["ROLE_SLUZBENIK"]} />}
						>
							<Route path="/zahtevi" element={<Zahtevi />} />
						</Route>
					</Routes>
				</Fragment>
			</Router>
		</div>
	);
}

export default App;
