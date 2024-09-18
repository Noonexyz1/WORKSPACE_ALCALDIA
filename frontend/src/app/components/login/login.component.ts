import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { CredencialRequest } from '../../models/CredencialRequest';
import { UsuarioResponse } from '../../models/UsuarioResponse';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { catchError, map, of } from 'rxjs';
import { Router } from '@angular/router';
import { RootNavigateService } from '../../services/root-navigate/root-navigate.service';

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

  //Mis servicios
  private rootNavigateService: RootNavigateService;

  loginForm: FormGroup;

  constructor(http: HttpClient, formBuilder: FormBuilder, router: Router, rootNavigateService: RootNavigateService){
    this.http = http;
    this.formBuilder = formBuilder;
    this.loginForm = this.formBuilder.group({
      userName: [],
      pass: [],
    });
    this.router = router;

    this.rootNavigateService = rootNavigateService;
  }

  botonIniciarSesion(): void {
    const url = 'http://localhost:8081/dologin'; // URL de tu API

    // Extraer los valores del formulario
    const credenciales: CredencialRequest = {
      nombreUser: this.loginForm.get('userName')?.value, // Obtener el valor de userName
      pass: this.loginForm.get('pass')?.value           // Obtener el valor de pass
    };

    this.http.post<UsuarioResponse>(url, credenciales).pipe(
      map((response: UsuarioResponse) => {
        // Mostrar la respuesta en un alert
        const mensaje = `Usuario: ${response.nombres} ${response.apellidos}\nRol: ${response.nombreRol}\nDashboard: ${response.listDashConfig.join(', ')}`;
        alert(mensaje);

        let toNavegate = this.rootNavigateService.valorParaNavegar(response.listDashConfig[0]);
        this.router.navigate([toNavegate]);
        
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al iniciar sesión');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();
  }
}
