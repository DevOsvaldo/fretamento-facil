import { TestBed } from '@angular/core/testing';

import { CarregamentoService } from './carregamento.service';

describe('CarregamentoService', () => {
  let service: CarregamentoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CarregamentoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
