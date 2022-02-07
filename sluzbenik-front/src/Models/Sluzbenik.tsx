import { Role } from "./CommonTypes/Role";

export interface Sluzbenik {
	Ime: string;
	Prezime: string;
	Email: string;
	Lozinka: string;
	Enabled: boolean;
	Roles: Array<Role>;
	lastPasswordResetDate: Date;
}
