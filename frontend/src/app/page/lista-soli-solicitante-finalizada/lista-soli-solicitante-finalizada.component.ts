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
import {
  ItemPopover,
  PaginaData,
  TablaResponsableComponent
} from "../../share/tabla-responsable/tabla-responsable.component";
import {ModelMapperService} from "../../utils/mapper/model-mapper.service";

@Component({
  selector: 'app-lista-soli-solicitante-finalizada',
  standalone: true,
  imports: [
    TablaResponsableComponent
  ],
  templateUrl: './lista-soli-solicitante-finalizada.component.html'
})
export class ListaSoliSolicitanteFinalizadaComponent {




  //DATOS PARA EL COMPONENTE TABLA
  tituloDeTabla: string = "Lista de solicitudes finalizadas";
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





  //ESTADOS GLOBALES
  pageProperties: PageProperties = new PageProperties();
  usuario: UsuarioResponse = new UsuarioResponse();





  constructor(
    private readonly http: HttpClient,
    private readonly localStorage: LocalStorageService,
    private readonly modelMapper: ModelMapperService) {

    this.usuario = this.localStorage.getItem('userData');
    this.listarSolicitudes();
  }




  listarSolicitudes(): void {
    const body: PageRequest = {
      id: this.usuario.id,
      page: this.pageProperties.currentPage,
      size: this.pageProperties.pageSize,
      sortBy: this.pageProperties.sortBy,
      direction: this.pageProperties.direction
    }

    this.http.post<PageResponse<SolicitudResponse>>(
      UrlsProperties.PATH_FINALIZADAS_SOLI,
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
      UrlsProperties.PATH_FINALIZADAS_SOLI,
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

}
