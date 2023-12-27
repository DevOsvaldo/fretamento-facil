import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { Carga } from '../../cargas/models/carga';

@Injectable({
  providedIn: 'root',
})
export class GestorService {
  private baseUrl = 'http://localhost:8080/gestor'; // Substitua pela URL do seu backend
  private cargaUrl = 'http://localhost:8080/cargas';

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

  criarPerfilAdmin(gestor: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/cadastro`, gestor);
  }

  inserirCarga(cargaId: number, gestorId: number): Observable<any> {
    return this.http.post<any>(
      `${this.baseUrl}/inserir?cargaId=${cargaId}&gestorId=${gestorId}`,
      {}
    );
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
