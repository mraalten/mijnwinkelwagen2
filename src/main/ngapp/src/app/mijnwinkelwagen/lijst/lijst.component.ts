import {Component} from "@angular/core";
import {WinkelwagenItem} from "../winkelwagenitem";
import {Produkt} from "../produkt";

@Component({
    selector: 'lijst',
    templateUrl: 'lijst.component.html',
    styleUrls: [
        'lijst.css'
    ]
})

export class BoodschappenLijstComponent {
    private items : WinkelwagenItem[];

    constructor() {
        var produkt = new Produkt(1, 'Sla', 'sla.jpg', 'St.');
        var item1 = new WinkelwagenItem(produkt, 1);
        this.items = [item1];
    }


}
