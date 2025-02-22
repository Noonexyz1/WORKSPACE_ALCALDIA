import {Component, OnInit} from '@angular/core';
import {RouterLink, RouterLinkActive, RouterOutlet} from '@angular/router';
import { ModoDarkService } from '../../services/modo-dark/modo-dark.service';
import { NavBarComponent } from "../shared/nav-bar/nav-bar.component";

@Component({
  selector: 'app-responsable',
  standalone: true,
    imports: [RouterOutlet, RouterLink, NavBarComponent, RouterLinkActive],
  templateUrl: './responsable.component.html',
  styleUrl: './responsable.component.css'
})
export class ResponsableComponent implements OnInit{

  private modoDarkService: ModoDarkService;

  constructor(modoDarkService: ModoDarkService){
    this.modoDarkService = modoDarkService;
  }

  ngOnInit(): void {
    this.modoDarkService.metodoModoDark();
  }
}
