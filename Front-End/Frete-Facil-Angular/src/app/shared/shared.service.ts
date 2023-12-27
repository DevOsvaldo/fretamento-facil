
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SharedService {
  private dadosCompartilhados = new BehaviorSubject<{
    cargaId?: number;
    condutorId?: number;
  }>({});

  dadosCompartilhados$ = this.dadosCompartilhados.asObservable();

  atualizarDados(novosDados: { cargaId?: number; condutorId?: number }): void {
    this.dadosCompartilhados.next(novosDados);
  }

  
  private condutorId: number | null = null;
  private cargaId: number | null = null;

  setCondutorId(id: number | undefined): void {
    this.condutorId = id !== undefined ? id : null;
  }

  getCondutorId(): number | null {
    return this.condutorId;
  }

  setCargaId(id: number | undefined): void {
    this.cargaId = id !== undefined ? id : null;
  }

  getCargaId(): number | null {
    return this.cargaId;
  }
}
