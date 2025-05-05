import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import {provideHttpClient, withInterceptors} from '@angular/common/http';
import {authInterceptor} from "./utils/extra/auth-interceptor.service";

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    //provideHttpClient(withInterceptors([authInterceptor])), // para reenviar las sessionID
    provideHttpClient(), // Proveer HttpClientModule, pero porque??
    provideRouter(routes)
  ]
};
