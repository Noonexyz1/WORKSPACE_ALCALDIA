import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { AdministradorComponent } from './components/administrador/administrador.component';
import { ListaDeUsuariosComponent } from './components/shared/lista-de-usuarios/lista-de-usuarios.component';
import { NuevoUsuarioComponent } from './components/shared/nuevo-usuario/nuevo-usuario.component';
import { CambiarPassComponent } from './components/shared/cambiar-pass/cambiar-pass.component';
import { OperadorComponent } from './components/operador/operador.component';
import { ListaDeSolicitudesComponent } from './components/shared/lista-de-solicitudes/lista-de-solicitudes.component';
import { NuevaSolicitudComponent } from './components/shared/nueva-solicitud/nueva-solicitud.component';
import { SolicitanteComponent } from './components/solicitante/solicitante.component';
import { ResponsableComponent } from './components/responsable/responsable.component';
import { EditarUsuarioComponent } from './components/shared/editar-usuario/editar-usuario.component';
import { ListaSoliAprobadaResponsableComponent } from './components/shared/lista-soli-responsable-aprobada/lista-soli-responsable-aprobada.component';
import { ListaSoliPendienteResponsableComponent } from './components/shared/lista-soli-responsable-pendiente/lista-soli-responsable-pendiente.component';
import { ListaSoliRechazadaResponsableComponent } from './components/shared/lista-soli-responsable-rechazada/lista-soli-responsable-rechazada.component';
import { ListaSoliPendienteOperadorComponent } from './components/shared/lista-soli-operador-pendiente/lista-soli-operador-pendiente.component';
import { ListaSoliIniciadaOperadorComponent } from './components/shared/lista-soli-operador-iniciada/lista-soli-operador-iniciada.component';
import { ListaSoliCompletaOperadorComponent } from './components/shared/lista-soli-operador-completada/lista-soli-operador-completada.component';

export const routes: Routes = [
    { path: '', redirectTo: "login", pathMatch: "full" },
    { path: 'login', component: LoginComponent },
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
        path: 'operador',
        component: OperadorComponent,
        children: [
            {
                path: 'listaDeSolicitudesPendientes',
                component: ListaSoliPendienteOperadorComponent,
            },
            {
                path: 'listaDeSolicitudesIniciadas',
                component: ListaSoliIniciadaOperadorComponent,
            },
            {
                path: 'listaDeSolicitudesCompletadas',
                component: ListaSoliCompletaOperadorComponent,
            },

            {
                path: 'cambiarEstado',
                component: NuevoUsuarioComponent,
            },
            {
                path: 'cambiarPass',
                component: CambiarPassComponent,
            },
        ]
    },
    {
        path: 'solicitante',
        component: SolicitanteComponent,
        children: [
            {
                path: 'misSolicitudes',
                component: ListaDeSolicitudesComponent,
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
                component: ListaSoliRechazadaResponsableComponent,
            },
            {
                path: 'cambiarPass',
                component: CambiarPassComponent,
            },
        ]
    },
];
