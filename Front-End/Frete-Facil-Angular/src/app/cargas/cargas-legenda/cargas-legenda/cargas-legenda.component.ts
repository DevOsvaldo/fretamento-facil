import { Location } from '@angular/common';
import { Component } from '@angular/core';

@Component({
  selector: 'app-cargas-legenda',
  templateUrl: './cargas-legenda.component.html',
  styleUrl: './cargas-legenda.component.css',
})
export class CargasLegendaComponent {
  constructor(private location: Location) {}
  onBack() {
    this.location.back();
  }
}
