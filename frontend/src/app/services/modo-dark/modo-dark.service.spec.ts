import { TestBed } from '@angular/core/testing';

import { ModoDarkService } from './modo-dark.service';

describe('ModoDarkService', () => {
  let service: ModoDarkService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ModoDarkService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
