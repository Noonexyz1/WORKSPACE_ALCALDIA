import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";

export interface ItemDropDown {
  id: number;
  opcion: string;
}

export interface UsuarioData {
  idUser: string;
  ci: string;
  nombres: string;
  paterno: string;
  materno: string;
  correo: string;
  idRol: string;
  idCargo: string;
  idUni: string;
}

@Component({
  selector: 'app-formulario-usuario',
  standalone: true,
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './formulario-usuario.component.html'
})
export class FormularioUsuarioComponent implements OnInit {

  @Input() tituloFormulario: string = 'Titulo por defecto';
  @Input() listaDeCargos: ItemDropDown[] = [];
  @Input() listaDeRoles: ItemDropDown[] = [];
  @Input() listaDeUnidades: ItemDropDown[] = [];
  @Input() usuarioData: UsuarioData = {
    idUser: '',
    ci: '',
    nombres: '',
    paterno: '',
    materno: '',
    correo: '',
    idRol: '',
    idCargo: '',
    idUni: '',
  };

  @Output() usuarioDataPublisher: EventEmitter<UsuarioData> = new EventEmitter<UsuarioData>();

  //ESTADOS GLOBALES
  idRolSelected: number = 0;
  idCargoSelected: number = 0;
  idUnidadSelected: number = 0;

  usuarioFormData: FormGroup;


  constructor(
    private readonly formBuilder: FormBuilder) {

    this.usuarioFormData = this.formBuilder.group({
      idUser: [''],
      ci: ['', [Validators.required, Validators.minLength(6)]],
      nombres: ['', [Validators.required, Validators.minLength(3)]],
      paterno: ['', [Validators.required, Validators.minLength(3)]],
      materno: ['', [Validators.required, Validators.minLength(3)]],
      correo: ['', [Validators.required, Validators.email]],
      idRol: ['', Validators.required],
      idCargo: ['', Validators.required],
      idUni: ['', Validators.required],
    });

  }

  ngOnInit(): void {
    this.usuarioFormData = this.formBuilder.group({
      idUser: [this.usuarioData.idUser],
      ci: [this.usuarioData.ci, [Validators.required, Validators.minLength(6)]],
      nombres: [this.usuarioData.nombres, [Validators.required, Validators.minLength(3)]],
      paterno: [this.usuarioData.paterno, [Validators.required, Validators.minLength(3)]],
      materno: [this.usuarioData.materno, [Validators.required, Validators.minLength(3)]],
      correo: [this.usuarioData.correo, [Validators.required, Validators.email]],
      idRol: [this.usuarioData.idRol, Validators.required],
      idCargo: [this.usuarioData.idCargo, Validators.required],
      idUni: [this.usuarioData.idUni, Validators.required],
    });
  }

  botonRegistrarUsuario(): void {
    const usuarioData: UsuarioData = {
      idUser: this.usuarioFormData.value.idUser,
      ci: this.usuarioFormData.value.ci,
      nombres: this.usuarioFormData.value.nombres,
      paterno: this.usuarioFormData.value.paterno,
      materno: this.usuarioFormData.value.materno,
      correo: this.usuarioFormData.value.correo,
      idRol: this.idRolSelected + '',
      idCargo: this.idCargoSelected + '',
      idUni: this.idUnidadSelected + '',
    };
    this.usuarioDataPublisher.emit(usuarioData);
  }

}
