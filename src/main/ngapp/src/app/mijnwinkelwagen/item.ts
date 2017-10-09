import {Produkt} from "./produkt";

export class Item {

    constructor(
        public id: number,
        public produkt: Produkt,
        public hoeveelheid: number
    ){}
}
