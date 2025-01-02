import {Component, OnInit} from '@angular/core';
import {RowTableSolicitudesComponent} from "./row-table-solicitudes/row-table-solicitudes.component";
import {HttpClient} from '@angular/common/http';
import {SolicitudResponse} from '../../../models/SolicitudResponse';
import {catchError, map, of} from 'rxjs';
import {PageRequestID} from '../../../models/PageRequestID';
import {UsuarioResponse} from '../../../models/UsuarioResponse';
import {LocalStorageService} from '../../../services/local-storage/local-storage.service';
import {PageProperties} from "../../../models/PageProperties";

@Component({
  selector: 'app-lista-de-solicitudes',
  standalone: true,
  imports: [RowTableSolicitudesComponent],
  templateUrl: './lista-de-solicitudes.component.html',
  styleUrl: './lista-de-solicitudes.component.css'
})
export class ListaDeSolicitudesComponent implements OnInit {

  private http: HttpClient;
  private localStorage: LocalStorageService;

  listSolicitud: SolicitudResponse[] = [];

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
              localStorage: LocalStorageService) {

    this.http = http;
    this.localStorage = localStorage;
  }

  ngOnInit(): void {
    this.listarSolicitudes();
  }

  isModalVisible: boolean = false;

  toggleModal(): void {
    this.isModalVisible = !this.isModalVisible;
  }

  botonSolicitudFotocopiaPDF(idSolicitud: number): void {
    const usuario: UsuarioResponse = this.localStorage.getItem('userData');
    //"/exportSolicitudDPF/{idSolicitud}/{idSolicitante}/{idResponsable}"
    const url = 'http://localhost:8081/solicitante/exportSolicitudDPF/' + idSolicitud + '/' + usuario.id + '/2';

    // Recibimos la peticion
    this.http.get(url, { responseType: 'blob' }).pipe( // Cambiar el tipo de respuesta
      map((response: Blob) => {
        const blob = new Blob([response], { type: 'application/pdf' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'solicitudPDF.pdf';
        a.click();
        window.URL.revokeObjectURL(url);
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al generar la solicitud PDF');
        return of(null);
      })
    ).subscribe();
  }

  botonOrdenFotocopiaPDF(idSolicitud: number): void {
    const usuario: UsuarioResponse = this.localStorage.getItem('userData');
    //"/exportOrdenParaFotocopiaDPF/{idSolicitud}/{idSolicitante}/{idResponsable}"
    const url = 'http://localhost:8081/solicitante/exportOrdenParaFotocopiaDPF/' + idSolicitud + '/' + usuario.id + '/2';

    // Recibimos la peticion
    this.http.get(url, { responseType: 'blob' }).pipe( // Cambiar el tipo de respuesta
      map((response: Blob) => {
        const blob = new Blob([response], { type: 'application/pdf' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'ordenParaFotocopia.pdf';
        a.click();
        window.URL.revokeObjectURL(url);
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al generar la orden de fotocopia PDF');
        return of(null);
      })
    ).subscribe();
  }

  botonComunicacionInterna(idSolicitud: number): void {
    const usuario: UsuarioResponse = this.localStorage.getItem('userData');

    //"/exportSolicitudDPF/{idSolicitud}/{idSolicitante}/{idResponsable}"
    const url = 'http://localhost:8081/solicitante/exportComunicacionInternaDPF/' + idSolicitud + '/' + usuario.id + '/2';

    // Recibimos la peticion
    this.http.get(url, { responseType: 'blob' }).pipe( // Cambiar el tipo de respuesta
      map((response: Blob) => {
        const blob = new Blob([response], { type: 'application/pdf' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'comunicacionInterna.pdf';
        a.click();
        window.URL.revokeObjectURL(url);
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al generar la comunicacion interna PDF');
        return of(null);
      })
    ).subscribe();
  }

  listarSolicitudes(): void {
    const url = 'http://localhost:8081/solicitante/verHistorialSolicitudes';

    this.usuario = this.localStorage.getItem('userData');

    const body: PageRequestID = {
      idUsuarioUnidad: this.usuario.id,
      page: 0,
      size: 10,
      byColumName: ''
    }

    this.http.post<SolicitudResponse[]>(url, body).pipe(
      map((response: SolicitudResponse[]) => {
        console.log(response);
        this.listSolicitud = response;
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al listar las solicitudes de usuario');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();

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

}
