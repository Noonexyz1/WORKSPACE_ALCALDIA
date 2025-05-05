import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SubjectIdSolicitudService {

  private subject$ = new BehaviorSubject<number>(0);

  obtenerObservable(): Observable<number> {
    return this.subject$.asObservable();
  }

  publicarDatos(valor: number): void {
    this.subject$.next(valor);
  }

}
