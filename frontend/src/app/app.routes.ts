import {Routes} from '@angular/router';
import {LoginComponent} from './share/login/login.component';
import {AdministradorComponent} from './layout/administrador/administrador.component';
import {ListaDeUsuariosComponent} from './page/lista-de-usuarios/lista-de-usuarios.component';
import {NuevoUsuarioComponent} from './page/nuevo-usuario/nuevo-usuario.component';
import {CambiarPassComponent} from './share/cambiar-pass/cambiar-pass.component';
import {
  ListaSoliSolicitantePendienteComponent
} from './page/lista-soli-solicitante-pendiente/lista-soli-solicitante-pendiente.component';
import {NuevaSolicitudComponent} from './share/nueva-solicitud/nueva-solicitud.component';
import {SolicitanteComponent} from './layout/solicitante/solicitante.component';
import {ResponsableComponent} from './layout/responsable/responsable.component';
import {EditarUsuarioComponent} from './page/editar-usuario/editar-usuario.component';
import {
  ListaSoliAutorizadaResponsableComponent
} from './page/lista-soli-responsable-autorizada/lista-soli-responsable-autorizada.component';
import {
  ListaSoliPendienteResponsableComponent
} from './page/lista-soli-responsable-pendiente/lista-soli-responsable-pendiente.component';
import {
  ListaSoliFinalizadaResponsableComponent
} from './page/lista-soli-responsable-finalizada/lista-soli-responsable-finalizada.component';

import {
  ListaSoliSolicitanteFinalizadaComponent
} from "./page/lista-soli-solicitante-finalizada/lista-soli-solicitante-finalizada.component";
import {
  ListaSoliSolicitanteAutorizadaComponent
} from "./page/lista-soli-solicitante-autorizada/lista-soli-solicitante-autorizada.component";

export const routes: Routes = [
  {path: '', redirectTo: "login", pathMatch: "full"},
  {path: 'login', component: LoginComponent},
  {path: 'cambiarPass', component: CambiarPassComponent},
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
        path: 'editarUsuario',
        component: EditarUsuarioComponent,
      }
    ]
  },
  {
    path: 'solicitante',
    component: SolicitanteComponent,
    children: [
      {
        path: 'solicitudesPendientes',
        component: ListaSoliSolicitantePendienteComponent,
      },
      {
        path: 'solicitudesAutorizadas',
        component: ListaSoliSolicitanteAutorizadaComponent,
      },
      {
        path: 'solicitudesFinalizadas',
        component: ListaSoliSolicitanteFinalizadaComponent,
      },
      {
        path: 'nuevaSolicitud',
        component: NuevaSolicitudComponent,
      }
    ]
  },
  {
    path: 'responsable',
    component: ResponsableComponent,
    children: [
      {
        path: 'solicitudesPendientes',
        component: ListaSoliPendienteResponsableComponent,
      },
      {
        path: 'solicitudesAutorizadas',
        component: ListaSoliAutorizadaResponsableComponent,
      },
      {
        path: 'solicitudesFinalizadas',
        component: ListaSoliFinalizadaResponsableComponent,
      }
    ]
  },
];
