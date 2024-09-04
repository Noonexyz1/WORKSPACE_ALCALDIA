import { TestBed } from '@angular/core/testing';

import { SubjectUserService } from './subject-user.service';

describe('SubjectUserService', () => {
  let service: SubjectUserService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SubjectUserService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
