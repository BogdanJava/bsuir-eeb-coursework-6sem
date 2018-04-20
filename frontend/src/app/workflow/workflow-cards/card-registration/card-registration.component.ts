import { Component, OnInit, ChangeDetectorRef, Renderer } from '@angular/core';
import { Card } from '../../../model/card.model';
import { NgForm } from '@angular/forms';
import { CardsService } from '../../cards.service';
import { Router } from '@angular/router';
import { JwtService } from '../../../jwt.service';

@Component({
  selector: 'app-card-registration',
  templateUrl: './card-registration.component.html',
  styleUrls: ['./card-registration.component.css']
})
export class CardRegistrationComponent implements OnInit {

  card: Card = new Card();
  rePassword: string;
  passwordStrength: string;
  passwordStrengthBarWidth: number;
  progressBarClass: string;
  progressBar = null;
  submitted: boolean = false;
  passwordNoMatch: boolean = false;


  constructor(private cdr: ChangeDetectorRef,
    private renderer: Renderer,
    private cardsService: CardsService,
    private router: Router,
    private jwtService: JwtService) {
  }

  ngOnInit() {
  }

  setupProgressBar(password: string) {
    if (password.length < 5) {
      this.passwordStrength = "too short";
      this.passwordStrengthBarWidth = 33;
      this.progressBarClass = "progress-bar-danger";
    } else
      if (password.length >= 5 && password.length < 8) {
        this.passwordStrength = "normal";
        this.passwordStrengthBarWidth = 66;
        this.progressBarClass = "progress-bar-warning";
      } else {
        this.passwordStrength = "strong";
        this.passwordStrengthBarWidth = 100;
        this.progressBarClass = "progress-bar-success";
      }
  }

  onPasswordInput() {
    if (this.card.password.length == 0) {
      this.progressBar = null;
      return;
    }
    this.cdr.detectChanges();
    if (this.progressBar == null) {
      this.progressBar = document.getElementById('progressBar');
    }
    this.setupProgressBar(this.card.password);
    console.log(this.progressBarClass);
    this.renderer.setElementStyle(this.progressBar, "width", `${this.passwordStrengthBarWidth}%`);
    this.progressBar.setAttribute("class", "progress-bar " + this.progressBarClass);
    this.cdr.detectChanges();
  }

  onSubmit(form: NgForm) {
    this.submitted = true;
    if (form.valid) {
      this.passwordNoMatch = this.card.password !== this.rePassword;
      if(this.passwordNoMatch) return;
      this.card.cardType = this.card.cardType.toUpperCase();
      this.cardsService.saveCard(this.card).subscribe(result => {
        if (result.ok) {
          document.getElementById('modalButton').click();
          setTimeout(() => {
            document.getElementById('closeModalButton').click();
            this.router.navigate(['/workflow/cards']);
          }, 2000);
        }
      });
    } else {
      console.log('invalid form');
    }
  }

}
