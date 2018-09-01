import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-dropdown-option',
  templateUrl: './dropdown-option.component.html',
  styleUrls: ['./dropdown-option.component.scss']
})
export class DropdownOptionComponent implements OnInit {

  @Input() icon: string;
  @Input() content: string = "";

  @Output() onClick: EventEmitter<boolean> = new EventEmitter<boolean>();

  constructor() {}
  ngOnInit() {}

  public click() {
    this.onClick.emit(true);
  }
}
