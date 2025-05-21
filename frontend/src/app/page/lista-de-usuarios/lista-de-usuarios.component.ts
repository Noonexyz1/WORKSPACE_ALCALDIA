import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PageRequest } from '../../utils/models/PageRequest';
import { UsuarioUnidadResponse } from '../../utils/models/UsuarioUnidadResponse';
import { catchError, map, of } from 'rxjs';
import {Router} from "@angular/router";
import {SubjectUsuarioUnidadService} from "../../utils/services/subject-usuario-unidad/subject-usuario-unidad.service";
import {UrlsProperties} from "../../utils/enums/UrlsProperties";
import {PageResponse} from "../../utils/models/PageResponse";
import {PageProperties} from "../../utils/models/PageProperties";

@Component({
  selector: 'app-lista-de-usuarios',
  standalone: true,
  imports: [],
  templateUrl: './lista-de-usuarios.component.html'
})
export class ListaDeUsuariosComponent {

  private router: Router;
  private http: HttpClient;
  private subjectUsuarioUnidadService: SubjectUsuarioUnidadService;

  listUsuarios: UsuarioUnidadResponse[] = [];

  //Esto se ejecuta antes que el ngOnInit()
  constructor(router: Router,
              http: HttpClient,
              subjectUsuarioUnidadService: SubjectUsuarioUnidadService){

    this.router = router;
    this.http = http;
    this.subjectUsuarioUnidadService = subjectUsuarioUnidadService;
    this.listarUsuarios();
  }

  pageProperties: PageProperties = new PageProperties();
  listaConsecutiva: number[] = Array.from(
    {length: this.pageProperties.totalPages},
    (_, index) => index
  );
  listarUsuarios(): void {
    const body: PageRequest = {
      id: this.pageProperties.id,
      page: this.pageProperties.currentPage,
      size: this.pageProperties.pageSize,
      sortBy: this.pageProperties.sortBy,
      direction: this.pageProperties.direction
    }

    this.http.post<PageResponse<UsuarioUnidadResponse>>(
      UrlsProperties.PATH_LIST_USERS,
      body
    ).pipe(
      map((response: PageResponse<UsuarioUnidadResponse>) => {
        this.pageProperties.currentPage = response.page;
        this.pageProperties.pageSize = response.size;
        this.pageProperties.sortBy = response.sortBy;
        this.pageProperties.direction = response.direction;

        this.listUsuarios = response.content;
        this.pageProperties.totalPages = response.totalPages;
        this.pageProperties.totalElements = response.totalElements;

        this.listaConsecutiva = Array.from(
          {length: this.pageProperties.totalPages},
          (_, index) => index
        );
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al listar usuarios');
        // Retornar un observable vacío en caso de error
        return of(null);
      })
    )
    .subscribe();
  }

  goToPage(page: number): void {
    if (page >= 0 && page < this.pageProperties.totalPages) {
      this.pageProperties.currentPage = page;
      this.listarUsuarios();
    }
  }

  goToPreviousPage(): void {
    if (this.pageProperties.currentPage > 0) {
      this.pageProperties.currentPage--;
      this.listarUsuarios();
    }
  }

  goToNextPage(): void {
    if (this.pageProperties.currentPage < this.pageProperties.totalPages - 1) {
      this.pageProperties.currentPage++;
      this.listarUsuarios();
    }
  }

  botonEditarUsuario(usuarioUnidad: UsuarioUnidadResponse): void {
    usuarioUnidad.id = usuarioUnidad.idUser;
    this.subjectUsuarioUnidadService.publicarDatos(usuarioUnidad);
    this.router.navigate(['/administrador/editarUsuario']);
  }

  botonEliminarUsuarioUnidad(usuarioUnidad: UsuarioUnidadResponse): void {
    // URL de tu API
    const url = UrlsProperties.PATH_DELETE_USER + usuarioUnidad.id;
    this.http.get(url).pipe(
      map(() => {
        window.location.reload();
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al eliminar el usuario');
        // Retornar un observable vacío en caso de error
        return of(null);
      })
    ).subscribe();
  }
}
