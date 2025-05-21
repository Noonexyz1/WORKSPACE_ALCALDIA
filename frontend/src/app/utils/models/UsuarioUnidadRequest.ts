export interface UsuarioUnidadRequest {
    id: number;
    isActive: boolean;

    idUser: number;
    nombres: string;
    paterno: string;
    materno: string;
    correo: string;
    ci: string;

    idUni: number;
    idRol: number;
}
