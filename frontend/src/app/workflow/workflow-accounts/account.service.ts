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

}
