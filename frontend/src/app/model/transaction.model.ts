import { Card } from "./card.model";

export class CardTransaction {
    public id: number;
    public date: string;
    public name: string;
    public description: string;
    public diff: number;
    public card: Card;
    public transactionType: string;
    public additionalInfo: string;
}