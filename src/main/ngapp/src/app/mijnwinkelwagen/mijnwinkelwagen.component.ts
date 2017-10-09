import {Component, Injectable} from '@angular/core';
import {Http, Response, RequestOptions, Headers} from "@angular/http";
import {ProduktGroep} from "./produktgroep";
import {Observable} from "rxjs";
import 'rxjs/Rx';
import {Produkt} from "./produkt";

@Component({
  selector: 'mijnwinkelwagen-page',
  templateUrl: 'mijnwinkelwagen.component.html'
})

@Injectable()
export class MijnWinkelwagenComponent {
  private produktGroepenUrl = 'http://localhost:8080/mijnwinkelwagen/ophalenproduktgroepen';
  private produktenUrl = 'http://localhost:8080/mijnwinkelwagen/ophalenprodukten';
  private toevoegenProduktWinkelwagenUrl = 'http://localhost:8080/mijnwinkelwagen/toevoegenproduktWinkelwagen';
  private ophalenBoodschappenLijstUrl = 'http://localhost:8080/mijnwinkelwagen/ophalenboodschappenlijst';

  constructor(
      private http: Http
  ) {}

  public getProduktGroepen() {
    return this.http
        .get(this.produktGroepenUrl)
        .map((response) => {
            return response.json().produktGroep;
        });
  }

    public getProdukten(produktGroepId: number) {
        let searchParams = new URLSearchParams();
        searchParams.append('produktGroepId', produktGroepId.toString());
        let options = new RequestOptions({params: searchParams.toString() });

        return this.http
            .get(this.produktenUrl, options)
            .map(response => {
                return response.json().produkt;
            });
    }

    public toevoegenProduktAanWinkelwagen(produkt: Produkt) {
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let produktToAdd = { produkt : produkt};
        let options = new RequestOptions({headers: headers });
        return this.http
            .post(this.toevoegenProduktWinkelwagenUrl, produktToAdd, options).subscribe(
                res => {
                    console.log(res);
                },
                err => {
                    console.log("Error occurred");
                }
            );
    }

    public loadBoodschappenlijst(boodschappenLijstId: number) {
        let searchParams = new URLSearchParams();
        searchParams.append('boodschappenLijstId', boodschappenLijstId.toString());
        return this.doGet(searchParams, this.ophalenBoodschappenLijstUrl);
    }

    public doGet(searchParams : URLSearchParams, url : string) {
        let options = new RequestOptions({params: searchParams.toString() });

        return this.http
            .get(url, options)
            .map(response => {
                let resp = response.json();
                return response.json().item;
            });
    }
}
