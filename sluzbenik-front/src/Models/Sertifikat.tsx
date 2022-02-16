import { LicneInformacijeSertifikat } from "./CommonTypes/Sertifikat/LicneInformacijeSertifikat";
import { Vakcinacija } from "./CommonTypes/Sertifikat/Vakcinacija";

export interface Sertifikat {
	"se:BrojSertifikata": string;
	"se:DatumVremeIzdavanja": string;
	"se:QR": string;
	"se:LicneInformacije": LicneInformacijeSertifikat;
	"se:Test": any;
	"se:Vakcinacija": Array<Vakcinacija>;
}
