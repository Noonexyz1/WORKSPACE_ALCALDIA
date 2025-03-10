import { Component } from '@angular/core';
import {ImagesProperties} from "../../../enums/ImagesProperties";
import {UsuarioResponse} from "../../../models/UsuarioResponse";
import {LocalStorageService} from "../../../services/local-storage/local-storage.service";
import {PageResponse} from "../../../models/PageResponse";
import {UsuarioUnidadResponse} from "../../../models/UsuarioUnidadResponse";
import {UrlsProperties} from "../../../enums/UrlsProperties";
import {catchError, map, of} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-nav-bar',
  standalone: true,
  imports: [],
  templateUrl: './nav-bar.component.html',
  styleUrl: './nav-bar.component.css'
})
export class NavBarComponent {

  PATH_IMAGE_LOGO: string = ImagesProperties.PATH_IMAGE_LOGO;

  private localStorageService: LocalStorageService
  usuarioResponse: UsuarioResponse | null = null;

  constructor(
    localStorageService: LocalStorageService,
    private http: HttpClient) {
    this.localStorageService = localStorageService;
    this.usuarioResponse = this.localStorageService
      .getItem('userData');
  }

  botonCerrarSesion() {
    localStorage.removeItem('userData');

    this.http.get(UrlsProperties.PATH_CLOSE_LOGIN, { withCredentials: true }).subscribe({
      next: () => {
        console.log('Sesión cerrada correctamente');
        //this.router.navigate(['/login']); // Redirigir después de cerrar sesión
      },
      error: (error) => {
        console.error('Error al cerrar sesión:', error);
        alert('Hubo un error al cerrar la sesión');
      }
    });
  }

}
