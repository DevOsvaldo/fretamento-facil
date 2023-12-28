import { Component, OnInit } from '@angular/core';
import { FormGroup, NonNullableFormBuilder, Validators } from '@angular/forms';
import {
  MatSnackBar,
  MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition,
} from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';

import { AuthService } from '../../pages/login/auth.service';
import { SharedService } from '../../shared/shared.service';
import { CondutorService } from '../services/condutor.service';
import { Condutor } from './../models/condutor';

@Component({
  selector: 'app-condutor',
  templateUrl: './condutor.component.html',
  styleUrls: ['./condutor.component.css'],
})
export class CondutorComponent implements OnInit {
  ready: boolean = false;
  condutorForm!: FormGroup;
  condutor = {} as Condutor;
  condutores: Condutor[] = [];
  displayedColumns = [
    'nome',
    'cpf',
    'endereco',
    'tipoVeiculo',
    'capacidadeVeiculo',
    'situacaoMotorista',
  ];
  isEditing: boolean = false;
  requiredData: boolean = true;
  tipo_Veiculo: any;
  condutorId = this.condutor.id;
  motoristaId: number | undefined;
  constructor(
    private condutorService: CondutorService,
    private sharedService: SharedService,
    private _snackBar: MatSnackBar,
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute,
    private formBuilder: NonNullableFormBuilder
  ) {}

  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'top';
  openSnackBar() {
    this._snackBar.open('Sucesso!', 'Go!', {
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
    });
  }

  //

  tiposVeiculo = ['Carreta Trucada', 'Carreta Toco', 'Carreta Vanderleia'];
  capacidadesPorTipoVeiculo: { [tipo_Veiculo: string]: number[] } = {
    'Carreta Trucada': [30000, 31999, 32500],
    'Carreta Toco': [24900, 25200, 27000],
    'Carreta Vanderleia': [33000, 33500, 34500],
  };

  //
  ngOnInit() {
    this.route.params.subscribe((params) => {
      console.log('to no edit ' + params['id']);
      const id = params['id'];
      if (id) {
        this.isEditing = true;
        this.requiredData = false;
        this.populaForm(id);
      } else {
        this.iniciaForm();
      }
    });
    this.condutorForm.get('tipo_Veiculo')?.valueChanges.subscribe((value) => {
      this.atualizarHabilitacaoCapacidadeVeiculo(value);
    });
    this.condutorForm.patchValue({
      cpf: this.sharedService.gerarCpfFicticio(), // Preencher automaticamente com um CPF fictício
    });
  }

  private atualizarHabilitacaoCapacidadeVeiculo(tipo_Veiculo: string) {
    const capacidadeVeiculoControl = this.condutorForm.get('capacidadeVeiculo');

    if (tipo_Veiculo && this.capacidadesPorTipoVeiculo[tipo_Veiculo]) {
      capacidadeVeiculoControl?.enable();
    } else {
      capacidadeVeiculoControl?.disable();
    }
  }
  populaForm(id: number) {
    console.log('NO POPULAFORM');
    this.condutorService.getCondutorById(id).subscribe((data) => {
      console.log('pegando o data ' + data);

      this.condutorForm = this.formBuilder.group({
        login: data.login,
        password: data.password,
        role: data.role,
        id: data.id,
        nome: data.nome,
        cpf: data.cpf,
        cep: data.cep,
        endereco: data.endereco,
        tipo_Veiculo: data.tipo_Veiculo,
        capacidadeVeiculo: data.capacidadeVeiculo,
        situacaoCondutor: data.situacaoCondutor,
      });
      this.ready = true;
    });
  }
  iniciaForm() {
    console.log('INICIA FORM ');
    this.condutorForm = this.formBuilder.group({
      login: ['', Validators.required],
      password: ['', Validators.required],
      role: ['USER'],
      nome: ['', Validators.required],
      cpf: '',
      cep: ['', Validators.required],
      endereco: ['', Validators.required],
      tipo_Veiculo: ['', Validators.required],
      capacidadeVeiculo: [{ value: '', disabled: true }, Validators.required],
      situacaoCondutor: ['', Validators.required],
    });
    this.ready = true;
  }

  submitForm() {
    if (this.condutorForm.valid) {
      const formValues = this.condutorForm.value as Condutor;
      const condutorId = formValues.id;
      console.log('Condutor ID:', condutorId);
      console.log('Dados do Formulário:', this.condutorForm.value);
      if (condutorId == null || condutorId <= 0) {
        // Criar novo
        this.condutorService.saveCondutor(formValues).subscribe(
          (response) => {
            this._snackBar.open(
              'Cadastro realizado com sucesso!',
              'Tudo Pronto',
              { duration: 5000 }
            );
            console.log('Dados do formulário:', response);
            this.onBack();
          },
          (error) => {
            console.error('Erro ao salvar condutor:', error);
            this._snackBar.open(
              'Cadastro não realizado, tente novamente!',
              'X',
              {
                duration: 5000,
              }
            );
            this.onReset();
          }
        );
      } else {
        this.condutorService.update(condutorId, formValues).subscribe(
          (response) => {
            this._snackBar.open(
              'Cadastro atualizado com sucesso!',
              'Tudo Pronto',
              { duration: 2000 }
            );
            if (response.role === 'USER') {
              this.router.navigate(['condutordash']);
            } else {
              this.onBack();
            }
            console.log('Dados do formulário:', response);
          },
          (error) => {
            console.error('Erro ao salvar condutor:', error);
            this._snackBar.open(
              'Cadastro não realizado, tente novamente!',
              'X',
              {
                duration: 5000,
              }
            );
            this.onReset();
          }
        );
      }
    }
  }
  buscarEnderecoPorCep() {
    const cepControl = this.condutorForm.get('cep');
    if (cepControl && cepControl.value && cepControl.value.length === 8) {
      const cep = cepControl.value;
      if (cep && cep.length === 8) {
        // Certifique-se de que o CEP tem 8 dígitos
        this.sharedService.buscarEnderecoPorCep(cep).subscribe(
          (endereco) => {
            // Atualize os campos do formulário com os dados do endereço retornado
            this.condutorForm.patchValue({
              endereco: `Logradouro: ${endereco.logradouro}, Bairro: ${endereco.bairro}, Cidade: ${endereco.localidade},
               UF: ${endereco.uf}`,

              // ... outros campos de endereço que deseja preencher
            });
            console.log(endereco);
          },
          (error) => {
            console.error('Erro ao buscar endereço:', error);
          }
        );
      }
    } else {
      console.error('CEP inválido ou incompleto.');
    }
  }

  onReset() {
    this.condutorForm.reset();
  }
  onBack() {
    this.router.navigate(['condutorlist']);
  }
}
