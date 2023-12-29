import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CargasComponent } from '../../cargas/cargas.component';
import { CargasListComponent } from '../../cargas-list/cargas-list.component';
import { CargasFormComponent } from '../../cargas-form/cargas-form.component';
import { CondutorListComponent } from '../../../condutores/condutor-list/condutor-list/condutor-list.component';
import { SentinelGuard } from '../../../guard/sentinel/sentinel.guard';

const routes: Routes = [
  {
    path: 'cargaslist/newcargas',
    component: CargasFormComponent,
    // resolve: { carga: CargasResolver },
  },
  {
    path: 'cargaslist/edit/:id',
    component: CargasFormComponent,
    // resolve: { carga: CargasResolver },
  },
  {
    path: 'cargaslist',
    component: CargasComponent,
    children: [
      { path: 'edit/:id', component: CargasFormComponent },
      { path: ':id/condutorlist', component: CondutorListComponent },
    ],canActivate:[SentinelGuard], data:{requiredRole:['ROLE_ADMIN','ROLE_MOD']}
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CargasRoutingModule {}
