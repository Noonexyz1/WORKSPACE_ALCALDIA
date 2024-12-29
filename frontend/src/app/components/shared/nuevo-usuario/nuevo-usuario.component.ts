import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { RolResponse } from '../../../models/RolResponse';
import { UnidadResponse } from '../../../models/UnidadResponse';
import { catchError, map, of } from 'rxjs';
import { UsuarioNuevoRequest } from '../../../models/UsuarioNuevoRequest';
import { RootNavigateService } from '../../../services/root-navigate/root-navigate.service';
import { Router } from '@angular/router';
import {CargoResponse} from "../../../models/CargoResponse";
import {LocalStorageService} from "../../../services/local-storage/local-storage.service";
import {UsuarioResponse} from "../../../models/UsuarioResponse";
import {UrlsProperties} from "../../../enums/UrlsProperties";

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
  private localStorage: LocalStorageService;

  nuevoUsuario: FormGroup;

  listaRoles: RolResponse[] = [];
  listaCargos: CargoResponse[] = [];
  listaUnidades: UnidadResponse[] = [];

  constructor(http: HttpClient,
              formBuilder: FormBuilder,
              rootNavigateService: RootNavigateService,
              router: Router,
              localStorage: LocalStorageService) {

    this.formBuilder = formBuilder;
    this.localStorage = localStorage;
    this.http = http;
    this.nuevoUsuario = this.formBuilder.group({
      nombres: [''],
      materno: [''],
      paterno: [''],
      correo: [''],
      ci: [''],

      idRol: [],
      idCargo: [],
      idUni: [],
    });
    this.rootNavigateService = rootNavigateService;
    this.router = router;
  }

  ngOnInit(): void {
    this.listaDeRoles();
    this.listaDeUnidades();
    this.listaDeCargos();
  }

  listaDeCargos(): void {
    this.http.get<CargoResponse[]>(UrlsProperties.PATH_LIST_CARGOS)
      .pipe(
        map((response: CargoResponse[]) => {
          this.listaCargos = response;
          console.log(this.listaRoles);
        }),
        catchError(error => {
          console.error('Error en la petición:', error);
          alert('Hubo un error al traer los cargos');
          return of(null); // Retornar un observable vacío en caso de error
        })
      ).subscribe();
  }

  listaDeRoles(): void {
    this.http.get<RolResponse[]>(UrlsProperties.PATH_LIST_ROLES)
      .pipe(
        map((response: RolResponse[]) => {
          this.listaRoles = response;
          console.log(this.listaRoles);
        }),
        catchError(error => {
          console.error('Error en la petición:', error);
          alert('Hubo un error al traer los roles');
          return of(null); // Retornar un observable vacío en caso de error
        })
      ).subscribe();
  }

  listaDeUnidades(): void {
    this.http.get<UnidadResponse[]>(UrlsProperties.PATH_LIST_UNIDADES)
      .pipe(
        map((response: UnidadResponse[]) => {
          this.listaUnidades = response;
          console.log(this.listaUnidades);
        }),
        catchError(error => {
          console.error('Error en la petición:', error);
          alert('Hubo un error al traer las unidades');
          return of(null); // Retornar un observable vacío en caso de error
        })
      ).subscribe();
  }


  botonRegistrarUsuario(): void {
    const usuarioNuevoRequest: UsuarioNuevoRequest = {
      nombres: this.nuevoUsuario.get('nombres')?.value,
      materno: this.nuevoUsuario.get('materno')?.value,
      paterno: this.nuevoUsuario.get('paterno')?.value,
      correo: this.nuevoUsuario.get('correo')?.value,
      ci: this.nuevoUsuario.get('ci')?.value,

      idRol: this.nuevoUsuario.get('idRol')?.value,
      idUni: this.nuevoUsuario.get('idUni')?.value,
      idCargo: this.nuevoUsuario.get('idCargo')?.value,

      idDirector: this.localStorage.getItem('userData').id,
      idResponsable: 2
    };

    this.http.post<UsuarioNuevoRequest>(
      UrlsProperties.PATH_CREATE_USER,
      usuarioNuevoRequest
    ).pipe(
      map(() => {
        let toNavegate = this.rootNavigateService
          .valorParaNavegar("Administrador");
        this.router.navigate([toNavegate]);
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al crear usuario');
        // Retornar un observable vacío en caso de error
        return of(null);
      })
    ).subscribe();
  }
}
