import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ObservableService<T> {
  private subject$: BehaviorSubject<T>;

  constructor() {
    // Inicializa con un valor por defecto (necesitarás inicializar propiamente después)
    this.subject$ = new BehaviorSubject<T>({} as T);
  }

  inicializar(valorInicial: T): void {
    this.subject$ = new BehaviorSubject<T>(valorInicial);
  }

  obtenerObservable(): Observable<T> {
    return this.subject$.asObservable();
  }

  publicarDatos(valor: T): void {
    if (!this.subject$) {
      throw new Error('El servicio no ha sido inicializado. Llama a inicializar() primero.');
    }
    this.subject$.next(valor);
  }

  get valorActual(): T {
    return this.subject$.getValue();
  }
}
