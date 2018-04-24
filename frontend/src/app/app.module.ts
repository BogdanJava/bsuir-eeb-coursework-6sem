import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { RouterModule } from "@angular/router";
import { LoginComponent } from './login/login.component';
import { FormsModule } from "@angular/forms";
import { AuthenticationService } from "./authentication.service";
import { HttpModule } from "@angular/http";
import { HomeComponent } from './home/home.component';
import { CookieService } from "./cookie.service";
import { AuthGuard } from "./auth.guard";
import { JwtService } from "./jwt.service";
import { JwtModule, JwtHelperService } from "@auth0/angular-jwt";
import { JwtHelper } from "angular2-jwt";
import { ButtonModule, MenuModule } from "primeng/primeng";
import { UserProfileComponent } from './user-profile/user-profile.component';
import { UserService } from './user.service';
import { RegistrationComponent } from './registration/registration.component';
import { MyDatePickerModule } from 'mydatepicker';
import { UserRoomComponent } from './user-room/user-room.component';
import { ContactInfoComponent } from './contact-info/contact-info.component';
import { PhoneService } from './phone.service';
import { WorkflowComponent } from './workflow/workflow.component';
import { WorkflowDefaultComponent } from './workflow/workflow-default/workflow-default.component';
import { WorkflowAccountsComponent } from './workflow/workflow-accounts/workflow-accounts.component';
import { WorkflowCardsComponent } from './workflow/workflow-cards/workflow-cards.component';
import { CardsService } from './workflow/cards.service';
import { CardRegistrationComponent } from './workflow/workflow-cards/card-registration/card-registration.component';
import { CardDetailsComponent } from './workflow/workflow-cards/card-details/card-details.component';
import { TransactionsComponent } from './workflow/workflow-cards/transactions/transactions.component';
import { TransactionDetailsComponent } from './workflow/workflow-cards/transactions/transaction-details/transaction-details.component';
import { DepositsComponent } from './workflow/workflow-accounts/deposits/deposits.component';
import { WorkflowTransactionsComponent } from './workflow/workflow-transactions/workflow-transactions.component';
import { CreditsComponent } from './workflow/workflow-accounts/credits/credits.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    UserProfileComponent,
    RegistrationComponent,
    UserRoomComponent,
    ContactInfoComponent,
    WorkflowComponent,
    WorkflowDefaultComponent,
    WorkflowAccountsComponent,
    WorkflowCardsComponent,
    CardRegistrationComponent,
    CardDetailsComponent,
    TransactionsComponent,
    TransactionDetailsComponent,
    DepositsComponent,
    WorkflowTransactionsComponent,
    CreditsComponent
  ],
  imports: [
    BrowserModule, FormsModule, HttpModule, MenuModule, JwtModule, ButtonModule, MyDatePickerModule,
    RouterModule.forRoot([
      { path: "login", component: LoginComponent },
      { path: "signup", component: RegistrationComponent },
      { path: "home", component: HomeComponent, canActivate: [AuthGuard] },
      {
        path: "profile", component: UserRoomComponent, canActivate: [AuthGuard], children: [
          { path: "details", component: UserProfileComponent, canActivateChild: [AuthGuard] },
          { path: "contact", component: ContactInfoComponent, canActivateChild: [AuthGuard] },
          { path: "**", redirectTo: "/profile/details" }
        ]
      },
      {
        path: "workflow", component: WorkflowComponent, canActivate: [AuthGuard], children: [
          { path: "", component: WorkflowDefaultComponent, canActivateChild: [AuthGuard] },
          { path: "cards", component: WorkflowCardsComponent, canActivateChild: [AuthGuard] },
          { path: "accounts/deposits", component: DepositsComponent, canActivateChild: [AuthGuard] },
          { path: "accounts/credits", component: CreditsComponent, canActivateChild: [AuthGuard] },
          { path: "accounts", component: WorkflowAccountsComponent, canActivateChild: [AuthGuard] },
          { path: "transactions", component: WorkflowTransactionsComponent, canActivateChild: [AuthGuard] },
          { path: "cards/registration", component: CardRegistrationComponent, canActivateChild: [AuthGuard] },
          { path: "cards/:cardId", component: CardDetailsComponent, canActivateChild: [AuthGuard] }
        ]
      },
      { path: "**", redirectTo: "/login" }
    ])
  ],
  providers:
    [
      AuthenticationService,
      CookieService,
      AuthGuard,
      JwtService,
      UserService,
      PhoneService,
      CardsService,
      JwtHelperService,
      JwtHelper
    ],
  bootstrap: [AppComponent]
})
export class AppModule {

}
