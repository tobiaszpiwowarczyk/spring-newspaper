import {Directive, HostBinding, Component, NgModule, ViewEncapsulation} from "@angular/core";

@Directive({
  selector: 'button[appButton], a[appButton], input[appButton]'
})
export class ButtonDirective {

  @HostBinding("class.button")
  private buttonClass: boolean = true;

  constructor() {}

}


@Directive({
  selector: `button[appButtonPrimary], a[appButtomPrimary], input[appButtonPrimary]`
})
export class ButtonPrimaryDirective {
  
  @HostBinding("class.button--primary")
  private primaryClass: boolean = true;

}


@Directive({
  selector: "button[appButtonFluid], a[appButtonFluid], input[appButtonFluid]"
})
export class ButtonFluidDirective {

  @HostBinding("class.button--fluid")
  private fluidClass: boolean = true;
}


@Directive({
  selector: "button[appButtonIconized], a[appButtonIconized]"
})
export class ButtonIconizedDirective {

  @HostBinding("class.button--iconized")
  private iconizedClass: boolean = true;
}


@Component({
  selector: `button[appButton], a[appButton], input[appButton],
             button[appButtonPrimary], a[appButtomPrimary], input[appButtonPrimary],
             button[appButtonFluid], a[appButtonFluid], input[appButtonFluid],
             button[appButtonIconized], a[appButtonIconized]`,
  template: "<ng-content></ng-content>",
  styleUrls: [
    "./button.scss"
  ],
  encapsulation: ViewEncapsulation.None
})
export class ButtonComponent {}


@NgModule({
  declarations: [
    ButtonDirective,
    ButtonPrimaryDirective,
    ButtonFluidDirective,
    ButtonIconizedDirective,
    ButtonComponent
  ],
  exports: [
    ButtonDirective,
    ButtonPrimaryDirective,
    ButtonFluidDirective,
    ButtonIconizedDirective,
    ButtonComponent
  ]
})
export class ButtonModule {}
