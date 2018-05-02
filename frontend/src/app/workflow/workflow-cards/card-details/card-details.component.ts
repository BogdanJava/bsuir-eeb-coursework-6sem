import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CardsService } from '../../cards.service';
import { Card } from '../../../model/card.model';
import { Subscription } from 'rxjs/Subscription';
import { CardTransaction } from '../../../model/transaction.model';

@Component({
  selector: 'app-card-details',
  templateUrl: './card-details.component.html',
  styleUrls: ['./card-details.component.css']
})
export class CardDetailsComponent implements OnInit, OnDestroy {

  private sub: Subscription;
  private cardId: number;
  private card: Card = null;
  private balance: number = null;
  private transactions: CardTransaction[] = null;
  private selectedTransaction: CardTransaction = null;

  constructor(private route: ActivatedRoute,
    private cardsService: CardsService) { }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      this.cardId = +params['cardId'];
      this.cardsService.getCard(this.cardId).subscribe(result => {
        if (result.ok) {
          this.card = result.json();
          console.log(this.card);
        }
      }, err => {
        console.log(err);
      });
      this.cardsService.getCardBalance(this.cardId).subscribe(result => {
        console.log(result);
        if(result.ok) {
          this.balance = result.json().balance;
        }
      });
      this.cardsService.getAllTransactions(this.cardId).subscribe(result => {
        if(result.ok) {
          this.transactions = result.json();
        }
      })
    });
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

  selectTransaction(transaction: CardTransaction) {
    this.selectedTransaction = transaction;
  }

}
