import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RootNavigateService {

  mapa: Map<string, string> = new Map();

  constructor() {
    // Agregar elementos
    this.mapa.set('Administrador', '/administrador/listaDeUsuarios');
    this.mapa.set('Operador', '/operador/listaDeSolicitudesPendientes');
    this.mapa.set('Solicitante', '/solicitante/misSolicitudes');
    this.mapa.set('Responsable', '/responsable/solicitudesPendienteUnidad');
    this.mapa.set('Login', '/login');
  }

  valorParaNavegar(valor: string): string {
    let valorParaNavegar: string = '';
    for (let [clave, ruta] of this.mapa) {
      if (clave === valor) {
        valorParaNavegar = ruta;
        break;
      }
    }
    return valorParaNavegar;
  }
}
