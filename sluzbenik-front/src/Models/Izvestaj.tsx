import { KolicnaDozaPoRednomBroju } from "./CommonTypes/Izvestaj/KolicnaDozaPoRednomBroju";
import { PeriodIzvestaja } from "./CommonTypes/Izvestaj/PeriodIzvestaja";
import { RaspodelaDozaPoProizvodjacu } from "./CommonTypes/Izvestaj/RaspodelaDozaPoProizvodjacu";
import { ZahteviZaDigitalniSertifikat } from "./CommonTypes/Izvestaj/ZahteviZaDigitalniSertifikat";

export interface Izvestaj {
	"iz:BrojPodnetihDokumenata": number;
	"iz:KolicnaDozaPoRednomBroju": Array<KolicnaDozaPoRednomBroju>;
	"iz:PeriodIzvestaja": PeriodIzvestaja;
	"iz:RaspodelaDozaPoProizvodjacu": Array<RaspodelaDozaPoProizvodjacu>;
	"iz:UkupanBrojDatihDoza": number;
	"iz:ZahteviZaDigitalniSertifikat": ZahteviZaDigitalniSertifikat;
}
