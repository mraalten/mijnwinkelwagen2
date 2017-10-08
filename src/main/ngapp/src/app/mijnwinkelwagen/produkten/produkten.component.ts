import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {Produkt} from "../produkt";
import {MijnWinkelwagenComponent} from "../mijnwinkelwagen.component";

@Component({
    selector: 'produkten',
    templateUrl: 'produkten.component.html',
    styleUrls: [
        'produkten.css'
    ]
})

export class ProduktenComponent implements OnInit{
    private produktId : number;
    private produktGroepId : number;
    private produkten : Produkt[];

    constructor(
        private route: ActivatedRoute,
        private mijnwinkelwagenComponent: MijnWinkelwagenComponent
    ) {}

    ngOnInit(): void {
        this.route.params.subscribe(params => this.loadProdukten(params['produktGroepId']));
    }

    toevoegenProduktWinkelwagen(produkt: Produkt) {
        this.produktId = produkt.id;
        this.mijnwinkelwagenComponent.toevoegenProduktAanWinkelwagen(produkt);
        setTimeout(() => this.produktId = undefined, 100);
    }

    loadProdukten(produktGroepId: number) {
        this.produktGroepId = produktGroepId;
        this.mijnwinkelwagenComponent.getProdukten(produktGroepId).subscribe(
            produkten => this.produkten = produkten,
            err => {console.log(err)}
        );
    }
}

