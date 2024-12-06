import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {RowSolicitud} from "../../models/RowSolicitud";

@Injectable({
  providedIn: 'root'
})
export class SubjectDocumentoService {

  // Se supone que Subject deberia ser injectado porque Angular ya lo tiene en su IoC Container
  // pero bueno

  usuarioUnidad: RowSolicitud = {
    nombreDocumento: '',
    nroPaginas: 0,
    nroCopias: 0,
    tamanoPagina: '',
    anversoReverso: '',
    colorFotocopia: ''
  };

  private subject$ = new BehaviorSubject<RowSolicitud>(this.usuarioUnidad);

  obtenerObservable(): Observable<RowSolicitud> {
    return this.subject$.asObservable();
  }

  publicarDatos(valor: RowSolicitud): void {
    this.subject$.next(valor);
  }
}
