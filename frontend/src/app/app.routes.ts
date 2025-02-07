import {Routes} from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {AdministradorComponent} from './components/administrador/administrador.component';
import {ListaDeUsuariosComponent} from './components/shared/lista-de-usuarios/lista-de-usuarios.component';
import {NuevoUsuarioComponent} from './components/shared/nuevo-usuario/nuevo-usuario.component';
import {CambiarPassComponent} from './components/shared/cambiar-pass/cambiar-pass.component';
import {
  ListaSoliSolicitantePendienteComponent
} from './components/shared/lista-soli-solicitante-pendiente/lista-soli-solicitante-pendiente.component';
import {NuevaSolicitudComponent} from './components/shared/nueva-solicitud/nueva-solicitud.component';
import {SolicitanteComponent} from './components/solicitante/solicitante.component';
import {ResponsableComponent} from './components/responsable/responsable.component';
import {EditarUsuarioComponent} from './components/shared/editar-usuario/editar-usuario.component';
import {
  ListaSoliAprobadaResponsableComponent
} from './components/shared/lista-soli-responsable-aprobada/lista-soli-responsable-aprobada.component';
import {
  ListaSoliPendienteResponsableComponent
} from './components/shared/lista-soli-responsable-pendiente/lista-soli-responsable-pendiente.component';
import {
  ListaSoliFinalizadaResponsableComponent
} from './components/shared/lista-soli-responsable-finalizada/lista-soli-responsable-finalizada.component';
import {
  ListaSoliSolicitanteAprobadaComponent
} from "./components/shared/lista-soli-solicitante-aprobada/lista-soli-solicitante-aprobada.component";
import {
  ListaSoliSolicitanteFinalizadaComponent
} from "./components/shared/lista-soli-solicitante-finalizada/lista-soli-solicitante-finalizada.component";

export const routes: Routes = [
  {path: '', redirectTo: "login", pathMatch: "full"},
  {path: 'login', component: LoginComponent},
  {
    path: 'administrador',
    component: AdministradorComponent,
    children: [
      {
        path: 'listaDeUsuarios',
        component: ListaDeUsuariosComponent,
      },
      {
        path: 'nuevoUsuario',
        component: NuevoUsuarioComponent,
      },
      {
        path: 'cambiarPass',
        component: CambiarPassComponent,
      },
      {
        path: 'editarUsuario',
        component: EditarUsuarioComponent,
      },
    ]
  },
  {
    path: 'solicitante',
    component: SolicitanteComponent,
    children: [
      {
        path: 'misSolicitudesPendientes',
        component: ListaSoliSolicitantePendienteComponent,
      },
      {
        path: 'misSolicitudesAprobadas',
        component: ListaSoliSolicitanteAprobadaComponent,
      },
      {
        path: 'misSolicitudesFinalizadas',
        component: ListaSoliSolicitanteFinalizadaComponent,
      },
      {
        path: 'nuevaSolicitud',
        component: NuevaSolicitudComponent,
      },
      {
        path: 'cambiarPass',
        component: CambiarPassComponent,
      },
    ]
  },
  {
    path: 'responsable',
    component: ResponsableComponent,
    children: [
      {
        path: 'solicitudesPendienteUnidad',
        component: ListaSoliPendienteResponsableComponent,
      },
      {
        path: 'solicitudesAprobadaUnidad',
        component: ListaSoliAprobadaResponsableComponent,
      },
      {
        path: 'solicitudesRechazadaUnidad',
        component: ListaSoliFinalizadaResponsableComponent,
      },
      {
        path: 'cambiarPass',
        component: CambiarPassComponent,
      },
    ]
  },
];
