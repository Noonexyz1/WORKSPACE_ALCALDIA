import {Component} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {SolicitudResponse} from '../../utils/models/SolicitudResponse';
import {catchError, map, of} from 'rxjs';
import {UsuarioResponse} from '../../utils/models/UsuarioResponse';
import {LocalStorageService} from '../../utils/services/local-storage/local-storage.service';
import {PageProperties} from "../../utils/models/PageProperties";
import {UrlsProperties} from "../../utils/enums/UrlsProperties";
import {PageRequest} from "../../utils/models/PageRequest";
import {PageResponse} from "../../utils/models/PageResponse";
import {QuillModule} from "ngx-quill";
import {FormsModule} from "@angular/forms";
import {
  ItemPopover,
  PaginaData,
  TablaResponsableComponent
} from "../../share/tabla-responsable/tabla-responsable.component";
import {ModelMapperService} from "../../utils/mapper/model-mapper.service";
import {
  iconoComunicacionInterna,
  iconoEliminarSolicitud, iconoInformeSolicitud,
  iconoSolicitudFotocopia
} from "../../utils/icons/IconsSVG";
import {DomSanitizer, SafeHtml} from "@angular/platform-browser";

@Component({
  selector: 'app-lista-soli-solicitante-pendiente',
  standalone: true,
  imports: [
    QuillModule,
    FormsModule,
    TablaResponsableComponent
  ],
  templateUrl: './lista-soli-solicitante-pendiente.component.html'
})
export class ListaSoliSolicitantePendienteComponent {

  //DATOS PARA EL COMPONENTE TABLA
  tituloDeTabla: string = "Lista de solicitudes pendientes";
  listTituloTabla: string[] = ["Accion", "Id", "Cite", "Fecha", "Descripcion"];
  //Inicializamos por defecto este atributo para que se cambien a lo largo de la vida del componente
  pagina: PaginaData = {
    page: 0,
    size: 10, // Valor por defecto más lógico
    sortBy: "id", // Campo por defecto común
    direction: "ASC", // Dirección por defecto
    content: [],
    totalPages: 1,
    totalElements: 1
  };
  itemPopoverList: ItemPopover[] = [];




  // CONFIGURACION DEL EDITOR QUILL
  editorContent: string = '';

  quillConfig = {
    toolbar: [
      ['bold', 'italic', 'underline', 'strike'],
      [{ 'list': 'ordered'}, { 'list': 'bullet' }]
    ],
    clipboard: {
      matchVisual: true,  // Cambia a true para mejor preservación de formato
      allowed: {
        tags: ['p', 'b', 'i', 'u', 's', 'ol', 'ul', 'li', 'br'] // Elementos permitidos
        //attributes: ['style'] // Atributos permitidos (opcional)
      },
      magicPasteLinks: true,  // Convierte URLs en links automáticamente
      keepSelection: true    // Mantiene la selección después de pegar
    },
    keyboard: {
      bindings: {
        paste: {  // Manejo especial para pegado
          key: 'V',
          metaKey: true
        }
      }
    }
  };




  //ESTADOS GENERALES
  hayInformeModal: boolean = false;
  usuario: UsuarioResponse = new UsuarioResponse();
  pageProperties: PageProperties = new PageProperties();
  noHayInformeModal: boolean = false;
  idSolicitudTemporal: number = 0;





  constructor(
    private readonly http: HttpClient,
    private readonly localStorage: LocalStorageService,
    private readonly modelMapper: ModelMapperService,
    private readonly sanitizer: DomSanitizer) {


    this.usuario = this.localStorage.getItem('userData');

    this.itemPopoverList = [
      {
        icono: this.getSafeSvg(iconoSolicitudFotocopia),
        opcion: 'Solicitud de fotocopia PDF',
        //Las firmas son iguales jaja se puede enviar directamente el metodo pero lo voy a dejar asi
        accion: (idSolicitud: string) => {this.botonSolicitudFotocopiaPDF(+idSolicitud)}
      },
      {
        icono: this.getSafeSvg(iconoComunicacionInterna),
        opcion: 'Comunicacion interna PDF',
        //Las firmas son iguales jaja se puede enviar directamente el metodo pero lo voy a dejar asi
        accion: (idSolicitud: string) => {this.botonComunicacionInternaPDF(+idSolicitud)}
      },
      {
        icono: this.getSafeSvg(iconoInformeSolicitud),
        opcion: 'Informe de solicitud PDF',
        //Las firmas son iguales jaja se puede enviar directamente el metodo pero lo voy a dejar asi
        accion: (idSolicitud: string) => {this.botonInformePDF(+idSolicitud)}
      },
      {
        icono: this.getSafeSvg(iconoEliminarSolicitud),
        opcion: 'Eliminar solicitud',
        //Las firmas son iguales jaja se puede enviar directamente el metodo pero lo voy a dejar asi
        accion: (idSolicitud: string) => {this.botonEliminarSolicitudById(+idSolicitud)}
      },
    ];

    this.listarSolicitudes();

  }








  //Lo iniciamos en el constructor
  listarSolicitudes(): void {
    const body: PageRequest = {
      id: this.usuario.id,
      page: this.pageProperties.currentPage,
      size: this.pageProperties.pageSize,
      sortBy: this.pageProperties.sortBy,
      direction: this.pageProperties.direction
    }

    this.http.post<PageResponse<SolicitudResponse>>(
      UrlsProperties.PATH_LIST_SOLIC,
      body
    ).pipe(
      map((response: PageResponse<SolicitudResponse>) => {

        this.pagina = this.modelMapper
          .pageResponseSoliToPaginaDataResponsable(response, this.itemPopoverList);

      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al listar las solicitudes de usuario');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();

  }

  botonSolicitudFotocopiaPDF(idSolicitud: number): void {
    const url = UrlsProperties.PATH_SOLICITUD_PDF + idSolicitud;

    // Recibimos la peticion
    this.http.get(
      url,
      { responseType: 'blob' }
    ).pipe( // Cambiar el tipo de respuesta
      map((response: Blob) => {
        this.descargarPDF("solicitud_" + idSolicitud + ".pdf", response);
      }),
      catchError(error => {
        this.errorDescargaPDF("solicitud_" + idSolicitud + ".pdf", error);
        return of(null);
      })
    ).subscribe();
  }

  botonComunicacionInternaPDF(idSolicitud: number): void {
    const url = UrlsProperties.PATH_COMUINTERNA_PDF + idSolicitud;

    // Recibimos la peticion
    this.http.get(
      url,
      { responseType: 'blob' }
    ).pipe( // Cambiar el tipo de respuesta
      map((response: Blob) => {
        this.descargarPDF("comunicacion_" + idSolicitud + ".pdf", response);
      }),
      catchError(error => {
        this.errorDescargaPDF("comunicacion_" + idSolicitud + ".pdf", error)
        return of(null);
      })
    ).subscribe();
  }

  botonInformePDF(idSolicitud: number) {

    //Primero verificamos si este ID realmente necesita Informe o no
    const url = UrlsProperties.PATH_IS_INFORME + idSolicitud;
    this.http.get<boolean>(url).subscribe({
      next: (resp: boolean) => {

        if (resp) {
          //Si esto es verdad mostramos el modal del editor
          this.hayInformeModal = resp;
          this.idSolicitudTemporal = idSolicitud;

        } else {
          //Si no es verdad entonces mostrar el modal de Que esta solicitud no necesita Informe :D
          this.noHayInformeModal = true;

        }

      },
      error: error => {
        console.error('Error en la peticion al evaluar si la solicitud necesita Informe :', error);
        alert("Error en la peticion al verificar si solicitud necesita informe");
      }
    });

  }

  botonEliminarSolicitudById(idSoliciud: number): void {
    let url: string = UrlsProperties.PATH_ELIMINAR_SOLIC + idSoliciud;
    this.http.get(
      url
    ).pipe(
      map(() => {
        window.location.reload();
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al listar las solicitudes de usuario');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();
  }

  private getSafeSvg(svg: string): SafeHtml {
    return this.sanitizer.bypassSecurityTrustHtml(svg);
  }

  // Estos son metodos que seran disparados en cuanto se recibe un evento
  botonBuscarSolicitudById(id: number): void {
    //Con este id emitido hacer tal...
    const body: PageRequest = {
      id: id,
      page: this.pageProperties.currentPage,
      size: this.pageProperties.pageSize,
      sortBy: this.pageProperties.sortBy,
      direction: this.pageProperties.direction
    }

    this.http.post<PageResponse<SolicitudResponse>>(
      UrlsProperties.PATH_LIST_SOLIC,
      body
    ).pipe(
      map((response: PageResponse<SolicitudResponse>) => {

        this.pagina = this.modelMapper
          .pageResponseSoliToPaginaDataResponsable(response, this.itemPopoverList);

      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al listar las solicitudes de usuario');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();

  }

  botonTrashPublisher() {
    this.listarSolicitudes();
  }

  // Estos son metodos que seran disparados en cuanto se recibe un evento
  goToPage(page: number): void {
    this.pagina.page = page;
    this.listarSolicitudes();
  }

  // Estos son metodos que seran disparados en cuanto se recibe un evento
  goToPreviousPage(newPage: number): void {
    this.pagina.page = newPage;
    this.listarSolicitudes();
  }

  // Estos son metodos que seran disparados en cuanto se recibe un evento
  goToNextPage(newPage: number): void {
    this.pagina.page = newPage;
    this.listarSolicitudes();
  }





  botonGenerarInformeSolicitudPdf() {
    if (!this.editorContent) {
      alert('Por favor ingrese el contenido de la carta');
      return;
    }

    const url = UrlsProperties.PATH_INFORSOLI_PDF + this.idSolicitudTemporal;
    this.http.post(
      url,
      { text: this.editorContent },
      {
        responseType: 'blob',
        headers: { 'Content-Type': 'application/json' }
      }
    ).subscribe({
      next: (pdfBlob: Blob) => {
        this.descargarPDF("informe_" + this.idSolicitudTemporal + ".pdf", pdfBlob);
        this.hayInformeModal = !this.hayInformeModal;

        this.idSolicitudTemporal = 0;
      },
      error: (error) => {
        this.errorDescargaPDF("informe_" + this.idSolicitudTemporal + ".pdf", error);
        return of(null);
      }
    });

  }

  botonCerrarModalInforme(): void {
    this.hayInformeModal = !this.hayInformeModal;
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

  botonCerrarModalInformativo() {
    this.noHayInformeModal = !this.noHayInformeModal;
  }

}
