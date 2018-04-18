import { Component, OnInit } from '@angular/core';
import { Card } from '../../model/card.model';
import { CardsService } from '../cards.service';

@Component({
  selector: 'app-workflow-cards',
  templateUrl: './workflow-cards.component.html',
  styleUrls: ['./workflow-cards.component.css']
})
export class WorkflowCardsComponent {

  cardList: Card[] = new Array<Card>();

  constructor(private cardsService: CardsService) {
    this.cardsService.getAllCards().subscribe(
      result => {
        if (result.ok) {
          this.cardList = result.json();
        } else {
          console.log('Not found');
        }
      }, err => {
        console.log(err);
      });
  }

}
