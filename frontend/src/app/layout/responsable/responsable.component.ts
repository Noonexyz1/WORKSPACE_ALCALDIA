import {Component, OnInit} from '@angular/core';
import {RouterLink, RouterLinkActive, RouterOutlet} from '@angular/router';
import { ModoDarkService } from '../../utils/services/modo-dark/modo-dark.service';
import { NavBarComponent } from "../../share/nav-bar/nav-bar.component";
import {UrlsProperties} from "../../utils/enums/UrlsProperties";
import {catchError, map, of} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {SeparadorVersionComponent} from "../../share/separador-version/separador-version.component";

@Component({
  selector: 'app-responsable',
  standalone: true,
    imports: [RouterOutlet, RouterLink, NavBarComponent, RouterLinkActive, SeparadorVersionComponent],
  templateUrl: './responsable.component.html'
})
export class ResponsableComponent implements OnInit{


  constructor(
    private modoDarkService: ModoDarkService,
    private http: HttpClient){
  }

  ngOnInit(): void {
    this.modoDarkService.metodoModoDark();
  }

  isVisible: boolean = false;
  botonDescargarReporte() {
    const fechaFormateada: string = this.getCurrentMonthYear(); // Ejemplo: "03/2025"

    this.isVisible = !this.isVisible;

    this.http.get(
      UrlsProperties.PATH_REPORTE_PDF,
      { responseType: 'blob' }
    ).pipe( // Cambiar el tipo de respuesta
      map((response: Blob) => {
        this.descargarPDF("reporteDelMes_" + fechaFormateada + ".pdf", response);
        this.isVisible = !this.isVisible;
      }),
      catchError(error => {
        this.errorDescargaPDF("reporte_" + fechaFormateada + ".pdf", error);
        return of(null);
      })
    ).subscribe();
  }

  getCurrentMonthYear(): string {
    const today = new Date();
    const month = (today.getMonth() + 1).toString().padStart(2, '0'); // +1 porque los meses van de 0-11
    const year = today.getFullYear();
    return `${month}/${year}`;
  }

  descargarPDF(nombrePdf: string, response: Blob): void {
    const blob = new Blob([response], { type: 'application/pdf' });
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = nombrePdf;
    a.click();
    window.URL.revokeObjectURL(url);
  }

  errorDescargaPDF(nombrePdf: string, error: any): void {
    console.error('Error en la petición:', error);
    alert('Hubo un ERROR al generar ' + nombrePdf);
  }

}
