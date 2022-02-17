import { InformacijeOVakcinama } from "./Potvrda/InformacijeOVakcinama";
import { LicneInformacije } from "./Potvrda/LicneInformacije";
import { Vakcine } from "./Potvrda/Vakcine";


export interface Potvrda {
	"po:Sifra": string;
	"po:LicneInformacije": LicneInformacije;
	"po:InformacijeOVakcinama": Array<InformacijeOVakcinama>;
	"po:ZdravstvenaUstanova": string;
	"po:VakcinaPrveDveDoze": string;
	"po:Vakcine": Array<Vakcine>;
	"po:DatumIzdavanja": string;
	"po:QR": string;
}
