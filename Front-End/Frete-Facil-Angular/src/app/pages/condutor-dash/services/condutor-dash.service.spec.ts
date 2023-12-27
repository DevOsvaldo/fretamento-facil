import { TestBed } from '@angular/core/testing';

import { CondutorDashService } from './condutor-dash.service';

describe('CondutorDashService', () => {
  let service: CondutorDashService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CondutorDashService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
