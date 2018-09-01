import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'ribbon',
  templateUrl: './ribbon.component.html',
  styleUrls: ['./ribbon.component.scss']
})
export class RibbonComponent implements OnInit {

  @Input() category: string = "MUSIC";
  @Input() hasCorner: boolean;

  constructor() {}
  ngOnInit() {}

}
