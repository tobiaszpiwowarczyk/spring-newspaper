import { Component, Input, OnInit, ElementRef } from '@angular/core';

@Component({
  selector: 'app-loader',
  templateUrl: './loader.component.html',
  styleUrls: ['./loader.component.scss']
})
export class LoaderComponent implements OnInit {

  @Input() fullScreen: boolean = false;
  @Input() enabled: boolean = true;
  width: string = "100px";
  
  constructor(
    private el: ElementRef
  ) {}
  ngOnInit() {
    if(this.enabled) {
      this.enable();
    }
    if(!this.fullScreen) {
      this.width = (this.el.nativeElement.parentElement.offsetHeight * 0.6) + "px";
    }
  }

  public enable() {
    this.enabled = true;
    if(this.fullScreen) {
      document.body.classList.add("is-loading");
    }
  }

  public disable() {
    this.enabled = false;
    if(this.fullScreen) {
      document.body.classList.remove("is-loading");
    }
  }
}
