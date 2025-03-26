import { TestBed } from '@angular/core/testing';

import { SubjectDocumentoRetiroResponseService } from './subject-documento-retiro-response.service';

describe('SubjectDocumentoRetiroResponseService', () => {
  let service: SubjectDocumentoRetiroResponseService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SubjectDocumentoRetiroResponseService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
