import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { LogoutService } from '../../services/logout.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.css',
})
export class LayoutComponent {
  constructor(
    private http: HttpClient,
    private logoutService: LogoutService,
    private router: Router,
    private route: ActivatedRoute
  ) {}
  onViewMot() {
    this.router.navigate(['condutorlist']);
  }
  onViewCarg() {
    this.router.navigate(['cargaslist']);
  }
  onViewGest() {
    this.router.navigate(['gestor']);
  }
  onCarreg() {
    this.router.navigate(['info']);
  }
  logout() {
    this.logoutService.logout();
  }
}
