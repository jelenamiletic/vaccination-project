export interface Vakcinacija {
	BrojDoze: number;
	Proizvodjac: string;
    TipVakcine: string;
	DatumDavanja: string;
	Serija: string;
	ZdravstvenaUstanova: string;
}

export interface Test {
	ImeTesta: string;
	VrstaUzorka: string;
    ProizvodjacTesta: string;
	DatumVremeUzorkovanja: string;
	DatumVremeIzdavanjaRezultata: string;
	Rezultat: RezultatTesta;
	Laboratorija: string;
}

interface RezultatTesta{
	RezultatTesta: string;
}