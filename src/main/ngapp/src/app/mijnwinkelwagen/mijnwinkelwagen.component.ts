import {Component, Injectable} from '@angular/core';
import {Http, Response, RequestOptions, Headers} from "@angular/http";
import {ProduktGroep} from "./produktgroep";
import {Observable} from "rxjs";
import 'rxjs/Rx';

@Component({
  selector: 'mijnwinkelwagen-page',
  templateUrl: 'mijnwinkelwagen.component.html'
})

@Injectable()
export class MijnWinkelwagenComponent {
  private produktGroepenUrl = 'http://localhost:8080/mijnwinkelwagen/ophalenproduktgroepen';
  private produktenUrl = 'http://localhost:8080/mijnwinkelwagen/ophalenprodukten';
  private toevoegenProduktWinkelwagenUrl = 'http://localhost:8080/mijnwinkelwagen/toevoegenproduktWinkelwagen';

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

    public toevoegenProduktAanWinkelwagen(produktId: number) {
      alert('in service: ' + produktId);
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let searchParams = new URLSearchParams();
        searchParams.append('produktId', produktId.toString());
        let options = new RequestOptions({params: searchParams.toString() });
        return this.http
            .post(this.toevoegenProduktWinkelwagenUrl, options, headers).subscribe;
    }

}
