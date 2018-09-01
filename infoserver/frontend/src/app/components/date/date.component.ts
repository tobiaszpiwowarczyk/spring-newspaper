import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-date',
  templateUrl: './date.component.html',
  styleUrls: ['./date.component.scss']
})
export class DateComponent implements OnInit {

  @Input() category: string;
  @Input() date: any;

  constructor() {}
  ngOnInit() {}

}
