import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CondutorDashComponent } from './condutor-dash.component';

describe('CondutorDashComponent', () => {
  let component: CondutorDashComponent;
  let fixture: ComponentFixture<CondutorDashComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CondutorDashComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CondutorDashComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
