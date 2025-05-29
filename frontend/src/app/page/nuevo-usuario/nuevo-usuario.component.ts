import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { RolResponse } from '../../utils/models/RolResponse';
import { UnidadResponse } from '../../utils/models/UnidadResponse';
import { catchError, map, of } from 'rxjs';
import { UsuarioUnidadEditRequest } from '../../utils/models/UsuarioUnidadEditRequest';
import { RootNavigateService } from '../../utils/services/root-navigate/root-navigate.service';
import {CargoResponse} from "../../utils/models/CargoResponse";
import {LocalStorageService} from "../../utils/services/local-storage/local-storage.service";
import {UrlsProperties} from "../../utils/enums/UrlsProperties";
import {FormularioUsuarioComponent} from "../../share/formulario-usuario/formulario-usuario.component";

@Component({
  selector: 'app-nuevo-usuario',
  standalone: true,
  imports: [ReactiveFormsModule, FormularioUsuarioComponent],
  templateUrl: './nuevo-usuario.component.html'
})
export class NuevoUsuarioComponent {

  private http: HttpClient;
  private formBuilder: FormBuilder;
  private rootNavigateService: RootNavigateService;
  private localStorage: LocalStorageService;

  nuevoUsuario: FormGroup;

  listaRoles: RolResponse[] = [];
  listaCargos: CargoResponse[] = [];
  listaUnidades: UnidadResponse[] = [];

  constructor(http: HttpClient,
              formBuilder: FormBuilder,
              rootNavigateService: RootNavigateService,
              localStorage: LocalStorageService) {

    this.formBuilder = formBuilder;
    this.localStorage = localStorage;
    this.http = http;
    this.nuevoUsuario = this.formBuilder.group({
      idUser: [''],
      ci: ['', [Validators.required, Validators.minLength(6)]],
      nombres: ['', [Validators.required, Validators.minLength(3)]],
      paterno: ['', [Validators.required, Validators.minLength(3)]],
      materno: ['', [Validators.required, Validators.minLength(3)]],
      correo: ['', [Validators.required, Validators.email]],
      idRol: ['', Validators.required],
      idCargo: ['', Validators.required],
      idUni: ['', Validators.required],
    });
    this.rootNavigateService = rootNavigateService;

    this.inicializarDatos();
  }

  inicializarDatos(): void {
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
    const usuarioNuevoRequest: UsuarioUnidadEditRequest = {
      id: this.nuevoUsuario.get('idUser')?.value,

      ci: this.nuevoUsuario.get('ci')?.value.trim(),
      nombres: this.nuevoUsuario.get('nombres')?.value.trim(),
      paterno: this.nuevoUsuario.get('paterno')?.value.trim(),
      materno: this.nuevoUsuario.get('materno')?.value.trim(),
      correo: this.nuevoUsuario.get('correo')?.value.trim(),

      idRol: this.nuevoUsuario.get('idRol')?.value,
      idUni: this.nuevoUsuario.get('idUni')?.value,
      idCargo: this.nuevoUsuario.get('idCargo')?.value,

      idDirector: this.localStorage.getItem('userData').id,

      //TODO, esto ya tiene solucion en el back
      idResponsable: 2
    };

    this.http.post<UsuarioUnidadEditRequest>(
      UrlsProperties.PATH_CREATE_USER,
      usuarioNuevoRequest
    ).pipe(
      map(() => {
        this.rootNavigateService.valorParaNavegar("Administrador");
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
