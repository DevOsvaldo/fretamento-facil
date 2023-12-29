import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CondutorComponent } from '../condutor/condutor.component';
import { CondutorListComponent } from '../condutor-list/condutor-list/condutor-list.component';
import { SentinelGuard } from '../../guard/sentinel/sentinel.guard';

const routes: Routes = [
  // ... Outras rotas do condutor
  {
    path: 'condutorlist/new',
    component: CondutorComponent,
  },
  { path: 'condutorlist/edit/:id', component: CondutorComponent },
  {
    path: 'cargaslist/:id/condutorlist',
    component: CondutorListComponent,
    canActivate: [SentinelGuard],
    data: { requiredRole: ['ROLE_ADMIN', 'ROLE_MOD'] },
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CondutorRoutingModule {}
