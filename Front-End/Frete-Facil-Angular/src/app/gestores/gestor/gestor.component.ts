import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Carga } from '../../cargas/models/carga';
import { Observable, catchError, of } from 'rxjs';
import { GestorService } from '../services/gestor.service';
import { MatDialog } from '@angular/material/dialog';
import { ErrorDialogComponent } from '../../shared/components/error-dialog/error-dialog.component';
import { AuthService } from '../../pages/login/auth.service';
import { Gestor } from '../models/gestor';
import { Router } from '@angular/router';
import { ConfirmationDialogComponent } from '../../shared/components/confirmation-dialog/confirmation-dialog.component';
import { CargasService } from '../../cargas/services/cargas.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Location } from '@angular/common';

@Component({
  selector: 'app-gestor',
  templateUrl: './gestor.component.html',
  styleUrl: './gestor.component.css',
})
export class GestorComponent implements OnInit {
  cargas$!: Observable<Carga[]>;
  cargas: Carga[] = [];
  gestor!: Gestor;
  token: any;
  userId!: number;
  gestorId: number | null = null;
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
    private location: Location,
    private authService: AuthService,
    private cargasService: CargasService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}
  ngOnInit(): void {
    this.token = localStorage.getItem('loginToken');
    const decodedToken = this.authService.decodeToken(this.token);
    if (decodedToken) {
      this.userId = decodedToken.userId;
      console.log('UserID do token: ', this.userId);
    }
    this.buscar();
    console.log('Chamado getGestorById com userId: ', this.userId);
    this.gestorService.getGestorByUserId(this.userId).subscribe((gestorId) => {
      this.gestorId = gestorId;
    });
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
    const gestorId = this.gestorId;
    console.log('gestor id: ', this.gestorId, 'carga id', cargaId);
    this.gestorService.inserirCarga(cargaId, gestorId).subscribe((gestor) => {
      this.snackBar.open('CARGA ATUALIZADA PARA AGUARDANDO', '📦', {
        duration: 1000,
        horizontalPosition: 'start',
        verticalPosition: 'top',
      });
      console.log('Gestor inserido', gestor);
      this.buscar();
    });
  }

  onDelete(carga: Carga) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data: 'Tem certeza que deseja remover essa carga?',
    });
    dialogRef.afterClosed().subscribe((result: boolean) => {
      if (result) {
        this.cargasService.remove(carga.id).subscribe(
          () => {
            this.buscar();
            this.snackBar.open('Carga Removida com sucesso', '📦', {
              duration: 3000,
              verticalPosition: 'top',
              horizontalPosition: 'center',
            });
          },
          (error) => this.onError('Erro ao tentar remover carga!')
        );
      }
    });
  }
  onAdd() {
    this.router.navigate(['cargaslist', 'newcargas']);
  }
  onWest() {
    this.location.back();
  }
  onDash() {
    this.router.navigate(['dashboard']);
  }
}
