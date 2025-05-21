import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { CredencialRequest } from '../../utils/models/CredencialRequest';
import { UsuarioResponse } from '../../utils/models/UsuarioResponse';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { catchError, map, of } from 'rxjs';
import { RootNavigateService } from '../../utils/services/root-navigate/root-navigate.service';
import { LocalStorageService } from '../../utils/services/local-storage/local-storage.service';
import {UrlsProperties} from "../../utils/enums/UrlsProperties";
import {ImagesProperties} from "../../utils/enums/ImagesProperties";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './login.component.html'
})
export class LoginComponent {

  //Angular
  private http: HttpClient;
  private formBuilder: FormBuilder;

  //Mis servicios
  private rootNavigateService: RootNavigateService;
  private localStorage: LocalStorageService;

  PATH_IMAGE_LOGO: string = ImagesProperties.PATH_IMAGE_LOGO;
  loginForm: FormGroup;

  constructor(
    http: HttpClient,
    formBuilder: FormBuilder,
    rootNavigateService: RootNavigateService,
    localStorage: LocalStorageService){

    this.http = http;
    this.formBuilder = formBuilder;
    this.rootNavigateService = rootNavigateService;
    this.localStorage = localStorage;

    this.loginForm = this.formBuilder
      .group({
        ci: [],
        pass: [],
      });
  }

  botonIniciarSesion(): void {
    // Extraer los valores del formulario
    const credenciales: CredencialRequest = {
      // Obtener el valor de correo
      ci: this.loginForm.get('ci')?.value,
      // Obtener el valor de pass
      pass: this.loginForm.get('pass')?.value
    };

    // Recibimos la peticion
    this.http.post<UsuarioResponse>(
      UrlsProperties.PATH_LOGIN,
      credenciales
    ).pipe(
      map((response: UsuarioResponse) => {
        //Guardamos en el localStorage
        this.localStorage.setItem('userData', response);
        if (response.nombreRol == "Administrador") {
          this.rootNavigateService.valorParaNavegar(response.nombreRol);
        }
        if (response.nombreRol == "Responsable") {
          this.rootNavigateService.valorParaNavegar(response.nombreRol + "Pendientes");
        }
        if (response.nombreRol == "Solicitante") {
          this.rootNavigateService.valorParaNavegar(response.nombreRol + "Pendientes");
        }
      }),
      catchError(error => {
        alert('Hubo un error al iniciar sesión');
        return of(null);
      })
    ).subscribe();
  }

}
