import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { AuthenticationService } from './authentication.service';
import { IfObservable } from 'rxjs/observable/IfObservable';
import { Observable } from 'rxjs/Observable';
import { PhoneDTO } from './model/dto/phone.dto';
import { Phone } from './model/phone.model';

@Injectable()
export class PhoneService {

  baseUrl: string;

  constructor(private http: Http,
    private authService: AuthenticationService) {
    this.baseUrl = authService.baseUrl;
  }

  getPhonesByUserId(userId: number): Observable<Response> {
    return this.http.get(this.baseUrl + `/api/users/${userId}/phones`, this.authService.getOptions());
  }

  addPhone(phoneDTO: PhoneDTO): Observable<Response> {
    return this.http.post(this.baseUrl + '/api/phones', phoneDTO, this.authService.getOptions());
  }

  editPhone(phone: Phone): Observable<Response> {
    return this.http.put(this.baseUrl + '/api/phones', phone, this.authService.getOptions());
  }

  deletePhone(id: number): Observable<Response> {
    return this.http.delete(this.baseUrl + `/api/phones/${id}`, this.authService.getOptions());
  }

}
