import { response } from 'express';
import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit, Output, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Observable, catchError, of, tap } from 'rxjs';
import { CondutorService } from '../../services/condutor.service';
import { ErrorDialogComponent } from '../../../shared/components/error-dialog/error-dialog.component';
import { Condutor } from '../../models/condutor';
import { Location } from '@angular/common';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ConfirmationDialogComponent } from '../../../shared/components/confirmation-dialog/confirmation-dialog.component';
import { CondutorPage } from '../../models/condutor-page';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-condutor-list',
  templateUrl: './condutor-list.component.html',
  styleUrl: './condutor-list.component.css',
})
export class CondutorListComponent implements OnInit {
  condutor$: Observable<CondutorPage> | null = null;
  dataSource: MatTableDataSource<CondutorPage> | null = null;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  pageIndex = 0;
  pageSize = 10;
  condutor: Condutor[] = [];
  cargaId!: number;
  displayedColumns = [
    'id',
    'nome',
    'cpf',
    'endereco',
    'tipo_Veiculo',
    'capacidadeVeiculo',
    'situacaoCondutor',
    'actions',
  ];
  constructor(
    public dialog: MatDialog,
    private router: Router,
    private atualRoute: ActivatedRoute,
    private condutorService: CondutorService,
    private snackBar: MatSnackBar,
    private location: Location
  ) {
    this.buscar();
  }

  ngOnInit(): void {
    //Capturando os parametros da rota para definir  cargaId
    console.log('to no condutor list');
    this.atualRoute.params.subscribe((params) => {
      this.cargaId = params['id'];
      console.log('Parâmetros da rota:', params);
      console.log('Parametros do condutor ' + this.cargaId);
    });
  }
  //Capturando o id do condutor selecionado
  onSelect(condutorId: number): void {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data: 'Tem certeza que deseja selecionar este Condutor?',
    });
    dialogRef.afterClosed().subscribe((result: boolean) => {
      if (result) {
        console.log('Condutor ID: ', condutorId);
        console.log('Carga Id: ', this.cargaId);
        //passando ambos os parametros para executar o metodo.
        this.condutorService.carregarCarga(condutorId, this.cargaId).subscribe(
          (response) => {
            this.snackBar.open('Carga selecionada com sucesso', '🚛', {
              duration: 3000,
              horizontalPosition: 'center',
              verticalPosition: 'top',
            });
            this.buscar();
            console.log('Carga carregada com sucesso:', response);
          },
          (error) => {
            this.snackBar.open(
              'Carga não está pronta para ser carregada',
              'X',
              {
                duration: 3000,
                horizontalPosition: 'center',
                verticalPosition: 'top',
              }
            );
            console.error('Erro ao carregar carga:', error);
          }
        );
      }
    });
  }

  buscar(
    pageEvent: PageEvent = { length: 0, pageIndex: 0, pageSize: 10 }
  ): void {
    this.condutor$ = this.condutorService
      .findAll(pageEvent.pageIndex, pageEvent.pageSize)
      .pipe(
        tap(() => {
          (this.pageIndex = pageEvent.pageIndex),
            (this.pageSize = pageEvent.pageSize);
        }),
        catchError((error) => {
          this.onError('Erro ao carregar dados');
          return of({ condutor: [], totalElements: 0, totalPages: 0 });
        })
      );
  }
  onError(errorMsg: string) {
    this.dialog.open(ErrorDialogComponent, {
      data: errorMsg,
    });
  }

  onAdd() {
    this.router.navigate(['new'], { relativeTo: this.atualRoute }); //{ relativeTo: this.atualRoute });
  }
  onBack() {
    if (this.location.path().includes('condutorlist')) {
      this.router.navigate(['dashboard']);
    } else this.location.back();
  }

  onEdit(condutor: Condutor) {
    if (
      condutor &&
      typeof condutor === 'object' &&
      'id' in condutor &&
      condutor.id
    ) {
      const id = condutor.id;
      console.log('console do list' + id);

      // Aqui você pode adicionar a chamada para obter o condutor atual do backend
      // Você pode usar seu serviço para buscar os detalhes do condutor com base no ID
      this.condutorService.getCondutorById(id).subscribe(
        (condutorAtual: Condutor) => {
          // Certifique-se de tratar os detalhes do condutor obtido do backend conforme necessário

          // Agora você pode chamar o método updateAndNavigate com o objeto Condutor atualizado
          this.updateAndNavigate(condutorAtual);
        },
        (erro) => {
          console.error('Erro ao obter detalhes do condutor:', erro);
        }
      );
    } else {
      console.error('Condutor ou condutor.id é undefined ou inválido.');
    }
  }

  private updateAndNavigate(condutorAtual: Condutor) {
    // Navegue para a rota 'edit' com base no ID do condutor
    this.router.navigate(['condutorlist', 'edit', condutorAtual.id], {});
  }

  onRemove(condutor: Condutor) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data: 'Tem certeza que deseja remover esse Condutor?',
    });
    dialogRef.afterClosed().subscribe((result: boolean) => {
      if (result) {
        this.condutorService.remove(condutor.id).subscribe(
          () => {
            this.buscar();
            this.snackBar.open('Condutor Removido com Sucesso!', 'X', {
              duration: 2000,
              verticalPosition: 'top',
              horizontalPosition: 'center',
            });
          },
          (error) => this.onError('Erro ao tentar remover Condutor!')
        );
      }
    });
  }
}
