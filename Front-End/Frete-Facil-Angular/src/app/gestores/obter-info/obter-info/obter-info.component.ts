import { Component, OnInit } from '@angular/core';
import { GestorService } from '../../services/gestor.service';

import { catchError, of, switchMap, toArray } from 'rxjs';
import { Condutor } from '../../../condutores/models/condutor';
import { InformacoesCarregamento } from '../../models/informacoescarregamento';
import { Router } from '@angular/router';

@Component({
  selector: 'app-obter-info',
  templateUrl: './obter-info.component.html',
  styleUrl: './obter-info.component.css',
})
export class ObterInfoComponent implements OnInit {
  informacoesCarregamento!: InformacoesCarregamento;

  constructor(private gestorService: GestorService, private router: Router) {}

  ngOnInit() {}

  obter() {
    this.gestorService.obterInformacoesCarregamento().subscribe(
      (data: InformacoesCarregamento) => {
        this.informacoesCarregamento = data;
      },
      (error) => {
        console.error('Erro ao obter informações de carregamento:', error);
      }
    );
  }

  onDash() {
    this.router.navigate(['dashboard']);
  }
}
