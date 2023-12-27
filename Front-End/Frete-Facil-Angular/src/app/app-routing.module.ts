import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CargasComponent } from './cargas/cargas/cargas.component';
import { CargasRoutingModule } from './cargas/modulo/cargas/cargas-routing.module';
import { CondutorListComponent } from './condutores/condutor-list/condutor-list/condutor-list.component';
import { CondutorRoutingModule } from './condutores/modulo/condutor-routing.module';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { LayoutComponent } from './pages/layout/layout.component';
import { LoginComponent } from './pages/login/login.component';
import { CargasFormComponent } from './cargas/cargas-form/cargas-form.component';
import { CargasLegendaComponent } from './cargas/cargas-legenda/cargas-legenda/cargas-legenda.component';
import { CarregamentoComponent } from './carregamentos/carregamento/carregamento.component';
import { CondutorDashComponent } from './pages/condutor-dash/condutor-dash/condutor-dash.component';
import { SentinelGuard } from './guard/sentinel/sentinel.guard';
import { GestorComponent } from './gestores/gestor/gestor.component';
import { GestorFormComponent } from './gestores/gestor-form/gestor-form.component';
const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'login' },
  { path: 'login', component: LoginComponent },
  {
    path: 'cargaslist',
    component: CargasComponent,
    children: [
      {
        path: 'edit/:id',
        component: CargasFormComponent,
        canActivate: [SentinelGuard],
        data: { requiredRole: ['ROLE_ADMIN', 'ROLE_MOD'] },
      },
    ],
  },
  { path: 'condutordash', component: CondutorDashComponent },
  {
    path: 'condutorlist',
    component: CondutorListComponent,
    canActivate: [SentinelGuard],
    data: { requiredRole: ['ROLE_ADMIN', 'ROLE_MOD'] },
  },
  {
    path: 'dashboard',
    component: LayoutComponent,
    children: [
      {
        path: '',
        component: DashboardComponent,
        canActivate: [SentinelGuard],
        data: { requiredRole: ['ROLE_ADMIN', 'ROLE_MOD'] },
      },
    ],
  },
  {
    path: 'cargaslist',
    component: CargasComponent,
    children: [
      {
        path: ':id/condutorlist',
        component: CondutorListComponent,
        canActivate: [SentinelGuard],
        data: { requiredRole: ['ROLE_ADMIN', 'ROLE_MOD'] },
      },
    ],
  },
  { path: 'gestor', component: GestorFormComponent },
  {
    path: 'gestor-dash',
    component: GestorComponent,
    canActivate: [SentinelGuard],
    data: { requiredRole: ['ROLE_ADMIN', 'ROLE_MOD'] },
  },
  { path: 'cargaslegenda', component: CargasLegendaComponent },
  { path: 'carregamento', component: CarregamentoComponent },
  { path: '**', redirectTo: 'login' }, // Redireciona para 'login' se a rota não for encontrada
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
    CondutorRoutingModule,
    CargasRoutingModule,
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
