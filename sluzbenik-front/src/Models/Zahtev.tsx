import { Podnosilac } from "./CommonTypes/Zahtev/Podnosilac";

export interface Zahtev {
	"za:Podnosilac": Podnosilac;
	"za:RazlogPodnosenja": string;
	"za:Odobren": boolean;
	"za:DatumPodnosenja": string;
}
