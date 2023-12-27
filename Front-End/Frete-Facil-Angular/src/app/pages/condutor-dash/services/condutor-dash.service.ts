import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from '../../login/auth.service';

@Injectable({
  providedIn: 'root',
})
export class CondutorDashService {
  private apiUrl = 'http://localhost:8080/auth/login';

  constructor(private http: HttpClient, private authService: AuthService) {}
  /*
  getUserInfo(token: string, userId: number): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });
    return this.http.get<any>(`${this.apiUrl}/${userId}`, { headers });
  }*/
}
