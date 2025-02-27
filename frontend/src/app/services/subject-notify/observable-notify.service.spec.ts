import { TestBed } from '@angular/core/testing';

import { ObservableNotifyService } from './observable-notify.service';

describe('ObservableNotifyService', () => {
  let service: ObservableNotifyService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ObservableNotifyService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
