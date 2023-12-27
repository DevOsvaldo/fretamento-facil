import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { sentinelGuard } from './sentinel.guard';

describe('sentinelGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => sentinelGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
