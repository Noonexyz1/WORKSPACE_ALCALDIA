import {HttpClient} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {SolicitudRequest} from '../../utils/models/SolicitudRequest';
import {catchError, map, of} from 'rxjs';
import {UsuarioResponse} from '../../utils/models/UsuarioResponse';
import {LocalStorageService} from '../../utils/services/local-storage/local-storage.service';
import {RowSolicitud} from '../../utils/models/RowSolicitud';
import {SubjectDocumentoService} from "../../utils/services/subject-documento/subject-documento.service";
import {UrlsProperties} from "../../utils/enums/UrlsProperties";
import {RootNavigateService} from "../../utils/services/root-navigate/root-navigate.service";
import {numeroMayorACeroValidator} from "../../utils/extra/Validation";

@Component({
  selector: 'app-nueva-solicitud',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './nueva-solicitud.component.html'
})
export class NuevaSolicitudComponent implements OnInit {

  private http: HttpClient;
  private formBuilder: FormBuilder;
  private localStorage: LocalStorageService;

  usuario: UsuarioResponse = new UsuarioResponse();

  solicitudForm: FormGroup;

  nroDeDocumentos: number = 0;
  estadoNroDocumentos: boolean = false;

  listSolicitud: RowSolicitud[] = []

  private observable: SubjectDocumentoService;
  private rootNavigateService: RootNavigateService;
  listaTamano: string[] = [];
  listaAnverRever: string[] = [];
  listaColor: string[] = [];

  constructor(http: HttpClient,
              formBuilder: FormBuilder,
              localStorage: LocalStorageService,
              observable: SubjectDocumentoService,
              rootNavigateService: RootNavigateService) {

    this.observable = observable;
    this.rootNavigateService = rootNavigateService;
    this.http = http;
    this.formBuilder = formBuilder;
    this.localStorage = localStorage;

    this.solicitudForm = this.formBuilder.group({
      fkUsuarioSolicitante: [],
      cite: ['', Validators.required],
      descripcion: ['', Validators.required],
      nroDeDocumento: ['', [Validators.required, numeroMayorACeroValidator]],
      documentos: this.formBuilder.array([]), // FormArray para los documentos
    });

    this.listaDeTamano();
    this.listaDeAnverRever();
    this.listaDeColor();

  }

  ngOnInit(): void {
    this.observable.obtenerObservable().subscribe((nuevoDocumento: RowSolicitud) => {
      if (nuevoDocumento.tamanoPagina) {
        this.listSolicitud.push(nuevoDocumento);
      }
    });
  }

  listaDeTamano(): void {
    this.http.get<string[]>(UrlsProperties.PATH_LIST_TAM)
      .pipe(
        map((response: string[]) => {
          this.listaTamano = response;
        }),
        catchError(error => {
          console.error('Error en la petición:', error);
          alert('Hubo un error al traer los cargos');
          return of(null); // Retornar un observable vacío en caso de error
        })
      ).subscribe();
  }

  listaDeAnverRever(): void {
    this.http.get<string[]>(UrlsProperties.PATH_LIST_ANVER)
      .pipe(
        map((response: string[]) => {
          this.listaAnverRever = response;
        }),
        catchError(error => {
          console.error('Error en la petición:', error);
          alert('Hubo un error al traer los cargos');
          return of(null); // Retornar un observable vacío en caso de error
        })
      ).subscribe();
  }

  listaDeColor(): void {
    this.http.get<string[]>(UrlsProperties.PATH_LIST_COLOR)
      .pipe(
        map((response: string[]) => {
          this.listaColor = response;
        }),
        catchError(error => {
          console.error('Error en la petición:', error);
          alert('Hubo un error al traer los cargos');
          return of(null); // Retornar un observable vacío en caso de error
        })
      ).subscribe();
  }

  get documentos(): FormArray {
    return this.solicitudForm.get('documentos') as FormArray;
  }

  agregarDocumentosAlFormArray(nroDeDocumentos: number): void {
    const documentosArray = this.solicitudForm.get('documentos') as FormArray;
    documentosArray.clear(); // Limpiar el FormArray antes de agregar nuevos documentos

    for (let i = 0; i < nroDeDocumentos; i++) {
      documentosArray.push(this.crearFormGroupDocumento());
    }
  }

  crearFormGroupDocumento(): FormGroup {
    return this.formBuilder.group({
      nombreDocumento: ['', Validators.required],
      nroPaginas: ['', [Validators.required, numeroMayorACeroValidator]],
      nroCopias: ['', [Validators.required, numeroMayorACeroValidator]],
      tamanoPagina: ['', Validators.required],
      anversoReverso: ['', Validators.required],
      colorFotocopia: ['', Validators.required]
    });
  }


  isLoading: boolean = false;

  botonNuevaSolicitud(): void {
    this.isLoading = !this.isLoading;

    this.usuario = this.localStorage.getItem('userData');

    // Obtener los documentos del FormArray
    const documentosArray = this.solicitudForm.get('documentos') as FormArray;
    this.listSolicitud = documentosArray.value; // Capturar los valores de los documentos

    const solicitudRequest: SolicitudRequest = {
      fkUsuarioSolicitante: this.usuario.id,
      cite: this.solicitudForm.get('cite')?.value,
      descripcion: this.solicitudForm.get('descripcion')?.value,
      listDetalleSolicitud: this.listSolicitud,
    };



    // Enviar la solicitud
    this.http.post<SolicitudRequest>(
      UrlsProperties.PATH_CREATE_SOLICITUD,
      solicitudRequest
    ).subscribe({
      next: () => {
        // Reiniciar el estado del componente
        this.solicitudForm.reset();
        documentosArray.clear(); // Limpiar el FormArray
        this.estadoNroDocumentos = false;
        this.nroDeDocumentos = 0;

        this.isLoading = !this.isLoading;
        // Redirigir al usuario
        this.rootNavigateService.valorParaNavegar('SolicitantePendientes')
      },
      error: (error) => {
        // Manejo de errores (opcional)
        console.error('Error en la petición:', error);
        alert('Hubo un error al enviar la solicitud');
      }
    });
  }

  botonSetNumeroDeCopias(): void {
    this.nroDeDocumentos = this.solicitudForm.get('nroDeDocumento')?.value;
    this.agregarDocumentosAlFormArray(this.nroDeDocumentos); // Agregar documentos al FormArray
    this.estadoNroDocumentos = true;
  }
}
