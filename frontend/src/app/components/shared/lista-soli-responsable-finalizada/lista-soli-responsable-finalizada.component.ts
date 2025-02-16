import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, map, of } from 'rxjs';
import { PageRequestID } from '../../../models/PageRequestID';
import { UsuarioResponse } from '../../../models/UsuarioResponse';
import { LocalStorageService } from '../../../services/local-storage/local-storage.service';
import {PageProperties} from "../../../models/PageProperties";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {UrlsProperties} from "../../../enums/UrlsProperties";
import {SolicitudResponResponse} from "../../../models/SolicitudResponResponse";

@Component({
  selector: 'app-lista-soli-responsable-finalizada',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './lista-soli-responsable-finalizada.component.html',
  styleUrl: './lista-soli-responsable-finalizada.component.css'
})
export class ListaSoliFinalizadaResponsableComponent{

  private http: HttpClient;
  private localStorage: LocalStorageService;

  listSolicitudFinal: SolicitudResponResponse[] = [];

  usuario: UsuarioResponse = new UsuarioResponse();

  constructor(http: HttpClient,
              localStorage: LocalStorageService){

    this.http = http;
    this.localStorage = localStorage;
    this.usuario = this.localStorage.getItem('userData');
    this.listarSolicitudes();
  }

  listarSolicitudes(): void {
    const body: PageRequestID = {
      idUsuarioUnidad: this.usuario.id,
      page: 0,
      size: 100,
      byColumName: ""
    }

    this.http.post<SolicitudResponResponse[]>(
      UrlsProperties.PATH_LIST_SOLIFINALI,
      body
    ).pipe(
      map((response: SolicitudResponResponse[]) => {
        this.listSolicitudFinal = response;
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al listar las solicitudes para el responsable');
        return of(null); // Retornar un observable vacío en caso de error
      })
    )
    .subscribe();
  }

  botonDescargoSolicitudPDF(solicitudFinalizada: number): void {
    this.http.get(
      UrlsProperties.PATH_REPORTE_PDF + solicitudFinalizada + '/' + this.usuario.id + '/2',
      { responseType: 'blob' }
    ).pipe( // Cambiar el tipo de respuesta
      map((response: Blob) => {
        this.descargarPDF('reportePDF.pdf', response);
      }),
      catchError(error => {
        this.errorDescargaPDF('reportePDF.pdf', error);
        return of(null);
      })
    ).subscribe();
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


  pageProperties: PageProperties = new PageProperties();
  listaConsecutiva: number[] = Array.from(
    { length: this.pageProperties.totalPages },
    (_, index) => index
  );

  goToPage(page: number): void {
    if (page >= 0 && page < this.pageProperties.totalPages) {
      this.pageProperties.currentPage = page;
      this.listarSolicitudes();
    }
  }

  goToPreviousPage(): void {
    if (this.pageProperties.currentPage > 0) {
      this.pageProperties.currentPage--;
      this.listarSolicitudes();
    }
  }

  goToNextPage(): void {
    if (this.pageProperties.currentPage < this.pageProperties.totalPages - 1) {
      this.pageProperties.currentPage++;
      this.listarSolicitudes();
    }
  }

}
