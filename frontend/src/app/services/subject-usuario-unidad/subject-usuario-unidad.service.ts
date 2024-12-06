import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { UsuarioUnidadRequest } from '../../models/UsuarioUnidadRequest';
import {UsuarioRequest} from "../../models/UsuarioRequest";

@Injectable({
  providedIn: 'root'
})
export class SubjectUsuarioUnidadService {

    // Se supone que Subject deberia ser injectado porque Angular ya lo tiene en su IoC Container
  // pero bueno

  usuarioUnidad: UsuarioRequest = {
    id: 0,
    nombres: '',
    materno: '',
    paterno: '',
    correo: '',
    ci: ''
  };

  private subject$ = new BehaviorSubject<UsuarioRequest>(this.usuarioUnidad);

  obtenerObservable(): Observable<UsuarioRequest> {
      return this.subject$.asObservable();
  }

  publicarDatos(valor: UsuarioRequest): void {
      this.subject$.next(valor);
  }

}
