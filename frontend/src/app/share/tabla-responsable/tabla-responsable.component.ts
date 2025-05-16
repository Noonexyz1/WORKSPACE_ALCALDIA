import {Component, EventEmitter, HostListener, Input, Output} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {SolicitudResponResponse} from "../../utils/models/SolicitudResponResponse";
import {PageResponse} from "../../utils/models/PageResponse";
import {numeroMayorACeroValidator} from "../../utils/extra/Validation";
import {SafeHtml} from "@angular/platform-browser";

export interface ItemPopover {
  icono: SafeHtml;
  opcion: string;
  //En atributos, solo tienes que declara la firma nada mas jajaj como en Java
  accion: (idSolicitud: number) => void;

  //{} estun objeto, estas diciendo que retorne un objeto ajajaja
  //accion: (idSolicitud: number) => {};
}

@Component({
  selector: 'app-tabla-responsable',
  standalone: true,
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './tabla-responsable.component.html'
})
export class TablaResponsableComponent {

  idSolicitudForm: FormGroup;
  listaConsecutiva: number[] = [];

  @Input() tituloDeTabla: string = "";
  @Input() listTituloTabla: string[] = [];
  @Input() pagina: PageResponse<SolicitudResponResponse> = {
    page: 0,
    size: 0,
    sortBy: "",
    direction: "",
    content: [],
    totalPages: 0,
    totalElements: 0
  };
  @Input() listPopoverItem: ItemPopover[] = [];

  //Estos para modificar al componente padre
  //Esto parece un publicador
  @Output() idSolicitudPublisher = new EventEmitter<number>(); //Emite eventos de tipo number
  @Output() idSolicitudNotaPublisher = new EventEmitter<number>(); //Emite eventos de tipo number

  @Output() goToPagePublisher = new EventEmitter<number>(); //Emite eventos de tipo number
  @Output() goToPreviousPagePublisher = new EventEmitter<number>(); //Emite eventos de tipo number
  @Output() goToNextPagePublisher = new EventEmitter<number>(); //Emite eventos de tipo number



  constructor(
    private readonly formBuilder: FormBuilder) {

    this.formBuilder = formBuilder;
    this.idSolicitudForm = this.formBuilder.group({
      id: ['', [Validators.required, numeroMayorACeroValidator]]
    });
  }

  // Actualizar cuando cambie pagina
  ngOnChanges() {
    this.listaConsecutiva = Array.from(
      { length: this.pagina.totalPages },
      (_, index) => index
    );
  }


  @HostListener('document:click', ['$event'])
  onDocumentClick(event: MouseEvent) {
    // Cierra el popover si se hace clic en cualquier parte fuera de él
    if (this.showPopoverId !== null) {
      this.showPopoverId = null;
    }
  }

  showPopoverId: null | number | undefined = null;
  togglePopover(solicitudId: number | undefined, event?: MouseEvent): void {
    if (event) {
      event.stopPropagation();
    }
    this.showPopoverId = this.showPopoverId === solicitudId ? null : solicitudId;
    console.log(solicitudId);
  }




  /* Se ha llevado acabo un evento disparador por el HTML como un BOTON con dicho valor o tipo
  que se emitio como resultado es el MindSet */
  botonBuscarSolicitudById(): void {
    //Aqui debo publicar el id que se ha escrito en el campo
    let id: number = this.idSolicitudForm.get('id')?.value;
    this.idSolicitudPublisher.emit(id);
  }

  /* Se ha llevado acabo un evento disparador por el HTML como un BOTON con dicho valor o tipo
  que se emitio como resultado es el MindSet */
  botonNotaDeSolicitud(idSolicitud: number | undefined): void {
    //Aqui debo publicar el id que se ha escrito en el campo
    let idSolicitudPublic: number | undefined = idSolicitud;
    this.idSolicitudNotaPublisher.emit(idSolicitudPublic);
  }



  // Como si fuera un adapter
  botonAccion(item: ItemPopover, idSolicitud: number): void {
    item.accion(idSolicitud);
  }




  /* Se ha llevado acabo un evento disparador por el HTML como un BOTON con dicho valor o tipo
  que se emitio como resultado es el MindSet */
  goToPage(page: number): void {
    if (page >= 0 && page < this.pagina.totalPages) {
      //this.pagina.page = page;
      //Aqui debo publicar lo que se me esta pasando
      this.goToPagePublisher.emit(page);
    }
  }

  /* Se ha llevado acabo un evento disparador por el HTML como un BOTON con dicho valor o tipo
  que se emitio como resultado es el MindSet */
  goToPreviousPage(): void {
    if (this.pagina.page > 0) {
      this.goToPreviousPagePublisher.emit(this.pagina.page - 1); // Emitir la nueva página
    }
  }

  /* Se ha llevado acabo un evento disparador por el HTML como un BOTON con dicho valor o tipo
  que se emitio como resultado es el MindSet */
  goToNextPage(): void {
    if (this.pagina.page < this.pagina.totalPages - 1) {
      this.goToNextPagePublisher.emit(this.pagina.page + 1); // Emitir la nueva página
    }
  }

}
