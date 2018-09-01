import {Component, OnInit, Input} from '@angular/core';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from "@angular/forms";

@Component({
  selector: 'text-input',
  templateUrl: './text-input.component.html',
  styleUrls: ['./text-input.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: TextInputComponent,
      multi: true
    }
  ]
})
export class TextInputComponent implements OnInit, ControlValueAccessor {

  @Input() icon: string = "";
  @Input() type: string = "text";
  @Input() label: string = "";

  @Input() required: boolean = false;

  value: string = "";
  focused: boolean = false;

  propagateChange = (_: any) => {};


  constructor() {}
  ngOnInit() {}



  public focus() {
    this.focused = true;
  }
  public blur() {
    this.focused = false;
  }

  public changeValue(value) {
    this.value = value;
    this.propagateChange(value);
  }

  writeValue(value: string): void {
    this.value = value;
  }

  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }

  registerOnTouched(fn: any): void {}

}
