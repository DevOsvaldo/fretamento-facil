import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestorComponent } from './gestor.component';

describe('GestorComponent', () => {
  let component: GestorComponent;
  let fixture: ComponentFixture<GestorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GestorComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(GestorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
