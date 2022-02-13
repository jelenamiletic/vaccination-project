import { LicneInformacije } from "../../CommonTypes/LicneInformacije/LicneInformacije";
import { Imunizacija } from "./Imunizacija";

export interface PacijentSaglasnost {
	"sa:LicneInformacije": LicneInformacije;
    "sa:Imunizacija": Imunizacija;
}