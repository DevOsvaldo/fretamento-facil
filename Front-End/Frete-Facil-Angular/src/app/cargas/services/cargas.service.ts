import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Carga } from '../models/carga';
import { Observable, catchError, delay, first, map, retry, tap } from 'rxjs';
import { CargaPage } from '../models/carga-page';

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
  /*
  findAll() {
    return this.httpClient.get<CargaPage>(this.API).pipe(

    );
  }
*/
  findAll(
    page = 0,
    pageSize = 10
  ): Observable<{
    carga: Carga[];
    totalElements: number;
    totalPages: number;
  }> {
    return this.httpClient
      .get<CargaPage>(this.API, { params: { page, pageSize } })
      .pipe(
        map((cargaslist: CargaPage) => ({
          carga:
            cargaslist?.carga.filter(
              (carga) => carga.situacaoCarga !== 'INATIVA'
            ) || [],
          totalElements: cargaslist.totalElements,
          totalPages: cargaslist.totalPages,
        }))
      );
  }

  /*
  findAll(): Observable<{
    carga: Carga[];
    totalElements: number;
    totalPages: number;
  }> {
    return this.httpClient
      .get<CargaPage>(this.API)
      .pipe
      /*map((cargaslist: CargaPage) => {
        const cargas: Carga[] = cargaslist?.carga || [];

        return {
          carga: cargas.filter(
            (carga) =>
              carga.situacaoCarga === 'AGUARDANDO' ||
              carga.situacaoCarga === 'ATENDIDA'
          ),
          totalElements: cargaslist.totalElements,
          totalPages: cargaslist.totalPages,
        };
      })
      ();
  }*/

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
