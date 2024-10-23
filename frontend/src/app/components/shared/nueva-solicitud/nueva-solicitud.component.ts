import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { SolicitudRequest } from '../../../models/SolicitudRequest';
import { catchError, forkJoin, map, Observable, of } from 'rxjs';
import { UnidadResponse } from '../../../models/UnidadResponse';
import { SubjectUserLoginService } from '../../../services/subject-user-login/subject-user-login.service';
import { UsuarioResponse } from '../../../models/UsuarioResponse';

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
  private observable: SubjectUserLoginService;

  usuario: UsuarioResponse = {
    id: 0,
    nombres: '',
    apellidos: '',
    correo: '',
    nombreRol: '',
    dashConfig: '',
    idUnidad: 0
  };
  solicitudForm: FormGroup;
  archivosSeleccionados: File[] = []; // Archivos seleccionados
  listaDeUnidades: UnidadResponse[] = []; // Lista de unidades

  constructor(http: HttpClient, 
              formBuilder: FormBuilder, 
              router: Router, 
              observable: SubjectUserLoginService){
    
    this.http = http;
    this.formBuilder = formBuilder;
    this.router = router;
    this.observable = observable;
    this.solicitudForm = this.formBuilder.group({
      idSolicitante: [],
      idUnidad: [],
      nroDeCopias: [],
      tipoDeDocumento: [],
      nroDePaginas: [],
      archivosPdf: [],
    });
  }

  botonNuevaSolicitud(): void {
    const url = 'http://localhost:8081/solicitante/solicitarFotocopiar'; // URL de tu API

    this.observable.obtenerObservable().subscribe((datos) => {
      this.usuario = datos;
      console.log(this.usuario);
      alert("Datos obtenidos del publicador")
    });

    //TODO, tengo que revisar este codigo del Null
    const solicitudRequest: SolicitudRequest = {
      idSolicitante: this.usuario.id,
      idUnidad: this.usuario.idUnidad,
      nroDeCopias: this.solicitudForm.get('nroDeCopias')?.value,
      tipoDeDocumento: this.solicitudForm.get('tipoDeDocumento')?.value,
      nroDePaginas: this.solicitudForm.get('nroDePaginas')?.value,
      archivosPdf: [], // Llenado después de la conversión
    };

    console.log('Datos:', solicitudRequest)

    // Convertir los archivos a Base64
    const conversiones = this.archivosSeleccionados.map(file => this.convertFileToBase64(file));

    forkJoin(conversiones).pipe(
      
      map(base64Array => {
        solicitudRequest.archivosPdf = base64Array.map((base64, index) => ({
          nombreArchivo: this.archivosSeleccionados[index].name, // Usa el nombre del archivo real
          archivo: base64,
        }));
        return solicitudRequest; // Retorna el objeto completo
      }),
      catchError(error => {
        console.error('Error en la conversión a Base64:', error);
        alert('Error al convertir archivos a Base64');
        return of(null);
      })

    ).subscribe((solicitudRequest) => {
      if (solicitudRequest) {
        // Mostrar los datos antes de enviar
        console.log('Solicitud a enviar:', solicitudRequest);

        // Enviar la solicitud
        this.http.post<SolicitudRequest>(url, solicitudRequest).pipe(
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
}
