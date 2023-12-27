import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CondutorRoutingModule } from './condutor-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CondutorService } from '../services/condutor.service';
import { AppMaterialModule } from '../../shared/app-material/app-material.module';

import { CondutorComponent } from '../condutor/condutor.component';
import { CondutorListComponent } from '../condutor-list/condutor-list/condutor-list.component';

@NgModule({
  declarations: [CondutorComponent, CondutorListComponent],
  imports: [
    CommonModule,
    CondutorRoutingModule,
    AppMaterialModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  exports: [],
  providers: [CondutorService],
})
export class CondutorModule {}
