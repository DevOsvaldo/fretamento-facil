import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CargasLegendaComponent } from './cargas-legenda.component';

describe('CargasLegendaComponent', () => {
  let component: CargasLegendaComponent;
  let fixture: ComponentFixture<CargasLegendaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CargasLegendaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CargasLegendaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
