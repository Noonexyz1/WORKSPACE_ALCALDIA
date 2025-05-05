import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {UsuarioUnidadEditRequest} from "../../models/UsuarioUnidadEditRequest";

@Injectable({
  providedIn: 'root'
})
export class SubjectUsuarioUnidadService {

  // Se supone que Subject deberia ser injectado porque Angular ya lo tiene en su IoC Container
  // pero bueno

  usuarioUnidad: UsuarioUnidadEditRequest = {
    id: 0,
    nombres: '',
    materno: '',
    paterno: '',
    correo: '',
    ci: '',

    idRol: 0,
    idUni: 0,
    idCargo: 0,
    idResponsable: 0,
    idDirector: 0,
  };

  private subject$ = new BehaviorSubject<UsuarioUnidadEditRequest>(this.usuarioUnidad);

  obtenerObservable(): Observable<UsuarioUnidadEditRequest> {
    return this.subject$.asObservable();
  }

  publicarDatos(valor: UsuarioUnidadEditRequest): void {
    this.subject$.next(valor);
  }

}
