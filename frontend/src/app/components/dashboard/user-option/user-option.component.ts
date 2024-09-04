import { Component, OnInit } from '@angular/core';
import { AdminOptionComponent } from "./admin-option/admin-option.component";
import { SubjectUserService } from '../../../services/subject-user/subject-user.service';
import { Usuario } from '../../../model/Usuario';
import { OperadorOptionComponent } from "./operador-option/operador-option.component";
import { ResponsableOptionComponent } from "./responsable-option/responsable-option.component";
import { SolicitanteOptionComponent } from "./solicitante-option/solicitante-option.component";

@Component({
  selector: 'app-user-option',
  standalone: true,
  imports: [AdminOptionComponent, OperadorOptionComponent, ResponsableOptionComponent, SolicitanteOptionComponent],
  templateUrl: './user-option.component.html',
  styleUrl: './user-option.component.css'
})
export class UserOptionComponent implements OnInit {

  // Desde aquí tiene que hacerse una petición POST para traer la información del usuario response

  usuario: Usuario | null = null;

  constructor(private subjectUserOption: SubjectUserService) {}    

  ngOnInit(): void {
    this.subjectUserOption.obtenerObservable().subscribe((data: Usuario | null) => {
      if (data) {
        console.log('Datos recibidos:', data);
        this.usuario = data;
      } else {
        console.log('No se ha recibido un usuario.');
      }
    });
  }
}
