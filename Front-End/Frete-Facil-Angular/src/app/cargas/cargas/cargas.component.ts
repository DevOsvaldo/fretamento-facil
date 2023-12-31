import { Location } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { error } from 'console';
import { catchError, Observable, of, tap } from 'rxjs';

import { ConfirmationDialogComponent } from '../../shared/components/confirmation-dialog/confirmation-dialog.component';
import { ErrorDialogComponent } from '../../shared/components/error-dialog/error-dialog.component';
import { SharedService } from '../../shared/shared.service';
import { Carga } from '../models/carga';
import { CargasService } from '../services/cargas.service';
import { CargaPage } from '../models/carga-page';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator, PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-cargas',
  templateUrl: './cargas.component.html',
  styleUrl: './cargas.component.scss',
})
export class CargasComponent implements OnInit {
  cargas$: Observable<CargaPage> | null = null;
  cargaId: number | undefined;
  dataSource: MatTableDataSource<CargaPage> | null = null;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  pageIndex = 0;
  pageSize = 10;
  constructor(
    private cargasService: CargasService,
    private sharedService: SharedService,
    public dialog: MatDialog,
    private router: Router,
    private route: ActivatedRoute,
    private location: Location,
    private snackBar: MatSnackBar
  ) {}
  refresh(
    pageEvent: PageEvent = { length: 0, pageIndex: 0, pageSize: 10 }
  ): void {
    this.cargas$ = this.cargasService
      .findAll(pageEvent.pageIndex, pageEvent.pageSize)
      .pipe(
        tap(() => {
          (this.pageIndex = pageEvent.pageIndex),
            (this.pageSize = pageEvent.pageSize);
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

  ngOnInit(): void {
    this.refresh();
  }
  carregarCarga(carga: Carga) {
    console.log(carga);
    const id = carga.id;
    this.router.navigate([id, 'condutorlist'], { relativeTo: this.route });
  }
  onAdd() {
    this.router.navigate(['newcargas'], { relativeTo: this.route });
  }
  gestDash() {
    console.log('funcionando');
    this.router.navigate(['gestor-dash']);
  }
  onWest() {
    this.location.back();
  }
  onEdit(carga: Carga) {
    if (carga && typeof carga === 'object' && 'id' in carga && carga.id) {
      const id = carga.id;
      console.log('console da lista das cargas: ' + id);

      this.cargasService.getById(id).subscribe(
        (cargaAtual: Carga) => {
          this.updateAndNav(cargaAtual);
        },
        (erro) => {
          console.log('Erro ao obter detalhes do carga: ', erro);
        }
      );
    } else {
      console.error('Carga ou carga.id é undefined ou inválido.');
    }
  }
  private updateAndNav(cargaAtual: Carga) {
    this.router.navigate(['edit', cargaAtual.id], { relativeTo: this.route });
  }
  onRemove(carga: Carga) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data: 'Tem certeza que deseja remover essa carga?',
    });
    dialogRef.afterClosed().subscribe((result: boolean) => {
      if (result) {
        this.cargasService.remove(carga.id).subscribe(
          () => {
            this.refresh();
            this.snackBar.open('Carga Removida com sucesso', 'X', {
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
}
