import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import {Routes, RouterModule} from "@angular/router";
import {MijnWinkelwagenModule} from "./mijnwinkelwagen/mijnwinkelwagen.module";

const appRoutes: Routes = [
  { path: '',
    redirectTo: '/mijnwinkelwagen',
    pathMatch: 'full'
  }
];

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    MijnWinkelwagenModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
