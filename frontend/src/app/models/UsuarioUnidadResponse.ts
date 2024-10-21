export interface UsuarioUnidadResponse {
    id: number;
    isActive: boolean;

    idUser: number;
    nombres: string;
    apellidos: string;
    correo: string;

    nombreRol: string;
    nombreUnidad: string;

    idRol: number;
    idUni: number;
}
