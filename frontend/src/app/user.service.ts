import { Injectable } from '@angular/core';
import { Http, Response } from "@angular/http";
import { AuthenticationService } from "./authentication.service";
import { User } from "./model/user.model";
import { Observable } from "rxjs/Observable";
import { PasswordChangeData } from './model/dto/passwordChangeData.dto';

@Injectable()
export class UserService {

  baseUrl: string;

  constructor(private http: Http,
    private authService: AuthenticationService) {
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
      .get(this.baseUrl + `/api/users/${id}/${password}`, this.authService.getOptions())
      .map((res: Response) => res.json().correct);
  }

  changePassword(id: number, oldPassword: string, newPassword: string): Observable<any> {
    return this.http
      .put(this.baseUrl + `/api/users/changePass`, new PasswordChangeData(id, oldPassword, newPassword),
        this.authService.getOptions())
      .map(res => res.json());
  }

  signup(user: User): Observable<any> {
    return this.http.post(this.baseUrl + "/auth/signup", user, this.authService.getOptions())
      .map((res: Response) => res.json());
  }

}
