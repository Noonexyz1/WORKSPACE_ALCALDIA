export interface UsuarioResponse {
  id: number,
  fkUsuario: number,
  fkUnidad: number,
  fkRol: number,
  nombreRol: string,
  dashConfig: string,
  fkCargo: number,
  fkResponsable: number
}
