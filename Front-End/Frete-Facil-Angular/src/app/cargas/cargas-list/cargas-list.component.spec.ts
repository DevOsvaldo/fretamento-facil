import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CargasListComponent } from './cargas-list.component';

describe('CargasListComponent', () => {
  let component: CargasListComponent;
  let fixture: ComponentFixture<CargasListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CargasListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CargasListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
