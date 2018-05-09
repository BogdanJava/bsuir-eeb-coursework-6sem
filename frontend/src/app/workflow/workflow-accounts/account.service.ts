import { Injectable } from '@angular/core';
import { AuthenticationService } from '../../authentication.service';
import { JwtService } from '../../jwt.service';
import { Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Account } from '../../model/account.model';

@Injectable()
export class AccountService {

  baseUrl: string;
  userId: number;

  constructor(private authService: AuthenticationService,
    private jwtService: JwtService,
    private http: Http) {
    this.baseUrl = authService.baseUrl;
    this.userId = jwtService.getClaim('id');
  }

  openAccount(account: Account): Observable<any> {
    return this.http.post(this.baseUrl + '/api/accounts', account, this.authService.getOptions());
  }

  getAllAccounts(accountType: string): Observable<any> {
    return this.http.get(this.baseUrl + `/api/accounts?userId=${this.userId}&type=${accountType}`, this.authService.getOptions());
  }

  getAccountBalance(accountId: number): Observable<any> {
    return this.http.get(this.baseUrl + `/api/accounts/balance/${accountId}`, this.authService.getOptions());
  }

  getAccountById(accountId: number): Observable<any> {
    return this.http.get(this.baseUrl + `/api/accounts/${accountId}`, this.authService.getOptions());
  }

}
