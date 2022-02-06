import "./App.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Registracija from "./Gradjanin/Registracija/Registracija";
import { Fragment } from "react";
import Glavna from "./Glavna";
import Login from "./Auth/Login";
import Interesovanje from "./Gradjanin/Interesovanje/Interesovanje";
import Profil from "./Profil";
import ProtectedRoute from "./ProtectedRoute";

function App() {
	return (
		<div className="App">
			<Router>
				<Fragment>
					<Routes>
						<Route path="/" element={<Glavna />} />
						<Route path="/registracija" element={<Registracija />} />
						<Route path="/login" element={<Login />} />
						<Route
							path="/profil"
							element={<ProtectedRoute role={"ROLE_GRADJANIN"} />}
						>
							<Route path="/profil" element={<Profil />} />
						</Route>
						<Route
							path="/profil"
							element={<ProtectedRoute role={"ROLE_ZDRAVSTVENI_RADNIK"} />}
						>
							<Route path="/profil" element={<Profil />} />
						</Route>
						<Route
							path="/interesovanje"
							element={<ProtectedRoute role={"ROLE_GRADJANIN"} />}
						>
							<Route path="/interesovanje" element={<Interesovanje />} />
						</Route>
					</Routes>
				</Fragment>
			</Router>
		</div>
	);
}

export default App;