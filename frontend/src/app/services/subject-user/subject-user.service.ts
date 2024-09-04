import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Usuario } from '../../model/Usuario';

@Injectable({ providedIn: 'root' })
export class SubjectUserService {

  private subject$ = new BehaviorSubject<Usuario | null>(null); // Comienza con un valor nulo

  constructor() {
    const storedUser = localStorage.getItem('usuario');
    if (storedUser) {
      this.subject$.next(JSON.parse(storedUser)); // Emitir el usuario guardado al iniciar
    }
  }

  obtenerObservable(): Observable<Usuario | null> {
    return this.subject$.asObservable();
  }

  public publicarDatos(valor: Usuario): void {
    console.log('Publicando en el servicio (BehaviorSubject):', valor);
    this.subject$.next(valor);
    localStorage.setItem('usuario', JSON.stringify(valor)); // Guardar en localStorage
  }
}
