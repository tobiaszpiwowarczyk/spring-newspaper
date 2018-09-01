import { LoaderComponent } from './../../components/loader/loader.component';
import { Component, OnInit, ViewChild, DoCheck } from '@angular/core';
import {Title} from "@angular/platform-browser";

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.scss']
})
export class AboutComponent implements OnInit, DoCheck {

  @ViewChild("loader") loader: LoaderComponent;

  constructor(
    private title: Title
  ) {}

  ngOnInit() {
    this.title.setTitle("Info SERVICE - O stronie");
  }
  ngDoCheck() {
    this.loader.disable();
  }

}
