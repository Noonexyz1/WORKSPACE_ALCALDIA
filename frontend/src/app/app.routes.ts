import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ListaUsuariosComponent } from './components/dashboard/user-option/admin-option/lista-usuarios/lista-usuarios.component';
import { FormNuevoUsuarioComponent } from './components/dashboard/user-option/admin-option/form-nuevo-usuario/form-nuevo-usuario.component';
import { AdminOptionComponent } from './components/dashboard/user-option/admin-option/admin-option.component';
import { FormNuevoPassAdminComponent } from './components/dashboard/user-option/admin-option/form-nuevo-pass-admin/form-nuevo-pass-admin.component';
import { EditarUsuarioAdminComponent } from './components/dashboard/user-option/admin-option/editar-usuario-admin/editar-usuario-admin.component';

export const routes: Routes = [
    {path: "", redirectTo: "/login", pathMatch: "full"},
    {path: "login", component: LoginComponent},
    {
        path: "dashboard", 
        component: DashboardComponent,
        children: [
            {   
                path: "admin", 
                component: AdminOptionComponent,
                children: [
                    {   
                        path: "listaUsuarios", 
                        component: ListaUsuariosComponent,
                        children: [
                            {   
                                path: "editar", 
                                component: EditarUsuarioAdminComponent,
                            },
                        ]
                    },
                    {   
                        path: "nuevoUsuario", 
                        component: FormNuevoUsuarioComponent,
                    },        
                    {   
                        path: "cambioPass", 
                        component: FormNuevoPassAdminComponent,
                    },        
                ]
            },
            {   
                path: "operador", 
                component: AdminOptionComponent,
                children: [
                    {   
                        path: "listaUsuarios", 
                        component: ListaUsuariosComponent,
                    },
                    {   
                        path: "listaUsuarios", 
                        component: ListaUsuariosComponent,
                    },
                    {   
                        path: "nuevoUsuario", 
                        component: FormNuevoUsuarioComponent,
                    },        
                ]
            },
            {   
                path: "responsable", 
                component: AdminOptionComponent,
                children: [
                    {   
                        path: "listaUsuarios", 
                        component: ListaUsuariosComponent,
                    },
                    {   
                        path: "listaUsuarios", 
                        component: ListaUsuariosComponent,
                    },
                    {   
                        path: "nuevoUsuario", 
                        component: FormNuevoUsuarioComponent,
                    },        
                ]
            },
            {   
                path: "solicitante", 
                component: AdminOptionComponent,
                children: [
                    {   
                        path: "listaUsuarios", 
                        component: ListaUsuariosComponent,
                    },
                    {   
                        path: "listaUsuarios", 
                        component: ListaUsuariosComponent,
                    },
                    {   
                        path: "nuevoUsuario", 
                        component: FormNuevoUsuarioComponent,
                    },        
                ]
            },
        ]
    },
];
