import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  private route: Router;

  constructor(route: Router) {
    this.route = route;
  }
  hacerLogin(): void {
    alert('Haciendo Login...');
    this.route.navigate(['/dashboard']);
  }
}
