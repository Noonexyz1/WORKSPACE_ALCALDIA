import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RootNavigateService {

  mapa: Map<string, string> = new Map();

  constructor() {
    // Agregar elementos
    this.mapa.set('Administrador', '/administrador/listaDeUsuarios');
    this.mapa.set('Operador', '/operador/listaDeSolicitudes');
    this.mapa.set('Solicitante', '/solicitante/misSolicitudes');
    this.mapa.set('Responsable', '/responsable/solicitudesPendienteUnidad');
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
