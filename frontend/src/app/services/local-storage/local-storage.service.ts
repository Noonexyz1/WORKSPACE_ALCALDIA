import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LocalStorageService {

  constructor() { }

  // Metoodo para guardar JSON en localStorage
  setItem(key: string, value: any): void {
    localStorage.setItem(key, JSON.stringify(value));
  }

  // Metoodo para obtener JSON de localStorage
  getItem(key: string): any | null {
    const storedData = localStorage.getItem(key);
    if (storedData) {
      return JSON.parse(storedData);
    }
    return null;
  }

  // Metoodo para eliminar un item de localStorage
  removeItem(key: string): void {
    localStorage.removeItem(key);
  }
}
