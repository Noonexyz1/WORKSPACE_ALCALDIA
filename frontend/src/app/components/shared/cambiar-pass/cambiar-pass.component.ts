import { Component } from '@angular/core';
import { NuevoPassRequest } from '../../../models/NuevoPassRequest';
import { catchError, map, of } from 'rxjs';
import { Router } from '@angular/router';
import { RootNavigateService } from '../../../services/root-navigate/root-navigate.service';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-cambiar-pass',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './cambiar-pass.component.html',
  styleUrl: './cambiar-pass.component.css'
})
export class CambiarPassComponent {
  
  private http: HttpClient;
  private formBuilder: FormBuilder;
  private rootNavigateService: RootNavigateService;
  private router: Router;
  nuevoPassForm: FormGroup;

  constructor(http: HttpClient, formBuilder: FormBuilder, rootNavigateService: RootNavigateService, router: Router){
    this.formBuilder = formBuilder;
    this.http = http;
    this.nuevoPassForm = this.formBuilder.group({
      correo: [''],
      pass: [''],
      nuevoPass: [''],
    });
    this.rootNavigateService = rootNavigateService;
    this.router = router;
  }

  botonCambiarPass(): void {
    const url = 'http://localhost:8081/administrador/cambiarPass';
    const nuevoPassRequest: NuevoPassRequest = {
      correo: this.nuevoPassForm.get('correo')?.value,
      pass: this.nuevoPassForm.get('pass')?.value,
      nuevoPass: this.nuevoPassForm.get('nuevoPass')?.value,
    };

    console.log(nuevoPassRequest);

    this.http.post<NuevoPassRequest>(url, nuevoPassRequest).pipe(
      map((response: NuevoPassRequest) => {
        let toNavegate = this.rootNavigateService.valorParaNavegar("Administrador");
        this.router.navigate([toNavegate]);
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al crear usuario');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();
    
  }
}
