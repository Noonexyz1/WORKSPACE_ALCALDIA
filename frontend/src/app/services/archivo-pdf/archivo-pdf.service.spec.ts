import { TestBed } from '@angular/core/testing';

import { ArchivoPdfService } from './archivo-pdf.service';

describe('ArchivoPdfService', () => {
  let service: ArchivoPdfService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ArchivoPdfService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
