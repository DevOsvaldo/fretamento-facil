import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, map, tap } from 'rxjs';
import { Carga } from '../../cargas/models/carga';
import { Gestor } from '../models/gestor';

@Injectable({
  providedIn: 'root',
})
export class GestorService {
  private baseUrl = 'http://localhost:8080/gestor'; // Substitua pela URL do seu backend
  private cargaUrl = 'http://localhost:8080/cargas';

  private gestorIdSubject: BehaviorSubject<number | null> = new BehaviorSubject<
    number | null
  >(null);

  constructor(private http: HttpClient) {}
  findAll() {
    return this.http
      .get<Carga[]>(this.cargaUrl)
      .pipe(
        map((cargaslist: Carga[]) =>
          cargaslist.filter((carga) => carga.situacaoCarga === 'INATIVA')
        )
      );
  }
  getGestorByUserId(userId: number): Observable<number | null> {
    return this.http
      .get<number>(`${this.baseUrl}/user/${userId}?userId=${userId}`)
      .pipe(
        tap((gestorId) => {
          this.gestorIdSubject.next(gestorId);
        })
      );
  }
  getGestorId(): Observable<number | null> {
    return this.gestorIdSubject.asObservable();
  }
  criarPerfilAdmin(gestor: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/cadastro`, gestor);
  }

  inserirCarga(cargaId: number, gestorId: number | null): Observable<Gestor> {
    console.log(
      'Inserindo carga: ',
      'cargaId:',
      cargaId,
      ' gestorId: ',
      gestorId
    );
    const url = `${this.baseUrl}/inserir?cargaId=${cargaId}&gestorId=${gestorId}`;
    return this.http.post<Gestor>(url,{});
  }

  alterarCarga(cargaId: number, cargaModificada: any): Observable<any> {
    return this.http.put<any>(
      `${this.baseUrl}/modificar/${cargaId}`,
      cargaModificada
    );
  }

  obterInformacoesCarregamento(): Observable<string> {
    return this.http.get<string>(`${this.baseUrl}/info`);
  }
}
