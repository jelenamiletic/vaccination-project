import { LicneInformacijeSertifikat } from "./Saglasnost/utils/LicneInformacijeSertifikat";

export interface Sertifikat {
    "se:BrojSertifikata" : string;
    "se:DatumVremeIzdavanja" : string;
    "se:QR" : string;
    "se:LicneInformacije" : LicneInformacijeSertifikat;
    "se:Test": any;
    "se:Vakcinacija" : Array<any>;
}