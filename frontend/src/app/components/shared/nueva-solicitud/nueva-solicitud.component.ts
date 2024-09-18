import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { SolicitudRequest } from '../../../models/SolicitudRequest';
import { catchError, map, of } from 'rxjs';

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
      archivosPdf: [],
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
    //TODO---------------hacer el backend para esto---------
    const url = 'http://localhost:8081/dologin'; // URL de tu API

    // Extraer los valores del formulario
    const solicitudRequest: SolicitudRequest = {
        nroDeCopias: this.solicitudForm.get('nroDeCopias')?.value,
        tipoDeDocumento: this.solicitudForm.get('tipoDeDocumento')?.value,
        nroDePaginas: this.solicitudForm.get('nroDePaginas')?.value,
        nombreUnidad: this.solicitudForm.get('nombreUnidad')?.value,
        
        //TODO --------------------------------------------- hay que cambiar el formato a BASE_64        
        archivosPdf: this.archivosSeleccionados.map(file => file.name), // Obtener los nombres de los archivos
    };

    // Opcional: puedes usar alertas para verificar los valores
    alert(solicitudRequest.nroDeCopias);
    alert(solicitudRequest.tipoDeDocumento);
    alert(solicitudRequest.nroDePaginas);
    alert(solicitudRequest.nombreUnidad);
    alert(`Archivos seleccionados: ${solicitudRequest.archivosPdf.join(', ')}`);

    // Enviar la solicitud
    this.http.post<SolicitudRequest>(url, solicitudRequest).pipe(
      map((response: any) => {
        // Mostrar la respuesta en un alert
        //TODO----------------------------------------------------------
        //const mensaje = `Usuario: ${response.nombres} ${response.apellidos}\nRol: ${response.nombreRol}\nDashboard: ${response.listDashConfig.join(', ')}`;
        //alert(mensaje);
        //--------------------------------------------------------------

        //No esta bien hardcodear pero bueno, estoy aprendiendo
        this.router.navigate(['/solicitante/misSolicitudes']);
        
      }),
      catchError(error => {
        console.error('Error en la petición:', error);
        alert('Hubo un error al hacer la solicitud');
        return of(null); // Retornar un observable vacío en caso de error
      })
    ).subscribe();

  }

}
