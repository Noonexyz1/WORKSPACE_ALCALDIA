import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Usuario } from '../model/Usuario';

@Injectable({ providedIn: 'root' })
export class AuthService {
  
  private API_URL = '/dologin'; // La URL de tu endpoint en el backend
  private http: HttpClient;

  constructor(http: HttpClient) { 
    this.http = http;
  }

  loginService(credentials: { correo: string, contrasena: string }): Observable<Usuario> {
    return this.http.post<Usuario>(this.API_URL, credentials);
  }
}
