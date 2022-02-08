import { KolicnaDozaPoRednomBroju } from "./CommonTypes/KolicnaDozaPoRednomBroju";
import { PeriodIzvestaja } from "./CommonTypes/PeriodIzvestaja";
import { RaspodelaDozaPoProizvodjacu } from "./CommonTypes/RaspodelaDozaPoProizvodjacu";
import { ZahteviZaDigitalniSertifikat } from "./CommonTypes/ZahteviZaDigitalniSertifikat";

export interface Izvestaj {
	"iz:BrojPodnetihDokumenata": number;
	"iz:KolicnaDozaPoRednomBroju": Array<KolicnaDozaPoRednomBroju>;
	"iz:PeriodIzvestaja": PeriodIzvestaja;
	"iz:RaspodelaDozaPoProizvodjacu": Array<RaspodelaDozaPoProizvodjacu>;
	"iz:UkupanBrojDatihDoza": number;
	"iz:ZahteviZaDigitalniSertifikat": ZahteviZaDigitalniSertifikat;
}
