import { Injectable } from '@angular/core';
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class RootNavigateService {

  mapa: Map<string, string> = new Map();

  constructor(private router: Router) {
    // Agregar elementos
    this.mapa.set('Administrador', '/administrador/listaDeUsuarios');
    this.mapa.set('Operador', '/operador/listaDeSolicitudesPendientes');
    this.mapa.set('Solicitante', '/solicitante/misSolicitudesPendientes');
    this.mapa.set('Responsable', '/responsable/solicitudesPendienteUnidad');
    this.mapa.set('Login', '/login');
  }

  valorParaNavegar(valor: string): void {
    let valorParaNavegar: string = '';
    for (let [clave, ruta] of this.mapa) {
      if (clave === valor) {
        valorParaNavegar = ruta;
        break;
      }
    }
    this.router.navigate([valorParaNavegar]);
  }
}
