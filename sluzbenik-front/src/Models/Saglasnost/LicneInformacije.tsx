import { PunoIme } from "../CommonTypes/PunoIme";
import { Drzavljanstvo } from "./Drzavljanstvo";

export interface LicneInformacije {
    "sa:Drzavljanstvo": Drzavljanstvo;
    "sa:PunoIme": PunoIme;
    "sa:ImeRoditelja": string;
    "sa:Pol": string;
    "sa:DatumRodjenja": string;
    "sa:MestoRodjenja": string;
    "sa:Adresa": string;
    "sa:Mesto": string;
    "sa:Opstina": string;
    "sa:BrojFiksnogTelefona": string;
    "sa:BrojMobilnogTelefona": string;
    "sa:Email": string;
    "sa:RadniStatus": string;
    "sa:ZanimanjeZaposlenog": string;
}