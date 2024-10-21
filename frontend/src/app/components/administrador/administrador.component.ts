import { Component, OnInit } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { ModoDarkService } from '../../services/modo-dark/modo-dark.service';
import { NavBarComponent } from "../shared/nav-bar/nav-bar.component";

@Component({
  selector: 'app-administrador',
  standalone: true,
  imports: [RouterLink, RouterOutlet, NavBarComponent],
  templateUrl: './administrador.component.html',
  styleUrl: './administrador.component.css'
})
export class AdministradorComponent implements OnInit{

  private modoDarkService: ModoDarkService;

  constructor(modoDarkService: ModoDarkService){
    this.modoDarkService = modoDarkService;
  }

  ngOnInit(): void {
    this.modoDarkService.metodoModoDark();
  }

  crearUsuario(): void {}
  editarUsuario(): void {}
  eliminarUsuario(): void {}
}
