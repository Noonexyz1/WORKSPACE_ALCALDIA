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

  archivosSeleccionados: File[] = []; // Archivos seleccionados


  constructor(http: HttpClient, formBuilder: FormBuilder, router: Router){
    this.http = http;
    this.formBuilder = formBuilder;
    this.solicitudForm = this.formBuilder.group({
      nroDeCopias: [],
      tipoDeDocumento: [],
      nroDePaginas: [],
      nombreUnidad: ['Selecciona una opción'],
      archivoPdf: [],
    });
    this.router = router;
  }


  // Maneja la selección de archivos
  onFileSelected(event: any): void {
    const files: FileList = event.target.files;
    // Convierte el FileList a un array y concatena a los archivos ya seleccionados
    this.archivosSeleccionados = this.archivosSeleccionados.concat(Array.from(files));
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
    alert(solicitudRequest.nombreUnidad);

    const formData: FormData = new FormData();
    this.archivosSeleccionados.forEach((file, index) => {
      formData.append('archivoPdf' + index, file);
    });

    this.http.post('http://localhost:8081/dologin', formData).subscribe(response => {
      alert('Solicitud enviada exitosamente');
      this.router.navigate(['/ruta-destino']);
    }, error => {
      alert('Error al enviar la solicitud');
    });

  }
}
