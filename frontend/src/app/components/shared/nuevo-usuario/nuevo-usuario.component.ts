import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { RolResponse } from '../../../models/RolResponse';
import { UnidadResponse } from '../../../models/UnidadResponse';
import { catchError, map, of } from 'rxjs';
import { UsuarioNuevoRequest } from '../../../models/UsuarioNuevoRequest';
import { RootNavigateService } from '../../../services/root-navigate/root-navigate.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-nuevo-usuario',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './nuevo-usuario.component.html',
  styleUrl: './nuevo-usuario.component.css'
})
export class NuevoUsuarioComponent implements OnInit {
  
  private http: HttpClient;
  private formBuilder: FormBuilder;
  private rootNavigateService: RootNavigateService;
  private router: Router;
  nuevoUsuario: FormGroup;

  listaRoles: RolResponse[] = [];
  listaUnidades: UnidadResponse[] = [];

  constructor(http: HttpClient, formBuilder: FormBuilder, rootNavigateService: RootNavigateService, router: Router) {
    this.formBuilder = formBuilder;
    this.http = http;
    this.nuevoUsuario = this.formBuilder.group({
      nombres: [''],
      apellidos: [''],
      correo: [''],
      idRol: [''],
      idUni: [''],
    });
    this.rootNavigateService = rootNavigateService;
    this.router = router;
  }

  ngOnInit(): void {
    this.listaDeRoles();
    this.listaDeUnidades();
  }
  
  listaDeRoles(): void {
    const url = 'http://localhost:8081/administrador/listarRoles';
    this.http.get<RolResponse[]>(url).pipe(
      map((response: RolResponse[]) => {
        this.listaRoles = response;
        console.log(this.listaRoles);
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al traer los roles');
        return of(null); // Retornar un observable vacío en caso de error
      })
    )
    .subscribe();
  }

  listaDeUnidades(): void {
    const url = 'http://localhost:8081/administrador/listarUnidades';
    this.http.get<UnidadResponse[]>(url).pipe(
      map((response: UnidadResponse[]) => {
        this.listaUnidades = response;
        console.log(this.listaUnidades);
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al traer las unidades');
        return of(null); // Retornar un observable vacío en caso de error
      })
    )
    .subscribe();
  }

  botonRegistrarUsuario(): void {
    const url = 'http://localhost:8081/administrador/crearUsuario';
    const usuarioNuevoRequest: UsuarioNuevoRequest = {
      nombres: this.nuevoUsuario.get('nombres')?.value,
      apellidos: this.nuevoUsuario.get('apellidos')?.value,
      correo: this.nuevoUsuario.get('correo')?.value,
      idRol: this.nuevoUsuario.get('idRol')?.value,
      idUni: this.nuevoUsuario.get('idUni')?.value,
    };

    console.log(usuarioNuevoRequest);
    this.http.post<UsuarioNuevoRequest>(url, usuarioNuevoRequest).pipe(
      map((response: UsuarioNuevoRequest) => {
        let toNavegate = this.rootNavigateService.valorParaNavegar("Administrador");
        this.router.navigate([toNavegate]);
        
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al crear usuario');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();
    
  }
}
