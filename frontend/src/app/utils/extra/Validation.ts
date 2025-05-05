import { AbstractControl, ValidationErrors } from '@angular/forms';

export function numeroMayorACeroValidator(control: AbstractControl): ValidationErrors | null {
  const value = control.value;
  if (value === null || value === '') {
    return null; // No validar si el campo está vacío (deja que `required` maneje esto)
  }
  const esNumero = /^\d+$/.test(value); // Verifica si es un número
  if (!esNumero || +value <= 0) {
    return { numeroMayorACeroValidator: 'Ingrese un número mayor a cero' }; // Devuelve un mensaje de error
  }
  return null; // Devuelve null si no hay errores
}
