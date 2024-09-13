import { Component } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { ModoDarkService } from '../../services/modo-dark/modo-dark.service';
import { NavBarComponent } from "../shared/nav-bar/nav-bar.component";

@Component({
  selector: 'app-operador',
  standalone: true,
  imports: [RouterOutlet, RouterLink, NavBarComponent],
  templateUrl: './operador.component.html',
  styleUrl: './operador.component.css'
})
export class OperadorComponent {

  private modoDarkService: ModoDarkService;

  constructor(modoDarkService: ModoDarkService){
    this.modoDarkService = modoDarkService;
  }

  ngOnInit(): void {
    this.modoDarkService.metodoModoDark();
  }
}
