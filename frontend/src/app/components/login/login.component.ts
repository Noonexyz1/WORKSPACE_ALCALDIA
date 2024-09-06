import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { SubjectUserService } from '../../services/subject-user/subject-user.service';
import { Usuario } from '../../model/Usuario';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  private route: Router;
  private subjectUserOption: SubjectUserService;

  usuarioFake: Usuario = {
    "id": 23,
    "nombres": "Pablito",
    "apellidos": "De la Roble",
    "fk_responsable": {
      "id": 23,
      "nombres": "Juanito",
      "apellidos": "Hamburguesa",
      "fk_responsable": null,
      "fk_rol": {
        "id": 5,
        "nombreRol": "Administrador"
      },
      "listDashConfig": []
    },
    "fk_rol": {
      "id": 23,
      "nombreRol": "Administrador"
    },
    "listDashConfig": [
      {
        "id": 1,
        "nombreComponente": "solicitante-option",
        "datosCompononente": "todas mis opciones"
      }
    ]
  }

  constructor(route: Router, subjectUserOption: SubjectUserService) {
    this.route = route;
    this.subjectUserOption = subjectUserOption;
  }

  hacerLogin(): void {
    this.subjectUserOption.publicarDatos(this.usuarioFake);
    this.route.navigate(['/dashboard']);
  }
}
