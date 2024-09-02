import { DashboardConfig } from "./DashboardConfig";
import { Rol } from "./Rol";

export interface Usuario {
    id: number;
    nombres: string;
    apellidos: string;
    fk_responsable: Usuario | null;
    fk_rol: Rol;
    listDashConfig: DashboardConfig[];
}