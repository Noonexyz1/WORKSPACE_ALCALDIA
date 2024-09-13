import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { AdministradorComponent } from './components/administrador/administrador.component';
import { ListaDeUsuariosComponent } from './components/shared/lista-de-usuarios/lista-de-usuarios.component';
import { NuevoUsuarioComponent } from './components/shared/nuevo-usuario/nuevo-usuario.component';
import { CambiarPassComponent } from './components/shared/cambiar-pass/cambiar-pass.component';
import { GenerarReporteComponent } from './components/shared/generar-reporte/generar-reporte.component';
import { OperadorComponent } from './components/operador/operador.component';
import { ListaDeSolicitudesComponent } from './components/shared/lista-de-solicitudes/lista-de-solicitudes.component';
import { NuevaSolicitudComponent } from './components/shared/nueva-solicitud/nueva-solicitud.component';
import { SolicitanteComponent } from './components/solicitante/solicitante.component';
import { ResponsableComponent } from './components/responsable/responsable.component';

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
                path: 'generarReporte',
                component: GenerarReporteComponent,
            },
            {
                path: 'cambiarPass',
                component: CambiarPassComponent,
            },
        ]
    },
    {
        path: 'operador',
        component: OperadorComponent,
        children: [
            {
                path: 'listaDeSolicitudes',
                component: ListaDeSolicitudesComponent,
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
                path: 'solicitudesUnidad',
                component: ListaDeSolicitudesComponent,
            },
            {
                path: 'cambiarPass',
                component: CambiarPassComponent,
            },
        ]
    },
];
