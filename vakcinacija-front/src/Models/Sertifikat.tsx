import { Test, Vakcinacija } from "./CommonTypes/SertifikatTypes";

export interface SertifikatXML {
	Ime: string;
	Prezime: string;
	DatumRodjenja: Date;
	Pol: string;
	BrojPasosa: string;
	JMBG: string;
	Vakcinacija: Array<Vakcinacija>;
    Test: Array<Test>
}