import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { LogoutService } from '../../services/logout.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css',
})
export class DashboardComponent {
  users: any[] = [];

  constructor(private http: HttpClient, private logoutService: LogoutService) {
  }
  logout() {
    this.logoutService.logout();
  }

}
