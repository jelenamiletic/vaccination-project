import { Role } from "./CommonTypes/Role";

export interface Gradjanin {
	Ime: string;
	Prezime: string;
	Email: string;
	Lozinka: string;
	Enabled: boolean;
	Roles: Array<Role>;
	lastPasswordResetDate: Date;
	Pol: string;
	Drzavljanstvo: string;
}
