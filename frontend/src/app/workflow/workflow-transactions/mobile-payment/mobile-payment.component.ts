import { Component, OnInit } from '@angular/core';
import { CardTransaction } from '../../../model/transaction.model';
import { CardsService } from '../../cards.service';
import { Card } from '../../../model/card.model';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-mobile-payment',
  templateUrl: './mobile-payment.component.html',
  styleUrls: ['./mobile-payment.component.css']
})
export class MobilePaymentComponent implements OnInit {

  transaction: CardTransaction = new CardTransaction();
  cards: Card[];
  selectedCardId: number;
  phone: number;
  sum: number;

  constructor(cardService: CardsService) {
    cardService.getAllCards().subscribe(result => {
      if (result.ok) {
        this.cards = result.json();
        console.log(this.cards);
      }
    });
  }

  ngOnInit() {
  }

  onSubmit(forn: NgForm) {
    //todo: implement transaction logic
  }

}
