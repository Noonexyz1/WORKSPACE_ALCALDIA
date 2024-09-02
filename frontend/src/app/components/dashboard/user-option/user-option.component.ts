import { Component } from '@angular/core';
import { AdminOptionComponent } from "./admin-option/admin-option.component";

@Component({
  selector: 'app-user-option',
  standalone: true,
  imports: [AdminOptionComponent],
  templateUrl: './user-option.component.html',
  styleUrl: './user-option.component.css'
})
export class UserOptionComponent {
    
}
