import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {MijnWinkelwagenComponent} from "../mijnwinkelwagen.component";
import {Item} from "../item";

@Component({
    selector: 'lijst',
    templateUrl: 'lijst.component.html',
    styleUrls: [
        'lijst.css'
    ]
})

export class BoodschappenLijstComponent implements OnInit {
    private items: Item[];

    constructor(
        private route: ActivatedRoute,
        private mijnwinkelwagenComponent: MijnWinkelwagenComponent
    ){}

    ngOnInit(): void {
        this.route.params.subscribe(params => this.loadBoodschappenlijst());
    }

    loadBoodschappenlijst() {
        let boodschappenLijstId = 1;
        this.mijnwinkelwagenComponent.loadBoodschappenlijst(boodschappenLijstId).subscribe(
            items => this.items = items,
            err => {console.log(err)}
        );
    }


}
