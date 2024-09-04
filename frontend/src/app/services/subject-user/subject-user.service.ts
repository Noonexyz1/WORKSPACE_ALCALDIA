import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Usuario } from '../../model/Usuario';

@Injectable({ providedIn: 'root' })
export class SubjectUserService {

  // Se supone que Subject deberia ser injectado porque Angular ya lo tiene en su IoC Container
  // pero bueno
  private subject$ = new Subject<Usuario>();

  constructor() { }

  obtenerObservable(): Observable<Usuario> {
      return this.subject$.asObservable();
  }

  publicarDatos(valor: Usuario): void {
      this.subject$.next(valor);
  }
}
