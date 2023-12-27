import { TestBed } from '@angular/core/testing';

import { CargasService } from './cargas.service';

describe('CargasService', () => {
  let service: CargasService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CargasService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
