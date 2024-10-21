import { TestBed } from '@angular/core/testing';
import { SubjectUsuarioUnidadService } from './subject-usuario-unidad.service';


describe('SubjectUsuarioUnidadService', () => {
  let service: SubjectUsuarioUnidadService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SubjectUsuarioUnidadService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
