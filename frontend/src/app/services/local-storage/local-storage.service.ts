import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LocalStorageService {

  constructor() { }

  // Método para guardar JSON en localStorage
  setItem(key: string, value: any): void {
    localStorage.setItem(key, JSON.stringify(value));
  }

  // Método para obtener JSON de localStorage
  getItem(key: string): any | null {
    const storedData = localStorage.getItem(key);
    if (storedData) {
      return JSON.parse(storedData);
    }
    return null;
  }

  // Método para eliminar un item de localStorage
  removeItem(key: string): void {
    localStorage.removeItem(key);
  }
}
