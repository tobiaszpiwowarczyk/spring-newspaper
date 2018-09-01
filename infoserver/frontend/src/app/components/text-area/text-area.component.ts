import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-text-area',
  templateUrl: './text-area.component.html',
  styleUrls: ['./text-area.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: TextAreaComponent,
      multi: true
    }
  ]
})
export class TextAreaComponent implements OnInit, ControlValueAccessor {
  
  @Input() enabled: boolean = true;

  value: string = "";

  constructor() {}
  ngOnInit() {}

  propagateChange = (_: any) => {};


  public changeValue(evt: any) {
    this.value = evt.target.innerText;
    this.propagateChange(evt.target.innerText);
  }


  writeValue(value: string): void {
    this.value = value;
  }
  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }
  registerOnTouched(fn: any): void {}
  setDisabledState(isDisabled: boolean): void {
    this.enabled = !isDisabled;
  }

}
