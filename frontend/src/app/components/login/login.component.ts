import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { CredencialRequest } from '../../models/CredencialRequest';
import { UsuarioResponse } from '../../models/UsuarioResponse';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  private http: HttpClient;

  constructor(http: HttpClient){
    this.http = http;
  }

  botonIniciarSesion(): void {
    const url = 'http://localhost:8081/dologin'; // URL de tu API

    const credenciales: CredencialRequest = {
      nombreUser: 'ana.perez',
      pass: 'password123'
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
