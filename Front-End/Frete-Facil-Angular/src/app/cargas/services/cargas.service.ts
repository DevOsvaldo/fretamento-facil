import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Carga } from '../models/carga';
import { Observable, catchError, delay, first, map, retry, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CargasService {
  private readonly API = 'http://localhost:8080/cargas';

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  constructor(private httpClient: HttpClient, private router: Router) {}

  findAll() {
    return this.httpClient
      .get<Carga[]>(this.API)
      .pipe(
        map((cargaslist: Carga[]) =>
          cargaslist.filter(
            (carga) =>
              carga.situacaoCarga === 'AGUARDANDO' ||
              carga.situacaoCarga === 'ATENDIDA'
          )
        )
      );
  }
  getById(id: number): Observable<Carga> {
    return this.httpClient
      .get<Carga>(`${this.API}/${id}`)
      .pipe(retry(2), catchError(this.handleError.bind(this)));
  }

  saveCarga(record: Partial<Carga>) {
    return this.httpClient
      .post<Carga>(this.API + '/cadastro', record)
      .pipe(first());
  }

  update(cargaId: number, updateCarga: Partial<Carga>): Observable<Carga> {
    const url = `${this.API}/${cargaId}`;
    return this.httpClient.put<Carga>(url, updateCarga).pipe(first());
  }
  remove(id: number) {
    return this.httpClient.delete<Carga>(`${this.API}/${id}`).pipe(first());
  }
  handleError(error: any): Observable<any> {
    console.error('Erro no serviço:', error);
    throw error;
  }
}
