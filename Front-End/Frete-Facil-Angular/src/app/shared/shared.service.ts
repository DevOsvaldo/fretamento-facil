import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SharedService {
  private readonly viaCepBaseUrl = 'https://viacep.com.br/ws';

  constructor(private http: HttpClient) {}

  buscarEnderecoPorCep(cep: string): Observable<any> {
    const url = `${this.viaCepBaseUrl}/${cep}/json`;
    return this.http.get(url);
  }

  gerarCpfFicticio(): string {
    const randomDigit = (): number => Math.floor(Math.random() * 10);
    const cpfNumbers: number[] = Array.from({ length: 9 }, randomDigit);

    const digito1: number =
      cpfNumbers.reduce((acc, digit, index) => acc + digit * (10 - index), 0) %
      11;
    cpfNumbers.push(digito1 === 10 ? 0 : digito1);

    const digito2: number =
      cpfNumbers.reduce((acc, digit, index) => acc + digit * (11 - index), 0) %
      11;
    cpfNumbers.push(digito2 === 10 ? 0 : digito2);

    // Formatação do CPF
    const formattedCpf = cpfNumbers
      .join('')
      .replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4');

    return formattedCpf.toString();
  }
}
