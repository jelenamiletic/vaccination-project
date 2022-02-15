import "./App.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Registracija from "./Gradjanin/Registracija/Registracija";
import { Fragment } from "react";
import Glavna from "./Glavna";
import Login from "./Auth/Login";
import Interesovanje from "./Gradjanin/Interesovanje/Interesovanje";
import Profil from "./Profil";
import ProtectedRoute from "./ProtectedRoute";
import "bootstrap/dist/css/bootstrap.min.css";
import Saglasnost from "./Gradjanin/Saglasnost/Saglasnost";
import Zahtev from "./Gradjanin/Zahtev/FormaZaIzdavanje";
import EvidencijaVakcinacije from "./ZdravstveniRadnik/EvidencijaVakcinacije/EvidencijaVakcinacije";
import PregledDokumenata from "./Gradjanin/PregledDokumenata/PregledDokumenata";

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
							element={
								<ProtectedRoute
									roles={["ROLE_GRADJANIN", "ROLE_ZDRAVSTVENI_RADNIK"]}
								/>
							}
						>
							<Route path="/profil" element={<Profil />} />
						</Route>
						<Route
							path="/interesovanje"
							element={<ProtectedRoute roles={["ROLE_GRADJANIN"]} />}
						>
							<Route path="/interesovanje" element={<Interesovanje />} />
						</Route>
						<Route
							path="/saglasnost"
							element={<ProtectedRoute roles={["ROLE_GRADJANIN"]} />}
						>
							<Route path="/saglasnost" element={<Saglasnost />} />
						</Route>
						<Route
							path="/zahtev"
							element={<ProtectedRoute roles={["ROLE_GRADJANIN"]} />}
						>
							<Route path="/zahtev" element={<Zahtev />} />
						</Route>
						<Route
							path="/evidencija-vakcinacije"
							element={<ProtectedRoute roles={["ROLE_ZDRAVSTVENI_RADNIK"]} />}
						>
							<Route path="/evidencija-vakcinacije" element={<EvidencijaVakcinacije />} />
						</Route>
						<Route
							path="/pregled-dokumenata"
							element={<ProtectedRoute roles={["ROLE_GRADJANIN"]} />}
						>
							<Route path="/pregled-dokumenata" element={<PregledDokumenata />} />
						</Route>
					</Routes>
				</Fragment>
			</Router>
		</div>
	);
}

export default App;
