import { Component, OnInit } from '@angular/core';
import { RowTableUserComponent } from "./row-table-user/row-table-user.component";
import { HttpClient } from '@angular/common/http';
import { PageRequest } from '../../../models/PageRequest';
import { UsuarioUnidadResponse } from '../../../models/UsuarioUnidadResponse';
import { catchError, map, of } from 'rxjs';

@Component({
  selector: 'app-lista-de-usuarios',
  standalone: true,
  imports: [RowTableUserComponent],
  templateUrl: './lista-de-usuarios.component.html',
  styleUrl: './lista-de-usuarios.component.css'
})
export class ListaDeUsuariosComponent implements OnInit {

  private http: HttpClient;
  listUsuarios: UsuarioUnidadResponse[] = [];

  constructor(http: HttpClient){
    this.http = http;
  }

  //Aqui debo poner mis peticiones al Backend
  ngOnInit(): void {
    this.listarUsuarios();
  }

  listarUsuarios(): void {
    const url = 'http://localhost:8081/administrador/listaDeUsuarios';
    
    const body: PageRequest = {
      page: 0,
      size: 20,
      byColumName: ""
    }

    this.http.post<UsuarioUnidadResponse[]>(url, body).pipe(
      map((response: UsuarioUnidadResponse[]) => {
        console.log(response);
        this.listUsuarios = response;
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al listar usuarios');
        return of(null); // Retornar un observable vacío en caso de error
      })
    )
    .subscribe();
  }
}
