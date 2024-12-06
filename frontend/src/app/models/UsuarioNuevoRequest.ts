export interface UsuarioNuevoRequest {
  nombres: string;
  materno: string;
  paterno: string;
  correo: string;
  ci: string;

  idRol: number;
  idUni: number;
  idCargo: number;

  idResponsable: number;
  idDirector: number;
}

