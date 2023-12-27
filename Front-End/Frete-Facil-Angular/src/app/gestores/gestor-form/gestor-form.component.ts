import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { GestorService } from '../services/gestor.service';
import { FormGroup, NonNullableFormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Gestor } from '../models/gestor';

@Component({
  selector: 'app-gestor-form',
  templateUrl: './gestor-form.component.html',
  styleUrl: './gestor-form.component.css',
})
export class GestorFormComponent implements OnInit {
  gestorForm!: FormGroup;
  gestor = {} as Gestor;

  displayedColumns = ['nome', 'cpf', 'cargo'];

  constructor(
    private gestorService: GestorService,
    private formBuilder: NonNullableFormBuilder,
    private snackBar: MatSnackBar,
    private location: Location
  ) {}
  ngOnInit(): void {
    this.iniciaForm();
  }

  iniciaForm() {
    this.gestorForm = this.formBuilder.group({
      login: ['', Validators.required],
      password: ['', Validators.required],
      role: ['MOD'],
      nome: ['', Validators.required],
      cpf: ['', Validators.required],
      cargo: ['', Validators.required],
    });
  }
  submitForm() {
    if (this.gestorForm.valid) {
      const formValues = this.gestorForm.value as Gestor;

      const gestorId = formValues.id;
      console.log('Gestor ID: ' + gestorId);
      console.log('Dados do Formulário: ', this.gestorForm.value);

      this.gestorService.criarPerfilAdmin(formValues).subscribe((response) => {
        this.snackBar.open('Cadastro Realizado com Sucesso', '👨🏽‍💼', {
          duration: 4000,
          horizontalPosition: 'center',
          verticalPosition: 'top',
        });
        console.log('Dados do gestor: ', response);
      });
    }
  }
  onBack() {
    this.location.back();
  }
  onReset() {
    this.gestorForm.reset();
  }
}
