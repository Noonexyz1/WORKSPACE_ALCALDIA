import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {UrlsProperties} from "../../utils/enums/UrlsProperties";
import {catchError, map, of} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {DocumentoRetiroResponse} from "../../utils/models/DocumentoRetiroResponse";
import {DocumentoRetiroRequest} from "../../utils/models/DocumentoRetiroRequest";

@Component({
  selector: 'app-registrar-retiro',
  standalone: true,
  imports: [],
  templateUrl: './registrar-retiro.component.html'
})
export class RegistrarRetiroComponent implements OnInit{

  //A partir del ID de solicitud que me pasen, debo mostrar toda la informacion haciendo peticiones
  @Input() idSolicitud: number = 0;

  @Output() botonOrdenPedidoPublisher = new EventEmitter<boolean>();


  //ESTADOS GLOBALES
  listaDeDocumentos2: DocumentoRetiroResponse[] = [];
  documentosSeleccionados: DocumentoRetiroRequest[] = [];



  constructor(
    private readonly http: HttpClient) {

    //Valor por defecto
    this.documentosSeleccionados.push(new DocumentoRetiroRequest());

    // debo tener una lista de documento seleccionados y unicamente renderizarlo de nuevo cada vez haya un nuevo push
    // la primera vez obio no habra resultados, pero eso unicamente se puede arreglar en el HTML diciendo que
    // si es null, entonces que muestre la primerfila con opciones y mensajes de elegir una opcion
  }


  ngOnInit(): void {
    this.iniciarValores();
  }


  iniciarValores(){
    console.log(this.idSolicitud)

    this.http.get<DocumentoRetiroResponse[]>(
      UrlsProperties.PATH_DOCU_RETIRO + this.idSolicitud,
    ).pipe(
      map((response: DocumentoRetiroResponse[]) => {
        this.listaDeDocumentos2 = response;
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        // alert('Hubo un error al obtener Documentos de retiro');
        // Retornar un observable vacío en caso de error
        return of(null);
      })
    ).subscribe();
  }


  inputValue: string = "";
  onInputChange(event: Event) {
    // Obtener el elemento <input> que disparó el evento
    const inputElement = event.target as HTMLInputElement;

    // Obtener el valor del input
    this.inputValue = inputElement.value;
  }

  selectedValue: string = "";
  documentoRetiro: DocumentoRetiroResponse = new DocumentoRetiroResponse();
  onDocumentoSeleccionado(event: Event) {
    const selectElement = event.target as HTMLSelectElement;
    const selectedId = selectElement.value;

    // Buscar el documento correspondiente en tu lista
    const documentoSeleccionado = this.listaDeDocumentos2
      .find(doc => doc.id === parseInt(selectedId, 10));

    if (documentoSeleccionado) {
      this.selectedValue = documentoSeleccionado.id + "";
      this.documentoRetiro = documentoSeleccionado;
    }
  }


  botonGenerarNotaDePedidoPDF() {
    //Aqui se va a evaluar si no hay redundancia con los id de las fotocopias
    console.log(this.documentosSeleccionados)

    // Debo quitar el ultimo espacio en blanco o null de esta cola
    this.botonPop();

    // Esto debe registrar y descargar el pdf inmediatamente despues
    this.http.post(
      UrlsProperties.PATH_REGIS_RETIRODOCU,
      this.documentosSeleccionados,
      { responseType: 'blob' }
    ).subscribe({
      next: (response: Blob) => {
        this.descargarPDF("ordenDeFotocopia.pdf", response);

        this.botonOrdenPedidoPublisher.emit();

      },
      error: error => {
        this.errorDescargaPDF("ordenDeFotocopia.pdf", error);
        return of(null);
      }
    });

  }

  botonPush() {
    if (this.selectedValue != "" && this.inputValue != "") {
      let tamanoAreglo: number = this.documentosSeleccionados.length - 1;

      //Formamos el objeto para agregar a la lista
      let documentoRetiroToPush: DocumentoRetiroRequest = new DocumentoRetiroRequest();
      documentoRetiroToPush.totalCopia = this.documentoRetiro.totalCopia;
      documentoRetiroToPush.totalUsado = this.documentoRetiro.totalUsado;
      documentoRetiroToPush.totalDisponible = this.documentoRetiro.totalDisponible;

      documentoRetiroToPush.nroRetiro = parseInt(this.inputValue, 10);
      documentoRetiroToPush.id = parseInt(this.selectedValue, 10);

      //Aqui agregamos el objeto formado a la lista
      this.documentosSeleccionados[tamanoAreglo] = documentoRetiroToPush;

      this.documentoRetiro = new DocumentoRetiroResponse();

      this.documentosSeleccionados.push(new DocumentoRetiroRequest());

      // Reiniciar los campos del formulario
      this.selectedValue = '';
      this.inputValue = '';
    }
  }

  botonPop() {
    this.documentosSeleccionados.pop();
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
