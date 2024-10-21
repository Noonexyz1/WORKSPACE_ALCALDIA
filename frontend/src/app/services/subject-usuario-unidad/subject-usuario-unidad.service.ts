import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { UsuarioUnidadRequest } from '../../models/UsuarioUnidadRequest';

@Injectable({
  providedIn: 'root'
})
export class SubjectUsuarioUnidadService {

    // Se supone que Subject deberia ser injectado porque Angular ya lo tiene en su IoC Container
  // pero bueno

  usuarioUnidad: UsuarioUnidadRequest = {
    id: 0,             
    isActive: true,     
    idUser: 0,          
    nombres: '',        
    apellidos: '',      
    correo: '',         
    idUni: 0,           
    idRol: 0            
  };

  private subject$ = new BehaviorSubject<UsuarioUnidadRequest>(this.usuarioUnidad);

  obtenerObservable(): Observable<UsuarioUnidadRequest> {
      return this.subject$.asObservable();
  }

  publicarDatos(valor: UsuarioUnidadRequest): void {
      this.subject$.next(valor);
  }

}
