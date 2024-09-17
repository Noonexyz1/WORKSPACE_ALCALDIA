import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RootNavigateService {

  mapa: Map<string, string> = new Map();

  constructor() {
    // Agregar elementos
    this.mapa.set('administrador', '/administrador/listaDeUsuarios');
    this.mapa.set('operador', '/operador/listaDeSolicitudes');
    this.mapa.set('solicitante', '/solicitante/misSolicitudes');
    this.mapa.set('responsable', '/responsable/solicitudesUnidad');
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
