import {Component} from '@angular/core';
import {NuevoPassRequest} from '../../utils/models/NuevoPassRequest';
import {catchError, map, of} from 'rxjs';
import {RootNavigateService} from '../../utils/services/root-navigate/root-navigate.service';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {UrlsProperties} from "../../utils/enums/UrlsProperties";
import {ImagesProperties} from "../../utils/enums/ImagesProperties";

@Component({
  selector: 'app-cambiar-pass',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './cambiar-pass.component.html'
})
export class CambiarPassComponent {

  private http: HttpClient;
  private formBuilder: FormBuilder;
  private rootNavigateService: RootNavigateService;
  nuevoPassForm: FormGroup;

  PATH_IMAGE_LOGO: string = ImagesProperties.PATH_IMAGE_LOGO;

  constructor(http: HttpClient,
              formBuilder: FormBuilder,
              rootNavigateService: RootNavigateService) {

    this.formBuilder = formBuilder;
    this.http = http;
    this.nuevoPassForm = this.formBuilder.group({
      ci: ['', [Validators.required, Validators.minLength(6)]],
      pass: ['', Validators.required],
      nuevoPass: ['', Validators.required],
    });
    this.rootNavigateService = rootNavigateService;
  }

  botonCambiarPass(): void {
    const nuevoPassRequest: NuevoPassRequest = {
      ci: this.nuevoPassForm.get('ci')?.value.trim(),
      pass: this.nuevoPassForm.get('pass')?.value.trim(),
      nuevoPass: this.nuevoPassForm.get('nuevoPass')?.value.trim(),
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
