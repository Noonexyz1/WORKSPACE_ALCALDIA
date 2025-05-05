import { TestBed } from '@angular/core/testing';

import { SubjectDocumentoService } from './subject-documento.service';

describe('SubjectDocumentoService', () => {
  let service: SubjectDocumentoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SubjectDocumentoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
