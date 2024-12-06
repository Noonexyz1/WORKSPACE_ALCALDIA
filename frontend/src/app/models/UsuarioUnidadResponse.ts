export interface UsuarioUnidadResponse {
    id: number;
    ci: string;
    isActive: boolean;

    idUser: number;
    nombres: string;
    paterno: string;
    materno: string;
    correo: string;

    nombreRol: string;
    nombreUnidad: string;
    nombreCargo: string;

    idRol: number;
    idUni: number;
}
