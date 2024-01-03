import { AuthService } from './auth.service';
import { NavigationEnd, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { LoginResponse } from '../../interfaces/LoginResponse';
import { filter } from 'rxjs';
import {
  MatSnackBar,
  MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition,
} from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  loginObj: any = {
    login: '',
    password: '',
  };
  signupObj: any = {
    login: '',
    password: '',
    role: 'USER', // Valor padrão para o rádio
  };
  showErrorMessage: boolean = false;
  isSubmitting: boolean = false;
  showLoginError: boolean = false;
  showPasswordError: boolean = false;

  constructor(
    private authService: AuthService,
    private router: Router,
    private _snackBar: MatSnackBar
  ) {}
  //SnackBarConfigure

  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'top';
  openSnackBar() {
    this._snackBar.open('Sucesso!', 'Go!', {
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
    });
  }

  onSignup() {
    this.isSubmitting = true;
    if (!this.signupObj || !this.signupObj.login || !this.signupObj.password) {
      console.error('Invalid Register', this.signupObj);
      this.showErrorMessage = true;

      return;
    }
    this.authService.signup(this.signupObj).subscribe(
      (res: any) => {
        console.log(res);

        if (res) {
          if (this.signupObj.role === 'USER') {
            console.log('tipo user');
          } else {
            console.log(this.signupObj.role);
          }
          this._snackBar.open('Cadastro Realizado com Sucesso!', 'Pode logar!');
          localStorage.setItem('loginToken', res.token);
          this.router.navigateByUrl('/login');
        } else {
          console.log('This is the log message: ' + res.message);
          alert('Signup failed: ' + res.message);
        }
      },
      (error) => {
        console.error('An error occurred during signup:', error);
        this._snackBar.open(
          'Ocorreu um erro durante o registro.Porfavor Tente novamente',
          '',
          {
            duration: 3000,
            horizontalPosition: 'start',
            verticalPosition: 'top',
          }
        );
      }
    );
    this.isSubmitting = false;
  }

  onLogin() {
    if (!this.loginObj.login && !this.loginObj.password) {
      console.error('Invalid login object:', this.loginObj);
      this.showLoginError = true;
      this.showPasswordError = true;
      return;
    } else if (!this.loginObj.login) {
      this.showLoginError = true;
      this.showPasswordError = false;
    } else if (!this.loginObj.password) {
      this.showPasswordError = true;
      this.showLoginError = false;
    } else {
      // Resetar mensagens de erro
      this.showLoginError = false;
      this.showPasswordError = false;
    }

    // Se algum campo estiver vazio, não prossiga com o login
    if (this.showLoginError || this.showPasswordError) {
      return;
    }

    this.authService.login(this.loginObj).subscribe(
      (res: any) => {
        console.log(res); //isso res é o nome da resposta que eu defini;
        if (res.token) {
          //res.token é a resposta no caso o token recebido
          this.openSnackBar();
          localStorage.setItem('loginToken', res.token); //aqui eu salvo o token no localStorage

          const decodedToken = this.authService.decodeToken(res.token);
          if (decodedToken.roles.includes('ROLE_ADMIN')) {
            this.router.navigateByUrl('/dashboard');
          } else if (decodedToken.roles.includes('ROLE_MOD')) {
            this.router.navigateByUrl('/dashboard');
          } else if (decodedToken.roles.includes('ROLE_USER')) {
            this.router.navigateByUrl('condutordash');
          } else {
            alert('Usuário não reconhecido');
          }
        } else {
          console.log('This is the log message: ' + res.message);
        }
      },
      (error) => {
        console.error('Ocorreu um erro durante o login', error);
        this._snackBar.open(
          'Ocorreu um erro durante o login.Porfavor Tente novamente',
          '🛑',
          {
            duration: 3000,
            horizontalPosition: 'start',
            verticalPosition: 'top',
          }
        );
      }
    );
  }
}
