import { LicneInformacijeLekara } from "./LicneInformacijeLekara";
import { Obrazac } from "./Obrazac";

export interface ZdravstveniRadnikSaglasnost {
    "sa:ZdravstvenaUstanova": string;
    "sa:VakcinacijskiPunkt": string;
    "sa:LicneInformacijeLekara": LicneInformacijeLekara;
    "sa:Obrazac": Obrazac;
}