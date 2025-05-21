import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ObservableNotifyService {

  private eventSource: EventSource | undefined;
  private observer: BehaviorSubject<number> = new BehaviorSubject<number>(0);

  pathToSuscribe: string = '';

  constructor() {
    this.iniciarConexion(); // Iniciar SSE automáticamente
  }
  private iniciarConexion(): void {
    if (this.eventSource) {
      this.eventSource.close(); // Cerrar conexión previa si existía
    }

    this.eventSource = new EventSource(this.pathToSuscribe);

    this.eventSource.onmessage = (event: MessageEvent) => {
      const valor = parseInt(event.data, 10);
      this.observer.next(valor); // Emitir el valor a todos los suscriptores
    };

    this.eventSource.onerror = () => {
      console.error("Error en la conexión SSE, intentando reconectar...");
      this.eventSource?.close();
      setTimeout(() => this.iniciarConexion(), 3000); // Reintentar en 3s
    };
  }

  obtenerActualizacion(): Observable<number> {
    return this.observer.asObservable(); // Permite múltiples suscriptores sin crear múltiples conexiones
  }

  cerrarConexion(): void {
    this.eventSource?.close();
  }
}
