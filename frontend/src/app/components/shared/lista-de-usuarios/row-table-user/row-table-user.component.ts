import { Component, Input } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { UsuarioUnidadResponse } from '../../../../models/UsuarioUnidadResponse';
import { HttpClient } from '@angular/common/http';
import { catchError, map, of } from 'rxjs';
import { RootNavigateService } from '../../../../services/root-navigate/root-navigate.service';
import { SubjectUsuarioUnidadService } from '../../../../services/subject-usuario-unidad/subject-usuario-unidad.service';
import { UsuarioUnidadRequest } from '../../../../models/UsuarioUnidadRequest';

@Component({
  selector: 'app-row-table-user',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './row-table-user.component.html',
  styleUrl: './row-table-user.component.css'
})
export class RowTableUserComponent {

  private router: Router;
  private http: HttpClient;
  private rootNavigateService: RootNavigateService;
  private subjectUsuarioUnidadService: SubjectUsuarioUnidadService;

  @Input() //Este usuario me tiene que venir completo me parece jajaj
  usuario!: UsuarioUnidadResponse;
  //Aqui me traer todos los datos, pero unicamente muestro algunos al frontend jaja

  constructor(router: Router, 
              http: HttpClient, 
              rootNavigateService: RootNavigateService,
              subjectUsuarioUnidadService: SubjectUsuarioUnidadService) {

    this.router = router;
    this.http = http;
    this.rootNavigateService = rootNavigateService;
    this.subjectUsuarioUnidadService = subjectUsuarioUnidadService;
  }
  
  botonEditarUsuario(): void { 
    //TODO, usar RxJs para pasar informacion de un componente a otro ;D
    const usuarioUnidad: UsuarioUnidadRequest = {
      id: this.usuario.id,
      isActive: this.usuario.isActive,
      idUser: this.usuario.idUser,
      nombres: this.usuario.nombres,
      apellidos: this.usuario.apellidos,
      correo: this.usuario.correo,
      idRol: this.usuario.idRol,
      idUni: this.usuario.idUni
    };
    this.subjectUsuarioUnidadService.publicarDatos(usuarioUnidad);
    this.router.navigate(['/administrador/editarUsuario']);
  }

  botonEliminarUsuario(): void { 
    const url = 'http://localhost:8081/administrador/eliminarUsuario/' + this.usuario.idUser; // URL de tu API
    this.http.get(url).pipe(
      map(() => {
        window.location.reload();
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al eliminar el usuario');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();
  }
}
