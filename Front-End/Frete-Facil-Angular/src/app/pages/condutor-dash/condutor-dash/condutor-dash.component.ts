import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { AuthService } from '../../login/auth.service';
import { Condutor } from '../../../condutores/models/condutor';
import { CondutorService } from '../../../condutores/services/condutor.service';
import { HttpErrorResponse } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-condutor-dash',
  templateUrl: './condutor-dash.component.html',
  styleUrl: './condutor-dash.component.css',
})
export class CondutorDashComponent implements OnInit {
  token: any;
  condutor: Condutor | undefined;
  userId: number | null = null;
  login: string | null = null;
  password!: string;
  showPasswordUpdate: boolean = false;
  showSituacaoUpdate = false;
  constructor(
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar,
    private condutorService: CondutorService
  ) {
    if (typeof localStorage !== 'undefined') {
      this.token = localStorage.getItem('loginToken');
    }
  }
  ngOnInit(): void {
    const decodedToken = this.authService.decodeToken(this.token);
    if (decodedToken) {
      this.login = decodedToken.sub;
      this.userId = decodedToken.userId;
      console.log('UserID do token:', this.userId);
      this.getUserInfo();
      this.getUserById();
    } else {
      console.log('TOKEN INVALIDO');
    }
    console.log('Token:', this.token);
  }

  getUserById() {
    if (this.userId !== null) {
      this.authService.getUserById(this.token, this.userId).subscribe(
        (data) => {
          console.log('Informações do usuário por ID:', data);
          this.condutor = data as Condutor;
        },
        (error) => {
          console.error('Erro ao obter informações do usuário por ID:', error);
        }
      );
    } else {
      console.warn(
        'UserID é nulo. Não é possível obter informações do usuário por ID.'
      );
    }
  }
  getUserInfo() {}

  logoutOff() {
    this.authService.logout();
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
  newPassword() {
    this.showPasswordUpdate = true;
  }
  updatePassword(newPassword: string) {
    // Supondo que você tenha o ID do usuário disponível
    const id = this.userId; // Substitua pelo ID real do usuário

    this.authService.updatePassword(id, newPassword).subscribe(
      (response) => {
        // Verifique se a propriedade "message" existe
        if (response && response.message) {
          console.log('Senha atualizada com sucesso:', response.message);
          this.snackBar.open('Senha Atualizada', 'ok!', {
            duration: 3000,
            horizontalPosition: 'center',
            verticalPosition: 'top',
          });
          this.showPasswordUpdate = false;
        } else {
          console.log('Resposta inesperada:', response);
        }
      },
      (error) => {
        console.error('Erro ao atualizar senha:', error);

        // Adicione este trecho para imprimir detalhes adicionais do erro
        if (error instanceof HttpErrorResponse) {
          console.error('Status do erro:', error.status);
          console.error('Corpo do erro:', error.error);
        }

        this.showPasswordUpdate = false;
      }
    );
  }
  modificarSituacao(id: number, novaSituacao: string): void {
    this.condutorService.modificarSituacao(id, novaSituacao).subscribe(
      (condutorAtualizado) => {
        this.snackBar.open('Atualização com suceso', '👍', {
          duration: 900,
          horizontalPosition: 'center',
          verticalPosition: 'top',
        });
        console.log('Condutor atualizado:', condutorAtualizado);
      },
      (error) => {
        this.snackBar.open('Atualização incorreta ou Inpropria', 'X', {
          duration: 1000,
          verticalPosition: 'bottom',
          horizontalPosition: 'right',
        });
        console.error('Erro ao modificar a situação do condutor:', error);
      }
    );
  }
}
