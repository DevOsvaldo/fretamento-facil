import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CargasModule } from './cargas/modulo/cargas/cargas.module';
import { CargasService } from './cargas/services/cargas.service';
import { CondutorModule } from './condutores/modulo/condutor.module';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { LayoutComponent } from './pages/layout/layout.component';
import { AuthService } from './pages/login/auth.service';
import { LoginComponent } from './pages/login/login.component';
import { TokenInterceptor } from './services/token.interceptor';
import { AppMaterialModule } from './shared/app-material/app-material.module';
import { ConfirmationDialogComponent } from './shared/components/confirmation-dialog/confirmation-dialog.component';
import { ErrorDialogComponent } from './shared/components/error-dialog/error-dialog.component';
import { CargasLegendaComponent } from './cargas/cargas-legenda/cargas-legenda/cargas-legenda.component';
import { GestorComponent } from './gestores/gestor/gestor.component';
import { GestorFormComponent } from './gestores/gestor-form/gestor-form.component';
import { CarregamentoComponent } from './carregamentos/carregamento/carregamento.component';
import { CondutorDashComponent } from './pages/condutor-dash/condutor-dash/condutor-dash.component';
import { JWT_OPTIONS, JwtHelperService } from '@auth0/angular-jwt';
import { ObterInfoComponent } from './gestores/obter-info/obter-info/obter-info.component';



@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    LayoutComponent,
    DashboardComponent,
    ErrorDialogComponent,
    ConfirmationDialogComponent,
    CargasLegendaComponent,
    GestorComponent,
    GestorFormComponent,
    CarregamentoComponent,
    CondutorDashComponent,
    ObterInfoComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    CondutorModule,
    AppMaterialModule,
    CargasModule,
  ],
  providers: [
    AuthService,

    CargasService,
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
    { provide: JWT_OPTIONS, useValue: JWT_OPTIONS },
    JwtHelperService,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
