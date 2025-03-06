import {Component} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, map, of } from 'rxjs';
import { UsuarioResponse } from '../../../models/UsuarioResponse';
import { SolicitudResponResponse } from '../../../models/SolicitudResponResponse';
import { LocalStorageService } from '../../../services/local-storage/local-storage.service';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {RootNavigateService} from "../../../services/root-navigate/root-navigate.service";
import {PageProperties} from "../../../models/PageProperties";
import {UrlsProperties} from "../../../enums/UrlsProperties";
import {PageRequest} from "../../../models/PageRequest";
import {PageResponse} from "../../../models/PageResponse";
import {numeroMayorACeroValidator} from "../../../validation/Validation";

@Component({
  selector: 'app-lista-soli-responsable-autorizada',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './lista-soli-responsable-autorizada.component.html',
  styleUrl: './lista-soli-responsable-autorizada.component.css'
})
export class ListaSoliAutorizadaResponsableComponent {

  listSolicitud: SolicitudResponResponse[] = [];

  idSolicitudForm: FormGroup;

  usuario: UsuarioResponse = new UsuarioResponse();

  constructor(
    private http: HttpClient,
    private rootNavigateService: RootNavigateService,
    private localStorage: LocalStorageService,
    private formBuilder: FormBuilder){

    this.http = http;
    this.localStorage = localStorage;
    this.rootNavigateService = rootNavigateService;
    this.formBuilder = formBuilder;
    this.idSolicitudForm = this.formBuilder.group({
      id: ['', [Validators.required, numeroMayorACeroValidator]]
    });
    this.usuario = this.localStorage.getItem('userData');
    this.listarSolicitudes();
  }

  isActiveBtnFinalizar: boolean = false;
  botonFinalizarSolicitud(solicitud: SolicitudResponResponse): void {
    if (this.isActiveBtnFinalizar) {
      this.http.post<number>(
        UrlsProperties.PATH_FINALIZAR_SOLI,
        solicitud.idAutorizacion
      ).pipe(
        map(() => {
          this.rootNavigateService.valorParaNavegar('ResponsableFinalizadas');
        }),
        catchError(error => {
          console.error('Error en la petición:', error);
          alert('Hubo un error al guardar la Finalizacion');
          return of(null); // Retornar un observable vacío en caso de error
        })
      ).subscribe();
    }
  }

  botonNotaDeSolicitud(idSolicitud: number): void {
    this.http.get(
      UrlsProperties.PATH_NOTA_PDF + idSolicitud,
      { responseType: 'blob' }
    ).pipe( // Cambiar el tipo de respuesta
      map((response: Blob) => {
        this.descargarPDF("notaPedido_" + idSolicitud + ".pdf", response);
        this.isActiveBtnFinalizar = true;
      }),
      catchError(error => {
        this.errorDescargaPDF("notaPedido_" + idSolicitud + ".pdf", error)
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
    { length: this.pageProperties.totalPages},
    (_, index) => index
  );
  listarSolicitudes(): void {
    const body: PageRequest = {
      id: this.usuario.id,
      page: this.pageProperties.currentPage,
      size: this.pageProperties.pageSize,
      sortBy: this.pageProperties.sortBy,
      direction: this.pageProperties.direction
    }

    this.http.post<PageResponse<SolicitudResponResponse>>(
      UrlsProperties.PATH_LIST_SOLIAPRO,
      body
    ).pipe(
      map((response: PageResponse<SolicitudResponResponse>) => {
        this.pageProperties.currentPage = response.page;
        this.pageProperties.pageSize = response.size;
        this.pageProperties.sortBy = response.sortBy;
        this.pageProperties.direction = response.direction;

        this.listSolicitud = response.content;
        this.pageProperties.totalPages = response.totalPages;
        this.pageProperties.totalElements = response.totalElements;

        this.listaConsecutiva = Array.from(
          {length: this.pageProperties.totalPages},
          (_, index) => index
        );
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al listar las solicitudes para el responsable');
        return of(null); // Retornar un observable vacío en caso de error
      })
    )
    .subscribe();
  }

  botonBuscarSolicitudById(): void {
    const body: PageRequest = {
      id: this.idSolicitudForm.get('id')?.value,
      page: this.pageProperties.currentPage,
      size: this.pageProperties.pageSize,
      sortBy: this.pageProperties.sortBy,
      direction: this.pageProperties.direction
    }

    this.http.post<PageResponse<SolicitudResponResponse>>(
      UrlsProperties.PATH_SOLI_AUTORIBYID,
      body
    ).pipe(
      map((response: PageResponse<SolicitudResponResponse>) => {
        this.pageProperties.currentPage = response.page;
        this.pageProperties.pageSize = response.size;
        this.pageProperties.sortBy = response.sortBy;
        this.pageProperties.direction = response.direction;

        this.listSolicitud = response.content;
        this.pageProperties.totalPages = response.totalPages;
        this.pageProperties.totalElements = response.totalElements;

        this.listaConsecutiva = Array.from(
          {length: this.pageProperties.totalPages},
          (_, index) => index
        );
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al listar las solicitudes pendientes para el responsable');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();

  }


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
