import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, map, of } from 'rxjs';
import { PageRequestID } from '../../../models/PageRequestID';
import { UsuarioResponse } from '../../../models/UsuarioResponse';
import { SubjectUserLoginService } from '../../../services/subject-user-login/subject-user-login.service';
import { LocalStorageService } from '../../../services/local-storage/local-storage.service';
import {FinalizacionResponse} from "../../../models/FinalizacionResponse";
import {PageProperties} from "../../../models/PageProperties";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

@Component({
  selector: 'app-lista-soli-responsable',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './lista-soli-responsable-finalizada.component.html',
  styleUrl: './lista-soli-responsable-finalizada.component.css'
})
export class ListaSoliFinalizadaResponsableComponent implements OnInit{

  private http: HttpClient;
  private observable: SubjectUserLoginService;
  private localStorage: LocalStorageService;

  listSolicitudFinal: FinalizacionResponse[] = [];

  usuario: UsuarioResponse = {
    id: 0,
    fkUsuario: 0,
    fkUnidad: 0,
    fkRol: 0,
    nombreRol: '',
    dashConfig: '',
    fkCargo: 0,
    fkResponsable: 0,
    nombreUsuario: '',
    apellidoUsuario: ''
  };

  constructor(http: HttpClient,
              observable: SubjectUserLoginService,
              localStorage: LocalStorageService){

    this.http = http;
    this.observable = observable;
    this.localStorage = localStorage;
  }

  ngOnInit(): void {
    this.observable
      .obtenerObservable()
      .subscribe((datos) => {
        this.usuario = datos;
      });
    this.usuario = this.localStorage.getItem('userData');
    this.listarSolicitudes();
  }

  listarSolicitudes(): void {
    const url = 'http://localhost:8081/responsable/verSolicitudesFinalizadas';
    this.usuario = this.localStorage.getItem('userData');

    const body: PageRequestID = {
      idUsuarioUnidad: this.usuario.id,
      page: 0,
      size: 100,
      byColumName: ""
    }

    this.http.post<FinalizacionResponse[]>(url, body).pipe(
      map((response: FinalizacionResponse[]) => {
        console.log(response);
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

  pageProperties: PageProperties = new PageProperties();
  listaConsecutiva: number[] = Array.from(
    {
      length: this.pageProperties.totalPages
    },
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

  botonDescargoSolicitud(solicitudFinalizada: number): void {
    const usuario: UsuarioResponse = this.localStorage.getItem('userData');
    //"/exportOrdenParaFotocopiaDPF/{idSolicitud}/{idSolicitante}/{idResponsable}"
    const url = 'http://localhost:8081/responsable/exportReporteDPF/' + solicitudFinalizada + '/' + usuario.id + '/2';

    // Recibimos la peticion
    this.http.get(url, { responseType: 'blob' }).pipe( // Cambiar el tipo de respuesta
      map((response: Blob) => {
        const blob = new Blob([response], { type: 'application/pdf' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'reportePDF.pdf';
        a.click();
        window.URL.revokeObjectURL(url);
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al generar el descargo o reporte PDF');
        return of(null);
      })
    ).subscribe();
  }

}
