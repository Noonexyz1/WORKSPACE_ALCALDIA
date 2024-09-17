import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { SolicitudRequest } from '../../../models/SolicitudRequest';

@Component({
  selector: 'app-nueva-solicitud',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './nueva-solicitud.component.html',
  styleUrl: './nueva-solicitud.component.css'
})
export class NuevaSolicitudComponent {

  //Angular
  private http: HttpClient;
  private formBuilder: FormBuilder;
  private router: Router;

  solicitudForm: FormGroup;

  constructor(http: HttpClient, formBuilder: FormBuilder, router: Router){
    this.http = http;
    this.formBuilder = formBuilder;
    this.solicitudForm = this.formBuilder.group({
      nroDeCopias: [],
      tipoDeDocumento: [],
      nroDePaginas: [],
      nombreUnidad: [],
      archivoPdf: [],
    });
    this.router = router;
  }

  botonNuevaSolicitud(): void {
    const url = 'http://localhost:8081/dologin'; // URL de tu API

    // Extraer los valores del formulario
    const solicitudRequest: SolicitudRequest = {
      nroDeCopias: this.solicitudForm.get('nroDeCopias')?.value,
      tipoDeDocumento: this.solicitudForm.get('tipoDeDocumento')?.value,
      nroDePaginas: this.solicitudForm.get('nroDePaginas')?.value,
      nombreUnidad: this.solicitudForm.get('nombreUnidad')?.value,
      archivoPdf: this.solicitudForm.get('archivoPdf')?.value,
    };

    alert(solicitudRequest.nroDeCopias);
    alert(solicitudRequest.tipoDeDocumento);
    alert(solicitudRequest.nroDePaginas);
  }
}
