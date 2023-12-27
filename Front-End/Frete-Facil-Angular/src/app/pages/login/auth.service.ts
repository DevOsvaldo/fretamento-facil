import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { error } from 'console';
import { jwtDecode } from 'jwt-decode';
import { Observable } from 'rxjs';

import { LoginResponse } from '../../interfaces/LoginResponse';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiBaseUrl = 'http://localhost:8080/auth/login';
  private apiRegistro = 'http://localhost:8080/auth';

  constructor(
    private http: HttpClient,
    private router: Router,
    private jwtHelper: JwtHelperService
  ) {}

  decodeToken(token: string): any {
    try {
      const decodedToken = jwtDecode(token);
      console.log(decodedToken); // Mova esta linha para dentro do bloco try
      return decodedToken;
    } catch (error) {
      console.error('Error decoding token:', error);
      return null;
    }
  }
  getUserIdFromToken(token: string): number | null {
    try {
      const decodedToken: any = jwtDecode(token);
      const userId: number = decodedToken.userId;
      return userId;
    } catch (error) {
      console.error('Error decoding token:', error);
      return null;
    }
  }
  signup(signupData: any): Observable<any> {
    return this.http.post(`${this.apiRegistro}/register`, signupData);
  }

  login(loginData: {
    login: string;
    password: string;
  }): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(this.apiBaseUrl, loginData);
  }
  getUserInfo(token: string | null): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });
    return this.http.get<any>(`${this.apiBaseUrl}`, { headers });
  }
  getUserById(token: string | null, userId: number): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });
    return this.http.get<any>(`${this.apiBaseUrl}/${userId}`, { headers });
  }
  isAuthenticated(): boolean {
    const token = localStorage.getItem('token');
    return !!token;
  }
  logout(): void {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

  updatePassword(id: number | null, newPassword: string): Observable<any> {
    const url = `${this.apiRegistro}/changepassword/${id}?newPassword=${newPassword}`;
    return this.http.put(url, newPassword);
  }
  hasRole(requiredRole: string): boolean {
    // Recupera o token do armazenamento local
    const token = localStorage.getItem('loginToken');

    // Verifica se o token está presente
    if (token) {
      try {
        // Decodifica o token para obter as informações do usuário
        const decodedToken: any = this.jwtHelper.decodeToken(token);

        // Verifica se o usuário tem a role necessária
        return (
          decodedToken &&
          decodedToken.roles &&
          decodedToken.roles.includes(requiredRole)
        );
      } catch (error) {
        console.error('Error decoding token:', error);
        return false;
      }
    }

    return false; // Retorna falso se não houver token
  }
}
