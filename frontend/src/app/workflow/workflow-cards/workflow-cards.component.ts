import { Component, OnInit } from '@angular/core';
import { Card } from '../../model/card.model';
import { CardsService } from '../cards.service';

@Component({
  selector: 'app-workflow-cards',
  templateUrl: './workflow-cards.component.html',
  styleUrls: ['./workflow-cards.component.css']
})
export class WorkflowCardsComponent implements OnInit {

  ngOnInit(): void {
  }

  cardList: Card[] = new Array<Card>();

  constructor(private cardsService: CardsService) {
    this.cardsService.getAllCards().subscribe(
      result => {
        if (result.ok) {
          this.cardList = result.json();
          setTimeout(() => {
            console.log(this.cardList);
            for (let i = 0; i < this.cardList.length; i++) {
              let card: Card = this.cardList[i];
              let color = "";
              console.log();
              switch (card.cardType) {
                case "CLASSIC": color = 'rgb(70, 70, 172)'; break;
                case "SILVER": color = 'rgb(187, 187, 235)'; break;
                case "GOLD": color = 'rgb(199, 186, 65)'; break;
                case "PLATINUM": color = 'rgb(210, 239, 241)'; break;
                case "DARK_MATTER": color = 'rgb(17, 17, 34)'; break;
              }
              document.getElementById(`card${card.id}`).style.color = color;
            }
          }, 50);
        } else {
          console.log('Not found');
        }
      }, err => {
        console.log(err);
      });
  }

}
