import {Component, OnInit} from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { ModoDarkService } from '../../services/modo-dark/modo-dark.service';
import { NavBarComponent } from "../shared/nav-bar/nav-bar.component";

@Component({
  selector: 'app-solicitante',
  standalone: true,
  imports: [RouterOutlet, RouterLink, NavBarComponent],
  templateUrl: './solicitante.component.html',
  styleUrl: './solicitante.component.css'
})
export class SolicitanteComponent implements OnInit{

  private modoDarkService: ModoDarkService;

  constructor(modoDarkService: ModoDarkService){
    this.modoDarkService = modoDarkService;
  }

  ngOnInit(): void {
    this.modoDarkService.metodoModoDark();
  }
}
