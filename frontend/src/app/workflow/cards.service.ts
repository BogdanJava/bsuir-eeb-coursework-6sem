import { Injectable } from '@angular/core';
import { AuthenticationService } from '../authentication.service';
import { JwtService } from '../jwt.service';
import { Observable } from 'rxjs/Observable';
import { Http } from '@angular/http';
import { Card } from '../model/card.model';
import { CardTransaction } from '../model/transaction.model';

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

  getCard(cardId: number): Observable<any> {
    return this.http.get(this.baseUrl + `/api/cards/${cardId}`, this.authService.getOptions());
  }

  getAllCards(): Observable<any> {
    return this.http.get(this.baseUrl + `/api/cards?userId=${this.userId}`, this.authService.getOptions());
  }

  saveCard(card: Card): Observable<any> {
    return this.http.post(this.baseUrl + '/api/cards', { card: card, userId: this.userId },
      this.authService.getOptions());
  }

  getCardBalance(cardId: number): Observable<any> {
    return this.http.get(this.baseUrl + `/api/cards/balance/${cardId}`, this.authService.getOptions());
  }

  getAllTransactions(cardId: number): Observable<any> {
    return this.http.get(this.baseUrl + `/api/cards/${cardId}/transactions`, this.authService.getOptions());
  }

  getTransactionById(transactionId: number): Observable<any> {
    return this.http.get(this.baseUrl + `/api/cards/transaction/${transactionId}`, this.authService.getOptions());
  }

  saveTransaction(transaction: CardTransaction): Observable<any> {
    return this.http.post(this.baseUrl + '/api/cards/transactions', transaction, this.authService.getOptions());
  }

  isPasswordCorrect(password: string, cardId: number): Observable<any> {
    return this.http.get(this.baseUrl + `/api/cards/${cardId}/isPasswordCorrect?password=${password}`, this.authService.getOptions());
  }

}
