import { TestBed } from '@angular/core/testing';
import { RootNavigateService } from './root-navigate.service';


describe('RootNavigateService', () => {
  let service: RootNavigateService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RootNavigateService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
