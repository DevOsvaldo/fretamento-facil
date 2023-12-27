import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class LogoutService {
  constructor(private router: Router) {}
  logout() {
    //limpa o local storage remover o token
    localStorage.removeItem('loginToken');
    //redireciona para pagina de login
    this.router.navigate(['/login']);
  }
}
