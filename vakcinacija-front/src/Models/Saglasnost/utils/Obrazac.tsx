import { PrivremeneKontraindikacije } from "./PrivremeneKontraindikacije";
import { VakcineInfo } from "./VakcineInfo";

export interface Obrazac {
    "sa:VakcineInfo": Array<VakcineInfo>;
    "sa:PrivremeneKontraindikacije": PrivremeneKontraindikacije;
    "sa:TrajneKontraindikacije": string;
}