import { Component, OnInit } from '@angular/core';
import { SubjectUsuarioUnidadService } from '../../../services/subject-usuario-unidad/subject-usuario-unidad.service';
import { UsuarioUnidadRequest } from '../../../models/UsuarioUnidadRequest';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { catchError, map, of } from 'rxjs';
import { Router } from '@angular/router';
import { RolResponse } from '../../../models/RolResponse';
import { UnidadResponse } from '../../../models/UnidadResponse';
import { RootNavigateService } from '../../../services/root-navigate/root-navigate.service';

@Component({
  selector: 'app-editar-usuario',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './editar-usuario.component.html',
  styleUrl: './editar-usuario.component.css'
})
export class EditarUsuarioComponent implements OnInit {

  //Crear formulario reactivo
  private http: HttpClient; 
  private formBuilder: FormBuilder;
  private subjectUsuarioUnidadService: SubjectUsuarioUnidadService;
  private rootNavigateService: RootNavigateService;
  private router: Router;
  editUserForm: FormGroup;

  usuarioUnidad!: UsuarioUnidadRequest;

  listaRoles: RolResponse[] = [];
  listaUnidades: UnidadResponse[] = [];

  constructor(subjectUsuarioUnidadService: SubjectUsuarioUnidadService,
              http: HttpClient, 
              formBuilder: FormBuilder,
              rootNavigateService: RootNavigateService,
              router: Router) {

    this.router = router;
    this.http = http;
    this.formBuilder = formBuilder;
    this.subjectUsuarioUnidadService = subjectUsuarioUnidadService;
    this.rootNavigateService = rootNavigateService;
    this.editUserForm = this.formBuilder.group({
      id: [],
      isActive: [],

      idUser: [],
      nombres: [],
      apellidos: [],
      correo: [],
      
      idUni: [],
      idRol: [],
    });
  }

  ngOnInit(): void {
    this.getObserbable();
    this.listaDeRoles();
    this.listaDeUnidades();
  }

  getObserbable(): void {
    // Suscribirse al observable para recibir cambios
    this.subjectUsuarioUnidadService.obtenerObservable().subscribe((datos) => {
      this.usuarioUnidad = datos;
      console.log('Datos recibidossss:', this.usuarioUnidad);

      this.editUserForm.patchValue({
        id: this.usuarioUnidad.id,
        isActive: this.usuarioUnidad.isActive,
  
        idUser: this.usuarioUnidad.idUser,
        nombres: this.usuarioUnidad.nombres,
        apellidos: this.usuarioUnidad.apellidos,
        correo: this.usuarioUnidad.correo,
  
        idUni: this.usuarioUnidad.idUni,
        idRol: this.usuarioUnidad.idRol,
      });
    });
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

  botonEditarUsuarioUnidad(): void {
    //TODO, implementar
    const url = 'http://localhost:8081/administrador/editarUsuario';
    const userUniEdit: UsuarioUnidadRequest = {
      id: this.editUserForm.get('id')?.value,
      isActive: this.editUserForm.get('isActive')?.value,

      idUser: this.editUserForm.get('idUser')?.value,
      nombres: this.editUserForm.get('nombres')?.value,
      apellidos: this.editUserForm.get('apellidos')?.value,
      correo: this.editUserForm.get('correo')?.value,

      idUni: this.editUserForm.get('idUni')?.value,
      idRol: this.editUserForm.get('idRol')?.value,
    }

    console.log(userUniEdit);

    this.http.post<UsuarioUnidadRequest>(url, userUniEdit).pipe(
      map(() => {
        let toNavegate = this.rootNavigateService.valorParaNavegar("Administrador");
        this.router.navigate([toNavegate]);
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al editar el usuario el usuario');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();

  }
}
