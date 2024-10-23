import { TestBed } from '@angular/core/testing';
import { SubjectUserLoginService } from './subject-user-login.service';


describe('SubjectUsuarioUnidadService', () => {
  let service: SubjectUserLoginService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SubjectUserLoginService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
