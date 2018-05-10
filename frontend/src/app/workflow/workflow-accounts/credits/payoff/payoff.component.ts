import { Component, OnInit } from '@angular/core';
import { CardsService } from '../../../cards.service';
import { AccountService } from '../../account.service';
import { ActivatedRoute } from '@angular/router';
import { Card } from '../../../../model/card.model';
import { Account } from '../../../../model/account.model';
import { NgForm } from '@angular/forms';
import { AccountTransaction } from '../../../../model/accountTransaction.model';
import { Location } from '@angular/common';

@Component({
  selector: 'app-payoff',
  templateUrl: './payoff.component.html',
  styleUrls: ['./payoff.component.css']
})
export class PayoffComponent implements OnInit {

  credit: Account;
  cards: Card[];
  interestRate: number;
  cardSelectElement;
  card: Card;
  balance: number;
  indebtedness: number;

  constructor(private cardService: CardsService, private accountService: AccountService,
    private route: ActivatedRoute, private _location: Location) {
    let accountId = +route.snapshot.params['accountId'];
    accountService.getAccountById(accountId).subscribe(result => {
      if (result.ok) {
        this.credit = result.json();
        accountService.getAccountBalance(accountId).subscribe(result => {
          if (result.ok) {
            this.credit.balance = result.json().balance;
            this.interestRate = result.json().interestRate;
            this.indebtedness = this.credit.balance + this.interestRate;
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

  onCardChange() {
    if (!this.cardSelectElement) {
      this.cardSelectElement = document.getElementById("cardSelect");
    }
    var cardId = this.cardSelectElement.options[this.cardSelectElement.selectedIndex].value;
    this.card = this.cards.find(card => card.id == cardId);
    this.balance = null;
    this.cardService.getCardBalance(cardId).subscribe(result => {
      if (result.ok) {
        console.log(result);
        this.balance = result.json().balance;
      }
    })
  }

  ngOnInit() {
  }

  goBack() {
    this._location.back();
  }

  onSubmit(form: NgForm) {
    if (form.valid && this.balance >= this.interestRate) {
      let payment = new AccountTransaction();
      payment.account = this.credit;
      payment.date = new Date();
      payment.account.startSum = this.credit.balance;
      payment.card = this.card;
      this.accountService.payoffCredit(payment).subscribe(result => {
        if (result.ok) {
          console.log(result);
        }
      });
    }
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
