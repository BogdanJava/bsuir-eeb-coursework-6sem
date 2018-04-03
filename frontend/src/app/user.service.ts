import {Injectable} from '@angular/core';
import {Http} from "@angular/http";
import {AuthenticationService} from "./authentication.service";
import {User} from "./model/user";
import {Observable} from "rxjs/Observable";

@Injectable()
export class UserService {

  constructor(private http: Http, private authService: AuthenticationService) {
  }

  getUser(id: number): Observable<User> {
    return this.authService.getResource(`users/${id}`);
  }

}
