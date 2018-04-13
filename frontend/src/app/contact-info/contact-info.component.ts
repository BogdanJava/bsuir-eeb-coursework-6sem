import { Component, OnInit } from '@angular/core';
import { PhoneService } from '../phone.service';
import { JwtService } from '../jwt.service';
import { Phone } from '../model/phone.model';
import { NgForm } from '@angular/forms';
import { PhoneDTO } from '../model/dto/phone.dto';

@Component({
  selector: 'app-contact-info',
  templateUrl: './contact-info.component.html',
  styleUrls: ['./contact-info.component.css']
})
export class ContactInfoComponent {

  deleteSuccess: boolean = null;
  hasError: boolean = false;;
  phones: any[] = null;
  newPhone: Phone = new Phone();
  userId: number = +this.jwtService.getClaim("id");

  constructor(private phoneServise: PhoneService,
    private jwtService: JwtService) {
    this.newPhone.type = "Mobile";
    phoneServise.getPhonesByUserId(jwtService.getClaim("id")).map(_ => _.json()).subscribe(phones => {
      this.phones = new Array();
      let index = 0;
      for (let phone of phones) {
        phone = { index: ++index, phone: phone };
        this.phones.push(phone);
      }
    });
  }

  addPhone() {
    if (!this.newPhone.number || !this.newPhone.type) {
      return;
    }
    this.phoneServise.addPhone(new PhoneDTO(this.newPhone, this.userId)).subscribe(result => {
      if (result.ok) {
        this.hasError = false;
        let phone: Phone = result.json();
        this.phones.push({ index: this.phones.length + 1, phone: phone });
      } else if (result.status == 404) {
        this.hasError = true;
      }
    });
  }

  deletePhone(phone: any) {
    this.phoneServise.deletePhone(phone.phone.id).subscribe(result => {
      if (result.ok) {
        this.phones.splice(phone.index - 1);
        this.deleteSuccess = true;
        this.hasError = false;
      } else {
        this.deleteSuccess = false;
        this.hasError = true;
      }
    });
  }
}
