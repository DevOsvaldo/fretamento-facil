import { TestBed } from '@angular/core/testing';

import { CondutorService } from './condutor.service';

describe('CondutorService', () => {
  let service: CondutorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CondutorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
