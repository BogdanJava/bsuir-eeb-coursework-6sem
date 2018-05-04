import { Component, OnInit } from '@angular/core';
import { AccountService } from '../account.service';
import { Account } from '../../../model/account.model';
import { Card } from '../../../model/card.model';
import { CardsService } from '../../cards.service';
import { NgForm } from '@angular/forms';
import { IMyDpOptions } from 'mydatepicker';


@Component({
  selector: 'app-deposits',
  templateUrl: './deposits.component.html',
  styleUrls: ['./deposits.component.css']
})
export class DepositsComponent implements OnInit {

  public myDatePickerOptions: IMyDpOptions = {
    dateFormat: 'yyyy-mm-dd',
  };

  deposits: Account[] = null;
  newAccount: Account;
  cards: Card[];

  cardCurrentSum: number;
  sumIncorrect: boolean = false;
  transactionSuccess: boolean = null;
  cardSelectElement: any;
  formInvalid: boolean = false;

  constructor(private accountService: AccountService,
    private cardService: CardsService) {
    this.accountService.getAllAccounts().subscribe(result => {
      if (result.ok) {
        this.deposits = result.json();
        if (this.deposits.length == 0) {
          this.newAccount = new Account();
          this.newAccount.accountType = "DEPOSIT";
          cardService.getAllCards().subscribe(result => {
            if (result.ok) {
              this.cards = result.json();
            }
          });
        }
      }
    });
  }

  onCardChange() {
    if (!this.cardSelectElement) {
      this.cardSelectElement = document.getElementById("cardSelect");
    }
    var cardId = this.cardSelectElement.options[this.cardSelectElement.selectedIndex].value;
    this.newAccount.card = this.cards.find(card => card.id == cardId);
    this.cardCurrentSum = null;
    this.cardService.getCardBalance(cardId).subscribe(result => {
      if (result.ok) {
        console.log(result);
        this.cardCurrentSum = result.json().balance;
      }
    })
  }

  ngOnInit() {
  }

  onSubmit(form: NgForm) {
    this.formInvalid = false;
    if (!form.valid) {
      this.makeFormInvalid();
    } else {
      this.newAccount.closeDate = this.newAccount.closeDate.jsdate;
      let today = new Date();
      today.setHours(0, 0, 0, 0);
      this.newAccount.openDate = new Date();
      if (this.newAccount.closeDate.getTime() <= today || this.newAccount.startSum > this.cardCurrentSum) {
        this.makeFormInvalid();
        return;
      }
      else {
        console.log(this.newAccount);
        this.accountService.openAccount(this.newAccount).subscribe(result => {
          console.log(result);
        });
      }
    }
  }

  makeFormInvalid() {
    this.formInvalid = true;
    setTimeout(() => {
      this.formInvalid = false;
    }, 3000);
  }

  currencySelect: any;
  onCurrencyChange() {
    if (!this.currencySelect) {
      this.currencySelect = document.getElementById('currencySelect');
    }
    if (this.currencySelect.value) {
      this.cardService.getAllCardsByCurrency(this.currencySelect.value).subscribe(result => {
        if (result.ok) {
          this.cards = result.json();
        }
      })
    }
  }

}
