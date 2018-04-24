import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkflowTransactionsComponent } from './workflow-transactions.component';

describe('WorkflowTransactionsComponent', () => {
  let component: WorkflowTransactionsComponent;
  let fixture: ComponentFixture<WorkflowTransactionsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkflowTransactionsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkflowTransactionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
