export interface Vakcinacija {
	BrojDoze: number;
	Proizvodjac: string;
    TipVakcine: string;
	DatumDavanja: Date;
	Serija: string;
	ZdravstvenaUstanova: string;
}

export interface Test {
	ImeTesta: string;
	VrstaUzorka: string;
    ProizvodjacTesta: string;
	DatumVremeUzorkovanja: Date;
	DatumVremeIzdavanjaRezultata: Date;
	Rezultat: RezultatTesta;
	Laboratorija: string;
}

interface RezultatTesta{
	RezultatTesta: string;
}