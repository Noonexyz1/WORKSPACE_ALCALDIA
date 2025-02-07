export interface UsuarioUnidadResponse {
  // Curiosamente esta interfaz es compatible con UsuarioUnidadEditRequest
  // al momento de publicar datos

  // Es como si las interfazes de atributos matcheen por si solos
  // atributo a atributo, sin tener que mapear
  id: number;
  isActive: boolean;

  idUser: number;
  nombres: string;
  materno: string;
  paterno: string;
  correo: string;
  ci: string;

  nombreRol: string;
  nombreUnidad: string;
  nombreCargo: string;

  idRol: number;
  idUni: number;
  idCargo: number;
  idResponsable: number;
  idDirector: number;
}
