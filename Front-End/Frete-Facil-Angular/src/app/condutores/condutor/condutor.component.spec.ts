import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CondutorComponent } from './condutor.component';

describe('CondutorComponent', () => {
  let component: CondutorComponent;
  let fixture: ComponentFixture<CondutorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CondutorComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CondutorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
