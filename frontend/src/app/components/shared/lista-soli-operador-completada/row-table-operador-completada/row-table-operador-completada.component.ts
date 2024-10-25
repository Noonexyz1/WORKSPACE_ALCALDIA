import { Component, Input } from '@angular/core';
import { SolicitudOperaResponse } from '../../../../models/SolicitudOperaResponse';
import { UsuarioResponse } from '../../../../models/UsuarioResponse';
import { HttpClient } from '@angular/common/http';
import { SubjectUserLoginService } from '../../../../services/subject-user-login/subject-user-login.service';
import { Route, Router } from '@angular/router';
import { RootNavigateService } from '../../../../services/root-navigate/root-navigate.service';
import { LocalStorageService } from '../../../../services/local-storage/local-storage.service';
import { OperacionSoliRequest } from '../../../../models/OperacionSoliRequest';
import { catchError, map, of } from 'rxjs';

@Component({
  selector: 'app-row-table-operador-completada',
  standalone: true,
  imports: [],
  templateUrl: 'row-table-operador-completada.component.html',
  styleUrl: './row-table-operador-completada.component.css'
})
export class RowTableCompletadaOperadorComponent {
  
  @Input()
  solicitud!: SolicitudOperaResponse;
 
}
