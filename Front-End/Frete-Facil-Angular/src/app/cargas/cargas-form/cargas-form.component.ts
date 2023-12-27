import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { AbstractControl, AsyncValidatorFn, FormGroup, NonNullableFormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { error } from 'console';
import { response } from 'express';
import { Observable, of } from 'rxjs';

import { Carga } from '../models/carga';
import { CargasService } from '../services/cargas.service';

@Component({
  selector: 'app-cargas-form',
  templateUrl: './cargas-form.component.html',
  styleUrl: './cargas-form.component.css',
})
export class CargasFormComponent implements OnInit {
  form!: FormGroup;
  carga = {} as Carga;
  cargas: Carga[] = [];
  ready: boolean = false;

  constructor(
    private formBuilder: NonNullableFormBuilder,
    private service: CargasService,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      console.log('to no edit de cargas ' + params['id']);
      const id = params['id'];
      if (id) {
        this.populaForm(id);
      }
      this.iniciaForm();
    });
  }
  nonNegativeAsyncValidator(): AsyncValidatorFn {
    return (
      control: AbstractControl
    ): Observable<{ [key: string]: any } | null> => {
      const value = control.value;

      // Se o valor for inválido, você pode retornar um Observable que emite um objeto com o erro.
      if (value !== null && value !== undefined && value < 0) {
        return of({ nonNegative: { value: control.value } });
      }

      // Se o valor for válido, retorne um Observable que emite nulo.
      return of(null);
    };
  }

  populaForm(id: number) {
    this.service.getById(id).subscribe((data) => {
      console.log('pegando o data ' + data);

      this.form = this.formBuilder.group({
        id: data.id,
        nomeCliente: data.nomeCliente,
        enderecoCliente: data.enderecoCliente,
        pesoCarga: data.pesoCarga,
        valorFrete: data.valorFrete,
        tipoVeiculo: data.tipoVeiculo,
        situacaoCarga: data.situacaoCarga,
      });
    });
  }
  iniciaForm() {
    this.form = this.formBuilder.group({
      nomeCliente: ['', Validators.required],
      enderecoCliente: ['', Validators.required],
      pesoCarga: ['', Validators.required, this.nonNegativeAsyncValidator()],
      valorFrete: ['', Validators.required, this.nonNegativeAsyncValidator()],
      tipoVeiculo: ['', Validators.required],
      situacaoCarga: ['', Validators.required],
    });
  }
  onSubmit() {
    if (this.form.valid) {
      const formValues = this.form.value as Carga;
      const cargaId = formValues.id;
      console.log(cargaId);
      if (cargaId != null || cargaId == '') {
        //UPDATE
        this.service.update(cargaId, formValues).subscribe(
          (response) => {
            this.snackBar.open('Carga modificada com sucesso', '!ok', {
              duration: 2000,
            });
            console.log('Dados do formulário: ', response);
            this.onCancel();
          },
          (error) => {
            console.error('Erro ao salvar a alteração: ', error);
            console.log(error.status);
            if (error.status === '403') {
              this.snackBar.open(
                'Usuário não tem permissão para este tipo de ação!',
                'XXX',
                { duration: 3000 }
              );
            } else if (error.error && error.error.errorMessage) {
              // Se houver uma mensagem de erro específica na resposta da API
              this.snackBar.open(error.error.errorMessage, 'X', {
                duration: 2000,
              });
            } else {
              this.snackBar.open(
                'Alteração não realizada, tente novamente',
                'X',
                { duration: 2000 }
              );
            }
            this.onCancel();
          }
        );
      } else {
        this.service.saveCarga(this.form.value).subscribe(
          (result) => {
            this.onSuccess();
            console.log(result);
          },
          (error) => {
            this.onError();
          }
        );
      }
    }
  }

  onCancel() {
    this.location.back();
  }
  private onSuccess() {
    this.snackBar.open('Carga salva com sucesso!', '🌐', { duration: 3000 });
    this.onCancel();
  }
  private onError() {
    this.snackBar.open('Erro ao salvar carga.', '🛑', { duration: 3000 });
  }
}
