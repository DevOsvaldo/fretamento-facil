import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CargasRoutingModule } from './cargas-routing.module';
import { AppMaterialModule } from '../../../shared/app-material/app-material.module';

import { CargasListComponent } from '../../cargas-list/cargas-list.component';
import { CargasComponent } from '../../cargas/cargas.component';
import { CargasFormComponent } from '../../cargas-form/cargas-form.component';
import { CondutorModule } from '../../../condutores/modulo/condutor.module';

@NgModule({
  declarations: [CargasComponent, CargasFormComponent, CargasListComponent],
  imports: [
    CommonModule,
    CargasRoutingModule,
    AppMaterialModule,
    FormsModule,
    ReactiveFormsModule,
    CondutorModule,
  ],
})
export class CargasModule {}
