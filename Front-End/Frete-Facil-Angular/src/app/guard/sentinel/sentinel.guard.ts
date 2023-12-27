import { Injectable } from '@angular/core';
import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  UrlTree,
  Router,
} from '@angular/router';
import { Observable, of } from 'rxjs';
import { AuthService } from '../../pages/login/auth.service';

@Injectable({
  providedIn: 'root',
})
export class SentinelGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    const requiredRole = route.data['requiredRole'] as string[];

    if (requiredRole.some((roles) => this.authService.hasRole(roles))) {
      // Verifique se o usuário possui alguma das roles requeridas
      return true;
    } else {
      this.router.navigate(['carregamento']);
      return of(false);
    }
  }
}
