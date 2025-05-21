export interface UsuarioUnidadEditRequest {
  // Es como si las interfazes de atributos matcheen por si solos
  // atributo a atributo, sin tener que mapear
  id: number;
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

