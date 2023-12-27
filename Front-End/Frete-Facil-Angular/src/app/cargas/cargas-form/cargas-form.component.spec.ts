import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CargasFormComponent } from './cargas-form.component';

describe('CargasFormComponent', () => {
  let component: CargasFormComponent;
  let fixture: ComponentFixture<CargasFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CargasFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CargasFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
