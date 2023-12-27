import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CargasComponent } from './cargas.component';

describe('CargasComponent', () => {
  let component: CargasComponent;
  let fixture: ComponentFixture<CargasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CargasComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CargasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
