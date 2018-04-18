import { Injectable } from '@angular/core';
import { AuthenticationService } from '../authentication.service';
import { JwtService } from '../jwt.service';
import { Observable } from 'rxjs/Observable';
import { Http } from '@angular/http';
import { Card } from '../model/card.model';

@Injectable()
export class CardsService {

  baseUrl: string;
  userId: number;

  constructor(private authService: AuthenticationService,
    private jwtService: JwtService,
    private http: Http) {
    this.baseUrl = authService.baseUrl;
    this.userId = jwtService.getClaim('id');
  }

  getCard(): Observable<any> {
    return this.http.get(this.baseUrl + `/api/cards/${this.userId}`, this.authService.getOptions());
  }

  getAllCards(): Observable<any> {
    return this.http.get(this.baseUrl + `/api/cards?userId=${this.userId}`, this.authService.getOptions());
  }

  saveCard(card: Card): Observable<any> {
    return this.http.post(this.baseUrl + '/api/cards', { card: card, userId: this.userId },
      this.authService.getOptions());
  }

}
