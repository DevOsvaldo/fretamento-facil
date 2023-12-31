import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, first, retry, tap, throwError } from 'rxjs';
import { Condutor } from '../models/condutor';
import { CondutorPage } from '../models/condutor-page';

@Injectable({
  providedIn: 'root',
})
export class CondutorService {
  //headers
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  };
  constructor(private httpClient: HttpClient) {}
  private readonly API = 'http://localhost:8080/condutor';

  findAll(
    page = 0,
    pageSize = 10
  ): Observable<{
    condutor: Condutor[];
    totalElements: number;
    totalPages: number;
  }> {
    return this.httpClient
      .get<CondutorPage>(this.API, { params: { page, pageSize } })
      .pipe(first());
  }

  /*
  findAll() {
    return this.httpClient.get<Condutor[]>(this.API).pipe(
      first()
      //delay(5000),
      //tap((condutorList) => console.log('lista impressa'))
    );
  }*/
  //obtem todos os condutores teste
  getCondutores(): Observable<Condutor[]> {
    return this.httpClient
      .get<Condutor[]>(this.API)
      .pipe(retry(2), catchError(this.handleError.bind(this)));
  }

  getCondutorById(id: number): Observable<Condutor> {
    return this.httpClient
      .get<Condutor>(`${this.API}/${id}`)
      .pipe(retry(), catchError(this.handleError.bind(this)));
  }

  saveCondutor(record: Partial<Condutor>) {
    return this.httpClient
      .post<Condutor>(this.API + '/cadastroUser', record)
      .pipe(first());
  }
  carregarCarga(condutorId: number, cargaId: number): Observable<any> {
    const url = `${this.API}/carregar`;
    const params = { condutorId, cargaId };

    return this.httpClient.post(url, {}, { params });
  }

  update(
    condutorId: number,
    updatedCondutor: Partial<Condutor>
  ): Observable<Condutor> {
    const url = `${this.API}/editar/${condutorId}`;
    return this.httpClient.put<Condutor>(url, updatedCondutor).pipe(first());
  }
  remove(id: number) {
    console.log('REMOVER CONDUTOR ID:' + id);
    return this.httpClient
      .delete<Condutor>(`${this.API}/delete/${id}`)
      .pipe(first());
  }
  modificarSituacao(id: number, novaSituacao: string): Observable<Condutor> {
    const url = `${this.API}/situacao/${id}`;
    const body = { situacaoCondutor: novaSituacao };

    return this.httpClient.put<Condutor>(url, body);
  }
  handleError(error: any): Observable<any> {
    console.error('Erro no serviço:', error);
    throw error;
  }
}
