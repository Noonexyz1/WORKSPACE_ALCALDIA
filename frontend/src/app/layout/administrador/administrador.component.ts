import { Component, OnInit } from '@angular/core';
import {RouterLink, RouterLinkActive, RouterOutlet} from '@angular/router';
import { ModoDarkService } from '../../utils/services/modo-dark/modo-dark.service';
import { NavBarComponent } from "../../page/nav-bar/nav-bar.component";
import {SeparadorVersionComponent} from "../../page/separador-version/separador-version.component";

@Component({
  selector: 'app-administrador',
  standalone: true,
  imports: [RouterLink, RouterOutlet, NavBarComponent, RouterLinkActive, SeparadorVersionComponent],
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
}
