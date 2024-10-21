export interface UsuarioUnidadRequest {
    id: number;
    isActive: boolean;

    idUser: number;
    nombres: string;
    apellidos: string;
    correo: string;

    idUni: number;
    idRol: number;
}