import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {catchError, map, of} from 'rxjs';
import {PageRequestID} from '../../../models/PageRequestID';
import {UsuarioResponse} from '../../../models/UsuarioResponse';
import {SolicitudResponResponse} from '../../../models/SolicitudResponResponse';
import {LocalStorageService} from '../../../services/local-storage/local-storage.service';
import {FormsModule} from "@angular/forms";
import {RowSolicitudExtendComponent} from "./row-table-responsable-pendiente/row-solicitud-extend/row-solicitud-extend.component";
import {PageProperties} from "../../../models/PageProperties";
import {DetalleSolicitudExtendidoResponse} from "../../../models/DetalleSolicitudExtendidoResponse";
import {DetalleSolicitudCotizadoResponse} from "../../../models/DetalleSolicitudCotizadoResponse";
import {RootNavigateService} from "../../../services/root-navigate/root-navigate.service";

@Component({
  selector: 'app-lista-soli-responsable',
  standalone: true,
  imports: [FormsModule, RowSolicitudExtendComponent],
  templateUrl: './lista-soli-responsable-pendiente.component.html',
  styleUrl: './lista-soli-responsable-pendiente.component.css'
})
export class ListaSoliPendienteResponsableComponent implements OnInit {

  private http: HttpClient;
  private localStorage: LocalStorageService;
  private rootNavigateService: RootNavigateService;

  listSolicitud: SolicitudResponResponse[] = [];

  pageProperties: PageProperties = new PageProperties();
  listaConsecutiva: number[] = Array.from(
    {
      length: this.pageProperties.totalPages
    },
    (_, index) => index
  );


  usuario: UsuarioResponse = new UsuarioResponse();

  constructor(http: HttpClient,
              rootNavigateService: RootNavigateService,
              localStorage: LocalStorageService) {

    this.http = http;
    this.localStorage = localStorage;
    this.rootNavigateService = rootNavigateService;
  }

  ngOnInit(): void {
    this.listarSolicitudes();
  }

  listarSolicitudes(): void {
    const url = 'http://localhost:8081/responsable/verSolicitudesPendientes';

    this.usuario = this.localStorage.getItem('userData');

    const body: PageRequestID = {
      idUsuarioUnidad: this.usuario.id,
      page: 0,
      size: 100,
      byColumName: ""
    }

    this.http.post<SolicitudResponResponse[]>(url, body).pipe(
      map((response: SolicitudResponResponse[]) => {
        this.listSolicitud = response;
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

  estadoModal = false;
  isModalVisible: boolean = false;
  detSoliExtendidoResponse: DetalleSolicitudExtendidoResponse = {
    idSolicitud: 0,
    cite: '',
    fecha: '',
    descripcion: '',
    detalleSolicitudResponses: [],
  };

  botonTraerDatosModal(idSolicitud: number): void {
    this.estadoModal = true;
    this.isModalVisible = !this.isModalVisible;
    //Traer el objeto de SolicitudExtendido mediante el ID de solicitud
    const url = 'http://localhost:8081/responsable/verDetalleDeSolicitud/' + idSolicitud;

    this.http.get<DetalleSolicitudExtendidoResponse>(url).pipe(
      map((response: DetalleSolicitudExtendidoResponse) => {
        this.detSoliExtendidoResponse = response;
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al listar los datos para el modal');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();
  }

  toggleModal(): void {
    this.isModalVisible = !this.isModalVisible;
  }

  detalleSolicitudCotizado: DetalleSolicitudCotizadoResponse[] = [];

  onCotizacionRecibida(cotizacion: DetalleSolicitudCotizadoResponse): void {
    console.log('Cotización recibida:', cotizacion);
    this.usuario = this.localStorage.getItem('userData');
    cotizacion.idUsuarioUnidad = this.usuario.id;
    this.detalleSolicitudCotizado.push(cotizacion);
    console.log('Lista actualizada:', this.detalleSolicitudCotizado);
  }

  botonRegistrar(): void {
    const url = 'http://localhost:8081/responsable/cotizarAutorizarSolicitud'; // URL de tu API
    // Extraer los valores del formulario
    const soliCotizadoList: DetalleSolicitudCotizadoResponse[] = this.detalleSolicitudCotizado;

    //Aqui hacer la peticion POST a mi servidor deberia tener la lista
    // Recibimos la peticion
    this.http.post<DetalleSolicitudCotizadoResponse[]>(url, soliCotizadoList).pipe(
      map(() => {
        this.rootNavigateService.valorParaNavegar('Responsable');
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al enviar las solicitudes cotizadas');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();

  }

}
