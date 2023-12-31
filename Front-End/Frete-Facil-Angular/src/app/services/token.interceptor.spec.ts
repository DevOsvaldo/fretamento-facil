import { HttpInterceptorFn } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';

describe('tokenInterceptor', () => {
  const interceptor: HttpInterceptorFn = (req, next) =>
    TestBed.runInInjectionContext(() => interceptor(req, next));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(interceptor).toBeTruthy();
  });
  if (typeof window !== 'undefined') {
    // Se estiver no navegador (cliente), então pode acessar o localStorage
    // Se você estiver usando um serviço, também pode verificar se está no navegador antes de usar o localStorage.
    // Exemplo: if (this.platform.isBrowser) { /* código que usa localStorage */ }
    const token = localStorage.getItem('loginToken');
  }
});
