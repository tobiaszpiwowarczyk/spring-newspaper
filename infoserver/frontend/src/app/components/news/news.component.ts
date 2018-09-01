import {Component, Input, OnInit} from '@angular/core';
import {News} from "../../models/News";

@Component({
  selector: 'news',
  templateUrl: './news.component.html',
  styleUrls: ['./news.component.scss']
})
export class NewsComponent implements OnInit {

  @Input() newsObj: News = new News();
  @Input() showAuthor: boolean = true;


  constructor() {}
  ngOnInit(): void {}
}
