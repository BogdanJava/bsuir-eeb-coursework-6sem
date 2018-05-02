import { Component, OnInit } from '@angular/core';
import { CardTransaction } from '../../../model/transaction.model';
import { CardsService } from '../../cards.service';
import { Card } from '../../../model/card.model';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-money-transfer',
  templateUrl: './money-transfer.component.html',
  styleUrls: ['./money-transfer.component.css']
})
export class MoneyTransferComponent implements OnInit {

  transaction: CardTransaction = new CardTransaction();
  cards: Card[];
  selectedCard: Card;
  destCard: string;
  cardCurrentSum: number;
  sumIncorrect: boolean = false;
  passwordIncorrect: boolean = false;
  transactionSuccess: boolean = null;
  password: string;
  cardSelectElement: any;


  constructor(private cardService: CardsService,
    private _location: Location) {
    cardService.getAllCards().subscribe(result => {
      if (result.ok) {
        this.cards = result.json();
      }
    });
  }

  ngOnInit() {
  }

  onSubmit() {
    this.cardService.isPasswordCorrect(this.password, this.selectedCard.id).subscribe(result => {
      if (result.ok) {
        if (result.json().passwordCorrect) {
          this.sumIncorrect = this.transaction.diff > this.cardCurrentSum;
          this.transaction.name = "Money transfering";
          this.transaction.description = `Transfer money to card #${this.destCard}  from card`;
          this.transaction.card = this.selectedCard;
          this.transaction.diff = -this.transaction.diff;
          this.transaction.transactionType = "TRANSFER";
          this.transaction.additionalInfo = this.destCard;
          this.cardService.saveTransaction(this.transaction).subscribe(result => {
            this.transaction = new CardTransaction();
            this.transactionSuccess = result.ok;
            setTimeout(() => {
              this.transactionSuccess = null;
            }, 3000);
          });
        } else {
          this.passwordIncorrect = true;
          setTimeout(() => {
            this.passwordIncorrect = false;
          }, 3000);
        }
      }
    })

  }

  onCardChange() {
    if (!this.cardSelectElement) {
      this.cardSelectElement = document.getElementById("cardSelect");
    }
    var cardId = this.cardSelectElement.options[this.cardSelectElement.selectedIndex].value;
    this.selectedCard = this.cards.find(card => card.id == cardId);
    this.cardCurrentSum = null;
    this.cardService.getCardBalance(cardId).subscribe(result => {
      if (result.ok) {
        console.log(result);
        this.cardCurrentSum = result.json().balance;
      }
    })
  }

  goBack() {
    this._location.back();
  }

}
