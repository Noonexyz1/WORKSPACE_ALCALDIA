import { Injectable } from '@angular/core';
import { ArchivoPdfResponse } from '../../models/ArchivoPdfResponse';

@Injectable({
  providedIn: 'root'
})
export class ArchivoPdfService {

  constructor() { }

  descargarPdf(archivo: { nombreArchivo: string; archivo: string }): void {
    try {
      // Si tiene el prefijo 'data:application/pdf;base64,', elimínalo
      const base64Data = archivo.archivo.startsWith('data:application/pdf;base64,')
        ? archivo.archivo.split(',')[1]
        : archivo.archivo;

      // Convertir a ArrayBuffer directamente
      const byteCharacters = window.atob(base64Data);
      const byteNumbers = new Array(byteCharacters.length);
      for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
      }
      const byteArray = new Uint8Array(byteNumbers);

      // Crear Blob desde el ArrayBuffer
      const blob = new Blob([byteArray], { type: 'application/pdf' });
      const url = window.URL.createObjectURL(blob);

      // Crear enlace de descarga
      const a = document.createElement('a');
      a.href = url;
      a.download = archivo.nombreArchivo;
      a.click();

      // Liberar la URL después de la descarga
      window.URL.revokeObjectURL(url);
    } catch (error) {
      console.error('Error al procesar el archivo PDF:', error);
    }
  }

}
