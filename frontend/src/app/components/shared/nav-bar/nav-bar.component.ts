import { Component } from '@angular/core';
import {ImagesProperties} from "../../../enums/ImagesProperties";
import {UsuarioResponse} from "../../../models/UsuarioResponse";
import {LocalStorageService} from "../../../services/local-storage/local-storage.service";

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

  constructor(localStorageService: LocalStorageService) {
    this.localStorageService = localStorageService;
    this.usuarioResponse = this.localStorageService
      .getItem('userData');
  }

  botonCerrarSesion() {
    localStorage.removeItem('userData')
  }
}
