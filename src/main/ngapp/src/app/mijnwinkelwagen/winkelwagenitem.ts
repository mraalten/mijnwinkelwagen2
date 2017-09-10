import {Produkt} from "./produkt";

export class WinkelwagenItem {

    constructor(
        public produkt: Produkt,
        public hoeveelheid: number
    ){}
}
