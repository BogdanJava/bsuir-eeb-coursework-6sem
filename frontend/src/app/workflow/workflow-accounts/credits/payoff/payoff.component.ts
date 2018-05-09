import { Component, OnInit } from '@angular/core';
import { CardsService } from '../../../cards.service';
import { AccountService } from '../../account.service';
import { ActivatedRoute } from '@angular/router';
import { Card } from '../../../../model/card.model';
import { Account } from '../../../../model/account.model';

@Component({
  selector: 'app-payoff',
  templateUrl: './payoff.component.html',
  styleUrls: ['./payoff.component.css']
})
export class PayoffComponent implements OnInit {

  credit: Account;
  cards: Card[];
  interestRate: number;

  constructor(private cardService: CardsService, private accountService: AccountService,
    private route: ActivatedRoute) {
    let accountId = +route.snapshot.params['accountId'];
    accountService.getAccountById(accountId).subscribe(result => {
      if (result.ok) {
        this.credit = result.json();
        accountService.getAccountBalance(accountId).subscribe(result => {
          if (result.ok) {
            this.credit.balance = result.json().balance;
            this.interestRate = result.json().interestRate;
          }
        });
        cardService.getAllCardsByCurrency(this.credit.currency).subscribe(result => {
          if (result.ok) {
            this.cards = result.json();
          }
        });
      }
      this.setStatusClass();
    });
  }

  ngOnInit() {
  }

  private setStatusClass() {
    setTimeout(() => {
      let status = document.getElementById('status');
      if (this.credit.accountStatus == "OPEN") {
        status.className += " status-open";
      } else {
        status.className += " status-closed";
      }
    }, 100);
  }

}
