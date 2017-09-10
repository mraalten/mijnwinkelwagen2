import {Component} from "@angular/core";
import {ProduktGroep} from "../produktgroep";
import {Router} from "@angular/router";
import {MijnWinkelwagenComponent} from "../mijnwinkelwagen.component";

@Component({
    selector: 'produktgroepen',
    templateUrl: 'produktgroepen.component.html',
    styleUrls: [
        'produktgroepen.css'
    ]
})

export class ProduktGroepenComponent {
    private produktGroepen: ProduktGroep[];

    constructor(
        private router : Router,
        private mijnwinkelwagenComponent: MijnWinkelwagenComponent
    ) {
    }

    ngOnInit(): void {
        this.mijnwinkelwagenComponent.getProduktGroepen().subscribe(
            produktGroepen => this.produktGroepen = produktGroepen,
            err => {console.log(err)}
        );
    }

}
