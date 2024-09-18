import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { SolicitudRequest } from '../../../models/SolicitudRequest';
import { catchError, forkJoin, map, Observable, of } from 'rxjs';

@Component({
  selector: 'app-nueva-solicitud',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './nueva-solicitud.component.html',
  styleUrl: './nueva-solicitud.component.css'
})
export class NuevaSolicitudComponent {

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

  // Función para convertir un archivo a Base64
  convertFileToBase64(file: File): Observable<string> {
    return new Observable((observer) => {
      const reader = new FileReader();
      reader.onload = () => {
        observer.next(reader.result as string);
        observer.complete();
      };
      reader.onerror = (error) => {
        observer.error(error);
      };
      reader.readAsDataURL(file); // Convertir a Base64
    });
  }

  botonNuevaSolicitud(): void {
    const url = 'http://localhost:8081/dologin'; // URL de tu API

    // Extraer los valores del formulario
    const solicitudRequest: SolicitudRequest = {
      nroDeCopias: this.solicitudForm.get('nroDeCopias')?.value,
      tipoDeDocumento: this.solicitudForm.get('tipoDeDocumento')?.value,
      nroDePaginas: this.solicitudForm.get('nroDePaginas')?.value,
      nombreUnidad: this.solicitudForm.get('nombreUnidad')?.value,
      archivosPdf: [], // Inicialmente vacío
    };

    // Convertir los archivos a Base64
    const conversiones = this.archivosSeleccionados.map(file => this.convertFileToBase64(file));

    forkJoin(conversiones).pipe(
      map(base64Array => {
        // Inicialmente vacio pero luego lo llenamos con la convercion
        solicitudRequest.archivosPdf = base64Array; // Almacena los valores base64
        return solicitudRequest; // Retornar el objeto completo
      }),
      catchError(error => {
        console.error('Error en la conversión a Base64:', error);
        alert('Error al convertir archivos a Base64');
        return of(null);
      })
    ).subscribe((solicitud) => {
      if (solicitud) {
        // Mostrar los datos antes de enviar
        console.log('Solicitud a enviar:', solicitud);

        // Enviar la solicitud
        this.http.post<SolicitudRequest>(url, solicitud).pipe(
          map((response: any) => {
            this.router.navigate(['/solicitante/misSolicitudes']);
          }),
          catchError(error => {
            console.error('Error en la petición:', error);
            alert('Hubo un error al enviar la solicitud');
            return of(null); // Retornar un observable vacío en caso de error
          })
        ).subscribe();
      }
    });
  }
}
