import {
  Component,
  EventEmitter,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import { Carga } from '../../cargas/models/carga';
import { Observable, catchError, of, tap } from 'rxjs';
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
import { CargaPage } from '../../cargas/models/carga-page';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator, PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-gestor',
  templateUrl: './gestor.component.html',
  styleUrl: './gestor.component.css',
})
export class GestorComponent implements OnInit {
  cargas$: Observable<CargaPage> | null = null;
  dataSource: MatTableDataSource<CargaPage> | null = null;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  pageIndex = 0;
  pageSize = 10;
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
  buscar(pageEvent: PageEvent = { length: 0, pageIndex: 0, pageSize: 10 }) {
    this.cargas$ = this.gestorService
      .findAll(pageEvent.pageIndex, pageEvent.pageSize)
      .pipe(
        tap(() => {
          this.pageIndex = pageEvent.pageIndex;
          this.pageSize = pageEvent.pageSize;
        }),
        catchError((error) => {
          console.error('Erro ao carregar dados', error);
          this.onError('Erro ao carregar dados: ' + error.message);
          return of({ carga: [], totalElements: 0, totalPages: 0 });
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
          (error) => this.onError('Erro ao tentar remover carga!' + error)
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
