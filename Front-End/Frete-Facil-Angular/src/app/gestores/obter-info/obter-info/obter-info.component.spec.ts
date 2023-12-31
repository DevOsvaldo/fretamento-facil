import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ObterInfoComponent } from './obter-info.component';

describe('ObterInfoComponent', () => {
  let component: ObterInfoComponent;
  let fixture: ComponentFixture<ObterInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ObterInfoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ObterInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
