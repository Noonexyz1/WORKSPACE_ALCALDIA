import { Component } from '@angular/core';
import { SubjectUsuarioUnidadService } from '../../utils/services/subject-usuario-unidad/subject-usuario-unidad.service';
import { HttpClient } from '@angular/common/http';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { catchError, map, of } from 'rxjs';
import { RolResponse } from '../../utils/models/RolResponse';
import { UnidadResponse } from '../../utils/models/UnidadResponse';
import { RootNavigateService } from '../../utils/services/root-navigate/root-navigate.service';
import {CargoResponse} from "../../utils/models/CargoResponse";
import {UrlsProperties} from "../../utils/enums/UrlsProperties";
import {UsuarioUnidadEditRequest} from "../../utils/models/UsuarioUnidadEditRequest";
import {LocalStorageService} from "../../utils/services/local-storage/local-storage.service";

@Component({
  selector: 'app-editar-usuario',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './editar-usuario.component.html'
})
export class EditarUsuarioComponent {

  //Crear formulario reactivo
  private http: HttpClient;
  private formBuilder: FormBuilder;
  private subjectUsuarioUnidadService: SubjectUsuarioUnidadService;
  private rootNavigateService: RootNavigateService;
  private localStorage: LocalStorageService;

  editUserForm: FormGroup;
  usuario!: UsuarioUnidadEditRequest;
  listaRoles: RolResponse[] = [];
  listaUnidades: UnidadResponse[] = [];
  listaCargos: CargoResponse[] = [];

  constructor(subjectUsuarioUnidadService: SubjectUsuarioUnidadService,
              http: HttpClient,
              formBuilder: FormBuilder,
              rootNavigateService: RootNavigateService,
              localStorage: LocalStorageService) {

    this.http = http;
    this.localStorage = localStorage;
    this.formBuilder = formBuilder;
    this.subjectUsuarioUnidadService = subjectUsuarioUnidadService;
    this.rootNavigateService = rootNavigateService;
    this.editUserForm = this.formBuilder.group({
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

    this.inicializacion();
  }

  inicializacion(): void {
    this.getObserbable();
    this.listaDeRoles();
    this.listaDeUnidades();
    this.listaDeCargos();
  }

  getObserbable(): void {
    // Suscribirse al observable para recibir cambios
    this.subjectUsuarioUnidadService.obtenerObservable()
      .subscribe((datos) => {
        this.usuario = datos;
        console.log('Datos recibidossss:', this.usuario);

        this.editUserForm.patchValue({
          id: this.usuario.idUni,
          nombres: this.usuario.nombres,
          materno: this.usuario.materno,
          paterno: this.usuario.paterno,
          correo: this.usuario.correo,
          ci: this.usuario.ci,

          idRol: this.usuario.idRol,
          idCargo: this.usuario.idCargo,
          idUni: this.usuario.idUni
        });
      });
  }

  listaDeCargos(): void {
    const url = 'http://localhost:8081/administrador/listarCargos';
    this.http.get<CargoResponse[]>(url).pipe(
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
    ).subscribe();
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
    ).subscribe();
  }

  botonEditarUsuarioUnidad(): void {
    const userUniEdit: UsuarioUnidadEditRequest = {
      id: this.usuario.id, //Id de USUARIO, lo de

      ci: this.editUserForm.get('ci')?.value.trim(),
      nombres: this.editUserForm.get('nombres')?.value.trim(),
      materno: this.editUserForm.get('materno')?.value.trim(),
      paterno: this.editUserForm.get('paterno')?.value.trim(),
      correo: this.editUserForm.get('correo')?.value.trim(),

      idRol: this.editUserForm.get('idRol')?.value,
      idUni: this.editUserForm.get('idUni')?.value,
      idCargo: this.editUserForm.get('idCargo')?.value,

      idDirector: this.localStorage.getItem('userData').id,

      //TODO, esto ya tiene solucion en el back
      idResponsable: 2
    };

    console.log(userUniEdit);

    this.http.post<UsuarioUnidadEditRequest>(
      UrlsProperties.PATH_EDIT_USER,
      userUniEdit
    ).pipe(
      map(() => {
        this.rootNavigateService.valorParaNavegar("Administrador");
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al editar el usuario el usuario');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();
  }
}
