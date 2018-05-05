import { Card } from "./card.model";

export class Account {
    public id: number;
    public accountType: string;
    public currency: string;
    public accountStatus: string;
    public sumRecountPeriod: string;
    public interestRate: number;
    public startSum: number;
    public openDate: any;
    public closeDate: any;
    public card: Card = new Card();
    public balance: number;
}
