import {AfterViewInit, Component, Input, ViewChild} from '@angular/core';
import {SolicitudResponResponse} from '../../../../models/SolicitudResponResponse';
import {AprobacionSoliRequest} from '../../../../models/AprobacionSoliRequest';
import {HttpClient} from '@angular/common/http';
import {SubjectUserLoginService} from '../../../../services/subject-user-login/subject-user-login.service';
import {UsuarioResponse} from '../../../../models/UsuarioResponse';
import {catchError, map, of} from 'rxjs';
import {Router} from '@angular/router';
import {RootNavigateService} from '../../../../services/root-navigate/root-navigate.service';
import {LocalStorageService} from '../../../../services/local-storage/local-storage.service';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {PageRequestID} from "../../../../models/PageRequestID";
import {DetalleSolicitudExtendidoResponse} from "../../../../models/DetalleSolicitudExtendidoResponse";
import {DetalleSolicitudCotizadoResponse} from "../../../../models/DetalleSolicitudCotizadoResponse";
import {RowSolicitudExtendComponent} from "./row-solicitud-extend/row-solicitud-extend.component";
import {CredencialRequest} from "../../../../models/CredencialRequest";

@Component({
  selector: 'app-row-table-responsable-pendiente',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    RowSolicitudExtendComponent
  ],
  templateUrl: './row-table-responsable-pendiente.component.html',
  styleUrl: './row-table-responsable-pendiente.component.css'
})
export class RowTablePendienteResponsableComponent implements AfterViewInit{

  @Input()
  solicitud!: SolicitudResponResponse;

  @ViewChild(RowSolicitudExtendComponent)
  rowSolicitudExtendComponent!: RowSolicitudExtendComponent;

  estadoModal = false;

  private http: HttpClient;
  private observable: SubjectUserLoginService;
  private router: Router;
  private rootNavigateService: RootNavigateService
  private localStorage: LocalStorageService;

  usuario: UsuarioResponse = {
    id: 0,
    fkUsuario: 0,
    fkUnidad: 0,
    fkRol: 0,
    nombreRol: '',
    dashConfig: '',
    fkCargo: 0,
    fkResponsable: 0
  };

  detSoliExtendidoResponse: DetalleSolicitudExtendidoResponse = {
    idSolicitud: 0,
    cite: '',
    fecha: '',
    descripcion: '',
    detalleSolicitudResponses: [],
  };

  detalleSolicitudCotizado: DetalleSolicitudCotizadoResponse[] = [];

  constructor(http: HttpClient,
              observable: SubjectUserLoginService,
              router: Router,
              rootNavigateService: RootNavigateService,
              localStorage: LocalStorageService) {

    this.http = http;
    this.observable = observable;
    this.router = router;
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
    // Mostrar el objeto dentro del alert (convertido a formato de texto)
    alert("Estos son los datos a enviarse: " + JSON.stringify(this.detalleSolicitudCotizado));

    const url = 'http://localhost:8081/responsable/cotizarAutorizarSolicitud'; // URL de tu API

    // Extraer los valores del formulario
    const soliCotizadoList: DetalleSolicitudCotizadoResponse[] = this.detalleSolicitudCotizado;

    //Aqui hacer la peticion POST a mi servidor deberia tener la lista
    // Recibimos la peticion
    this.http.post<DetalleSolicitudCotizadoResponse[]>(url, soliCotizadoList).pipe(
      map(() => {
        //TODO, la funcionalidad de esos botones (2) se deben implementar aqui
        //TODO, y quitar esos dos de la vista

        let toNavegate = this.rootNavigateService.valorParaNavegar('Responsable');
        this.router.navigate([toNavegate]);
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al enviar las solicitudes cotizadas');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();

  }
}
