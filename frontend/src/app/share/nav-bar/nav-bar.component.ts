import { Component } from '@angular/core';
import {ImagesProperties} from "../../utils/enums/ImagesProperties";
import {UsuarioResponse} from "../../utils/models/UsuarioResponse";
import {LocalStorageService} from "../../utils/services/local-storage/local-storage.service";
import {UrlsProperties} from "../../utils/enums/UrlsProperties";
import {HttpClient} from "@angular/common/http";
import {Router, RouterLink} from "@angular/router";
import {RootNavigateService} from "../../utils/services/root-navigate/root-navigate.service";

@Component({
  selector: 'app-nav-bar',
  standalone: true,
  imports: [
    RouterLink
  ],
  templateUrl: './nav-bar.component.html'
})
export class NavBarComponent {

  PATH_IMAGE_LOGO: string = ImagesProperties.PATH_IMAGE_LOGO;

  private localStorageService: LocalStorageService
  usuarioResponse: UsuarioResponse | null = null;

  constructor(
    localStorageService: LocalStorageService,
    private http: HttpClient,
    private routerService: Router) {

    this.localStorageService = localStorageService;
    this.usuarioResponse = this.localStorageService
      .getItem('userData');
  }

  botonCerrarSesion() {
    localStorage.removeItem('userData');

    this.http.get(UrlsProperties.PATH_CLOSE_LOGIN, { withCredentials: true }).subscribe({
      next: () => {
        console.log('Sesión cerrada correctamente');
        this.routerService.navigate(['/login']); // Redirigir después de cerrar sesión
      },
      error: (error) => {
        console.error('Error al cerrar sesión:', error);
        alert('Hubo un error al cerrar la sesión');
        this.routerService.navigate(['/login']); // Redirigir después de cerrar sesión
      }
    });
  }

  cambiarContrasena() {
    // Cierra el dropdown
    const dropdown = document.getElementById('dropdown');
    if (dropdown) {
      dropdown.classList.add('hidden');
    }

    // Aquí puedes agregar la lógica para cambiar contraseña
    this.routerService.navigate(['/cambiarPass']);
  }
}
