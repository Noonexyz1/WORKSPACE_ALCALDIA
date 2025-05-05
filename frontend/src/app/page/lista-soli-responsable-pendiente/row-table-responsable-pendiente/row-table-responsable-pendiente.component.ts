import {AfterViewInit, Component, Input, ViewChild} from '@angular/core';
import {SolicitudResponResponse} from '../../../utils/models/SolicitudResponResponse';
import {HttpClient} from '@angular/common/http';
import {UsuarioResponse} from '../../../utils/models/UsuarioResponse';
import {catchError, map, of} from 'rxjs';
import {RootNavigateService} from '../../../utils/services/root-navigate/root-navigate.service';
import {LocalStorageService} from '../../../utils/services/local-storage/local-storage.service';
import {ReactiveFormsModule} from "@angular/forms";
import {DetalleSolicitudExtendidoResponse} from "../../../utils/models/DetalleSolicitudExtendidoResponse";
import {DetalleSolicitudCotizadoResponse} from "../../../utils/models/DetalleSolicitudCotizadoResponse";
import {RowSolicitudExtendComponent} from "./row-solicitud-extend/row-solicitud-extend.component";

@Component({
  selector: 'app-row-table-responsable-pendiente',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    RowSolicitudExtendComponent
  ],
  templateUrl: './row-table-responsable-pendiente.component.html'
})
export class RowTablePendienteResponsableComponent implements AfterViewInit{

  @Input()
  solicitud!: SolicitudResponResponse;

  @ViewChild(RowSolicitudExtendComponent)
  rowSolicitudExtendComponent!: RowSolicitudExtendComponent;

  estadoModal = false;

  private http: HttpClient;
  private rootNavigateService: RootNavigateService
  private localStorage: LocalStorageService;

  usuario: UsuarioResponse = new UsuarioResponse();

  detSoliExtendidoResponse: DetalleSolicitudExtendidoResponse = {
    idSolicitud: 0,
    cite: '',
    fecha: '',
    descripcion: '',
    detalleSolicitudResponses: [],
  };

  detalleSolicitudCotizado: DetalleSolicitudCotizadoResponse[] = [];

  constructor(http: HttpClient,
              rootNavigateService: RootNavigateService,
              localStorage: LocalStorageService) {

    this.http = http;
    this.rootNavigateService = rootNavigateService;
    this.localStorage = localStorage;
  }

  ngAfterViewInit(): void {
    if (this.rowSolicitudExtendComponent) {
      this.rowSolicitudExtendComponent.cotizacionEmitida.subscribe((cotizacion: DetalleSolicitudCotizadoResponse) => {
          this.onCotizacionRecibida(cotizacion);
        });
    }
  }
  onCotizacionRecibida(cotizacion: DetalleSolicitudCotizadoResponse): void {
    console.log('Cotización recibida:', cotizacion);
    this.usuario = this.localStorage.getItem('userData');
    cotizacion.idUsuarioUnidad = this.usuario.id;
    this.detalleSolicitudCotizado.push(cotizacion);
    console.log('Lista actualizada:', this.detalleSolicitudCotizado);
  }

  isModalVisible: boolean = false;
  toggleModal(): void {
    this.isModalVisible = !this.isModalVisible;
  }

  botonTraerDatosModal(): void {
    this.estadoModal = true;
    this.isModalVisible = !this.isModalVisible;
    //Traer el objeto de SolicitudExtendido mediante el ID de solicitud
    const url = 'http://localhost:8081/responsable/verDetalleDeSolicitud/' + this.solicitud.idSolicitud;

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
