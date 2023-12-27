import { Component, OnInit } from '@angular/core';
import { Carga } from '../../cargas/models/carga';
import { Observable, catchError, of } from 'rxjs';
import { GestorService } from '../services/gestor.service';
import { MatDialog } from '@angular/material/dialog';
import { ErrorDialogComponent } from '../../shared/components/error-dialog/error-dialog.component';
import { AuthService } from '../../pages/login/auth.service';
import { Gestor } from '../models/gestor';

@Component({
  selector: 'app-gestor',
  templateUrl: './gestor.component.html',
  styleUrl: './gestor.component.css',
})
export class GestorComponent implements OnInit {
  cargas$!: Observable<Carga[]>;
  cargas: Carga[] = [];
  gestor!: Gestor;
  readonly displayedColumns = [
    'id',
    'nomeCliente',
    'enderecoCliente',
    'pesoCarga',
    'valorFrete',
    'tipoVeiculo',
    'situacaoCarga',
    'actions',
  ];
  constructor(
    private gestorService: GestorService,
    public dialog: MatDialog,
    private authService: AuthService
  ) {}
  ngOnInit(): void {
    this.buscar();
  }
  buscar() {
    this.cargas$ = this.gestorService.findAll().pipe(
      catchError((error) => {
        this.onError('Erro ao carregar dados');
        return of([]);
      })
    );
  }
  onError(errorMsg: string) {
    this.dialog.open(ErrorDialogComponent, {
      data: errorMsg,
    });
  }

  onInserir(cargas: Carga) {
    const cargaId = cargas.id;
    // const gestorId = this.gestor.id;
    console.log('gestor id: ', 'carga id', cargaId);
    //this.gestorService.inserirCarga(cargaId,)
  }
}
