import { PacijentSaglasnost } from "./utils/PacijentSaglasnost";
import { ZdravstveniRadnikSaglasnost } from "./utils/ZdravstveniRadnikSaglasnost";

export interface Saglasnost {
	"sa:PacijentSaglasnost": PacijentSaglasnost;
	"sa:ZdravstveniRadnikSaglasnost": ZdravstveniRadnikSaglasnost;
	"sa:DatumPodnosenja": string;
}
