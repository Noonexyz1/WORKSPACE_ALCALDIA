import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { CredencialRequest } from '../../models/CredencialRequest';
import { UsuarioResponse } from '../../models/UsuarioResponse';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  private http: HttpClient;
  private formBuilder: FormBuilder;
  loginForm: FormGroup;

  constructor(http: HttpClient, formBuilder: FormBuilder){
    this.http = http;
    this.formBuilder = formBuilder;
    this.loginForm = this.formBuilder.group({
      userName: [],
      pass: [],
    });
  }

  botonIniciarSesion(): void {
    const url = 'http://localhost:8081/dologin'; // URL de tu API

    // Extraer los valores del formulario
    const credenciales: CredencialRequest = {
      nombreUser: this.loginForm.get('userName')?.value, // Obtener el valor de userName
      pass: this.loginForm.get('pass')?.value           // Obtener el valor de pass
    };

    this.http.post<UsuarioResponse>(url, credenciales).subscribe(
      (response: UsuarioResponse) => {
        // Mostrar la respuesta en un alert
        const mensaje = `Usuario: ${response.nombres} ${response.apellidos}\nRol: ${response.nombreRol}\nDashboard: ${response.listDashConfig.join(', ')}`;
        alert(mensaje);
      },
      error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al iniciar sesión');
      }
    );
  }
}
