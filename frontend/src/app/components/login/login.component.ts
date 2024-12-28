import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { CredencialRequest } from '../../models/CredencialRequest';
import { UsuarioResponse } from '../../models/UsuarioResponse';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { catchError, map, of } from 'rxjs';
import { Router } from '@angular/router';
import { RootNavigateService } from '../../services/root-navigate/root-navigate.service';
import { SubjectUserLoginService } from '../../services/subject-user-login/subject-user-login.service';
import { LocalStorageService } from '../../services/local-storage/local-storage.service';
import {UrlsProperties} from "../../enums/UrlsProperties";
import {ImagesProperties} from "../../enums/ImagesProperties";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  //Angular
  private http: HttpClient;
  private formBuilder: FormBuilder;
  private router: Router;
  private observable: SubjectUserLoginService;

  //Mis servicios
  private rootNavigateService: RootNavigateService;
  private localStorage: LocalStorageService;

  PATH_IMAGE_LOGO: string = ImagesProperties.PATH_IMAGE_LOGO;
  loginForm: FormGroup;

  constructor(
    http: HttpClient,
    formBuilder: FormBuilder,
    router: Router,
    rootNavigateService: RootNavigateService,
    observable: SubjectUserLoginService,
    localStorage: LocalStorageService){

    this.http = http;
    this.formBuilder = formBuilder;
    this.router = router;
    this.rootNavigateService = rootNavigateService;
    this.observable = observable;
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
        //Publicamos los datos
        this.observable.publicarDatos(response);

        let toNavegate = this.rootNavigateService
          .valorParaNavegar(response.dashConfig);

        this.router.navigate([toNavegate]);
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al iniciar sesión');
        // Retornar un observable vacío en caso de error
        return of(null);
      })
    ).subscribe();
  }

}
