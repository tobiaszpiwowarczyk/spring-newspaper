import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-tab-header',
  templateUrl: './tab-header.component.html',
  styleUrls: ['./tab-header.component.scss']
})
export class TabHeaderComponent implements OnInit {

  @Input() name: string = "tab";

  constructor() {}
  ngOnInit() {}

}
