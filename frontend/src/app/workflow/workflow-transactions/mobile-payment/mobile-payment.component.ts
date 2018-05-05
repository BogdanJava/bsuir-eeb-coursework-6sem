import { Component, OnInit } from '@angular/core';
import { CardTransaction } from '../../../model/transaction.model';
import { CardsService } from '../../cards.service';
import { Card } from '../../../model/card.model';
import { Location } from '@angular/common';

@Component({
  selector: 'app-mobile-payment',
  templateUrl: './mobile-payment.component.html',
  styleUrls: ['./mobile-payment.component.css']
})
export class MobilePaymentComponent implements OnInit {

  transaction: CardTransaction = new CardTransaction();
  cards: Card[];
  selectedCard: Card;
  phone: number;
  cardCurrentSum: number;
  sumIncorrect: boolean = false;
  transactionSuccess: boolean = null;
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
    this.sumIncorrect = this.transaction.diff > this.cardCurrentSum;
    this.transaction.name = "Mobile payment";
    this.transaction.description = `Payment for mobile phone ${this.phone} from card`;
    this.transaction.card = this.selectedCard;
    this.transaction.diff = -this.transaction.diff;
    this.cardService.saveTransaction(this.transaction).subscribe(result => {
      this.transaction = new CardTransaction();
      this.transactionSuccess = result.ok;
      setTimeout(() => {
        this.transactionSuccess = null;
      }, 3000);
    });
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
