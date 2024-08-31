import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { DashadministradorComponent } from './components/dashadministrador/dashadministrador.component';

export const routes: Routes = [
    {path: "", redirectTo: "/login", pathMatch: "full"},
    {path: "login", component: LoginComponent},
    {path: "dashadministrador", component: DashadministradorComponent},
];
