import { Injectable } from '@angular/core';
import { Http, Response } from "@angular/http";
import { AuthenticationService } from "./authentication.service";
import { User } from "./model/user";
import { Observable } from "rxjs/Observable";

@Injectable()
export class UserService {

  baseUrl: string;

  constructor(private http: Http, private authService: AuthenticationService) {
    this.baseUrl = this.authService.baseUrl;
  }

  getUser(id: number): Observable<User> {
    return this.authService.getResource(`/users/${id}`);
  }

  updateUser(user: User): Observable<User> {
    let options = this.authService.getOptions();
    return this.http.put(this.baseUrl + "/api/users", user, options).map((res: Response) => res.json());
  }

  checkPasswordIsCorrect(password: string, id: number): Observable<boolean> {
    return this.http
      .put(this.baseUrl + `/api/users/${id}?password=${password}`, null, this.authService.getOptions())
      .map((res: Response) => res.json().correct);
  }

}
