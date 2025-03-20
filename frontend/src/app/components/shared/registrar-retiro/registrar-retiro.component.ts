import { Component } from '@angular/core';

@Component({
  selector: 'app-registrar-retiro',
  standalone: true,
  imports: [],
  templateUrl: './registrar-retiro.component.html',
  styleUrl: './registrar-retiro.component.css'
})
export class RegistrarRetiroComponent {

  //A partir del ID de solicitud que me pasen, debo mostrar toda la informacion haciendo peticiones

  arreglo: number[] = [];

  constructor() {
    this.arreglo.push(1);
  }

  metodoPrueba() {
    alert("Hola mundo");
  }

  botonPush() {
    this.arreglo.push(3);
  }

  botonPop() {
    this.arreglo.pop();
  }

  botonNotaDePedido() {
    alert("Nota de Pedido");
  }
}
