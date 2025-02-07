import {Component} from '@angular/core';
import {NuevoPassRequest} from '../../../models/NuevoPassRequest';
import {catchError, map, of} from 'rxjs';
import {Router} from '@angular/router';
import {RootNavigateService} from '../../../services/root-navigate/root-navigate.service';
import {FormBuilder, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {UrlsProperties} from "../../../enums/UrlsProperties";

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

  constructor(http: HttpClient,
              formBuilder: FormBuilder,
              rootNavigateService: RootNavigateService,
              router: Router) {

    this.formBuilder = formBuilder;
    this.http = http;
    this.nuevoPassForm = this.formBuilder.group({
      ci: [''],
      pass: [''],
      nuevoPass: [''],
    });
    this.rootNavigateService = rootNavigateService;
    this.router = router;
  }

  botonCambiarPass(): void {
    const nuevoPassRequest: NuevoPassRequest = {
      ci: this.nuevoPassForm.get('ci')?.value,
      pass: this.nuevoPassForm.get('pass')?.value,
      nuevoPass: this.nuevoPassForm.get('nuevoPass')?.value,
    };

    this.http.post<NuevoPassRequest>(
      UrlsProperties.PATH_CHANCE_PASS,
      nuevoPassRequest
    ).pipe(
      map((response: NuevoPassRequest) => {
        this.rootNavigateService.valorParaNavegar('Login');
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al cambiar la contrasena');
        // Retornar un observable vacío en caso de error
        return of(null);
      })
    ).subscribe();

  }
}
