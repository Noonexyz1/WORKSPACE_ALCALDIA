import { TestBed } from '@angular/core/testing';

import { SubjectIdSolicitudService } from './subject-id-solicitud.service';

describe('SubjectIdSolicitudService', () => {
  let service: SubjectIdSolicitudService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SubjectIdSolicitudService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
