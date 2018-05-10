import { Account } from "./account.model";
import { Card } from "./card.model";

export class AccountTransaction {
    public id: number;
    public date: Date;
    public diff: number;
    public account: Account;
    public card: Card;
}