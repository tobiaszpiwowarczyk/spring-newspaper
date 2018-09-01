import { Component, OnInit, Input, ViewEncapsulation } from '@angular/core';

@Component({
  selector: 'app-dropdown',
  templateUrl: './dropdown.component.html',
  styleUrls: ['./dropdown.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class DropdownComponent implements OnInit {

  @Input() label: string;

  opened: boolean = false;

  constructor() {}
  ngOnInit() {}


  public toggle() {
    this.opened = !this.opened;
  }

  public close() {
    this.opened = false;
  }
}
