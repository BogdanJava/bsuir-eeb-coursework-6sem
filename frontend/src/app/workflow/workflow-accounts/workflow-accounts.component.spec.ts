import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkflowAccountsComponent } from './workflow-accounts.component';

describe('WorkflowAccountsComponent', () => {
  let component: WorkflowAccountsComponent;
  let fixture: ComponentFixture<WorkflowAccountsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkflowAccountsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkflowAccountsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
