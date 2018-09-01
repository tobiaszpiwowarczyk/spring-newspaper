import { Component, OnInit, Input } from '@angular/core';

export interface IToastType {
  name: string;
  icon: string;
}

export class ToastType {
  public static DANGER: IToastType = {name: "danger", icon: "exclamation-triangle"};
  public static WARMING: IToastType = {name: "warming", icon: "exclamation-circle"};
  public static SUCCESS: IToastType = {name: "success", icon: "check-circle"};
}


@Component({
  selector: 'app-toast',
  templateUrl: './toast.component.html',
  styleUrls: ['./toast.component.scss']
})
export class ToastComponent implements OnInit {

  @Input() type: IToastType = ToastType.SUCCESS;
  @Input() message: string;
  @Input() time: number = 3000;

  shown: boolean = false;

  constructor() {}
  ngOnInit() {}

  public setToastType(type: IToastType): ToastComponent {
    this.type = type;
    return this;
  }

  public setMessage(value: string): ToastComponent {
    this.message = value;
    return this;
  }

  public show(): void {
    this.shown = true;
    setTimeout(() => {
      this.hide();
    }, this.time);
  }

  public hide(): void {
    this.shown = false;
  }

}
