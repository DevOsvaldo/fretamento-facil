import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AuthService } from '../../pages/login/auth.service';

@Component({
  selector: 'app-carregamento',
  templateUrl: './carregamento.component.html',
  styleUrl: './carregamento.component.css',
})
export class CarregamentoComponent implements OnInit {
  constructor(
    private router: Router,
    private location: Location,
    private service: AuthService
  ) {}
  ngOnInit(): void {}

  onBack() {
    this.location.back();
  }
  onUserPerfil() {
    this.router.navigate(['condutordash']);
  }
  onLogout() {
    this.service.logout();
  }
}
