import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CardsService } from '../../cards.service';
import { Card } from '../../../model/card.model';
import { Subscription } from 'rxjs/Subscription';

@Component({
  selector: 'app-card-details',
  templateUrl: './card-details.component.html',
  styleUrls: ['./card-details.component.css']
})
export class CardDetailsComponent implements OnInit, OnDestroy {

  private sub: Subscription;
  private cardId: number;
  private card: Card = null;

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
    });
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

}
