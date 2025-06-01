import {Component, EventEmitter, HostListener, Input, Output} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {numeroMayorACeroValidator} from "../../utils/extra/Validation";
import {SafeHtml} from "@angular/platform-browser";

export interface ItemPopover {
  icono: SafeHtml;
  opcion: string;
  //En atributos, solo tienes que declara la firma nada mas jajaj como en Java
  accion: (idSolicitud: string) => void;

  //{} estun objeto, estas diciendo que retorne un objeto ajajaja
  //accion: (idSolicitud: number) => {};
}

export interface RowIntemData {
  accion: ItemPopover[];
  // se necesita que el valor del inidice 0, sea un ID
  columData: string[];
}

export interface PaginaData {
  page: number;            // Número de página actual
  size: number;            // Tamaño de la página
  sortBy: string;          // Campo de ordenamiento
  direction: string;       // Dirección del ordenamiento
  content: RowIntemData[]; // Los elementos de la página actual
  totalPages: number;      // Total de páginas disponibles
  totalElements: number;   // Total de elementos en la BD
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
  isTrashActive: boolean = false;

  @Input() tituloDeTabla: string = "";
  @Input() listTituloTabla: string[] = [];
  @Input() pagina: PaginaData = {
    page: 0,
    size: 0, // Valor por defecto más lógico
    sortBy: '', // Campo por defecto común
    direction: '', // Dirección por defecto
    content: [],
    totalPages: 0,
    totalElements: 0
  };


  //Estos para modificar al componente padre
  //Esto parece un publicador
  @Output() idSolicitudPublisher = new EventEmitter<number>(); //Emite eventos de tipo number
  @Output() idSolicitudNotaPublisher = new EventEmitter<number>(); //Emite eventos de tipo number

  @Output() goToPagePublisher = new EventEmitter<number>(); //Emite eventos de tipo number
  @Output() goToPreviousPagePublisher = new EventEmitter<number>(); //Emite eventos de tipo number
  @Output() goToNextPagePublisher = new EventEmitter<number>(); //Emite eventos de tipo number

  @Output() botonTrashPublisher = new EventEmitter<boolean>(); //Emite eventos de tipo boolean


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
      {length: this.pagina.totalPages},
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

  showPopoverId: null | string | undefined = null;
  togglePopover(solicitudId: string | undefined, event?: MouseEvent): void {
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


  // Como si fuera un adapter
  //Necesito el atributo item para lanzar el metodo correspondiente del padre
  botonAccion(idSolicitud: string, item: ItemPopover): void {
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




  botonTrachCliked() {
    this.botonTrashPublisher.emit();
    this.isTrashActive = false;
  }

  onInputChange($event: Event) {
    const value = ($event.target as HTMLInputElement).value;
    console.log('Valor actual:', value);
    this.isTrashActive = true;
  }
}
