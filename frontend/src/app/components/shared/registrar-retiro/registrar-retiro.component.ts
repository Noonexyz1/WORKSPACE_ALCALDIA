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

  listaDeDocumentos: number[] = new Array(3);

  arreglo: number[] = [];

  constructor() {
    this.listaDeDocumentos[0] = 1;
    this.listaDeDocumentos[1] = 2;
    this.listaDeDocumentos[2] = 3;

    //Valor por defecto
    this.arreglo.push(0);
  }

  metodoPrueba() {
    alert("Hola mundo");
  }

  inputValue: string = "";
  selectedValue: string = "";
  onInputChange(event: Event) {
    // Obtener el elemento <input> que disparó el evento
    const inputElement = event.target as HTMLInputElement;
    const selectElement = document.getElementById('idCategory') as HTMLSelectElement;

    // Obtener el valor del input
    this.inputValue = inputElement.value;
    this.selectedValue = selectElement.value;
  }

  tamDocumentos: number = this.listaDeDocumentos.length - 1;
  tamArrayLimite: number = this.listaDeDocumentos.length;
  contador: number = 0;
  botonPush() {
    if (this.tamDocumentos > 0 && this.selectedValue != "" && this.inputValue != "") {
      let tamanoAreglo = this.arreglo.length - 1;
      this.arreglo[tamanoAreglo] = 1;

      // generamos nuevo elemento con condicion
      if(this.contador < this.tamArrayLimite){
        this.arreglo.push(0);
        this.contador++;
      }

      this.tamDocumentos--;

      // Reiniciar los campos del formulario
      this.selectedValue = '';
      this.inputValue = '';
    }
  }

  botonPop() {
    this.arreglo.pop();
  }

  botonNotaDePedido() {
    this.botonPush();
    //Aqui se va a evaluar si no hay redundancia con los id de las fotocopias
    console.log(this.arreglo)
    alert("Nota de Pedido");
  }
}
