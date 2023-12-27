import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Carga } from '../models/carga';

@Component({
  selector: 'app-cargas-list',
  templateUrl: './cargas-list.component.html',
  styleUrl: './cargas-list.component.css',
})
export class CargasListComponent implements OnInit {
  @Input() cargas: Carga[] = [];
  @Output() add = new EventEmitter(false);
  @Output() edit = new EventEmitter(false);
  @Output() west = new EventEmitter(false);
  @Output() remove = new EventEmitter(false);
  @Output() carregar = new EventEmitter(false);

  readonly displayedColumns = [
    'id',
    'nomeCliente',
    'enderecoCliente',
    'pesoCarga',
    'valorFrete',
    'tipoVeiculo',
    'situacaoCarga',
    'actions',
  ];
  constructor() {}

  ngOnInit(): void {}
  onAdd() {
    this.add.emit(true);
  }
  onWest() {
    this.west.emit(true);
  }
  onEdit(carga: Carga) {
    this.edit.emit(carga);
  }
  onDelete(carga: Carga) {
    this.remove.emit(carga);
  }
  carregarCarga(carga: Carga) {
    this.carregar.emit(carga);
  }
}
