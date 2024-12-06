import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { UsuarioUnidadRequest } from '../../models/UsuarioUnidadRequest';
import { UsuarioResponse } from '../../models/UsuarioResponse';

@Injectable({
  providedIn: 'root'
})
export class SubjectUserLoginService {

  // Se supone que Subject deberia ser injectado porque Angular ya lo tiene en su IoC Container
  // pero bueno

  usuarioUnidad: UsuarioResponse = {
    id: 0,
    fkUsuario: 0,
    fkUnidad: 0,
    fkRol: 0,
    nombreRol: '',
    dashConfig: '',
    fkCargo: 0,
    fkResponsable: 0
  };

  private subject$ = new BehaviorSubject<UsuarioResponse>(this.usuarioUnidad);

  obtenerObservable(): Observable<UsuarioResponse> {
      return this.subject$.asObservable();
  }

  publicarDatos(valor: UsuarioResponse): void {
      this.subject$.next(valor);
  }

}
