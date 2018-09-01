import { Component, OnInit, Input } from '@angular/core';

import { TweenLite, Power4 } from "gsap";

@Component({
  selector: 'back-to-top',
  templateUrl: './back-to-top.component.html',
  styleUrls: ['./back-to-top.component.scss'],
  host: {
    "(window:scroll)": "scroll($event)"
  }
})
export class BackToTopComponent implements OnInit {

  @Input() scrollTopOffset: number = 0;

  isHidden: boolean = true;

  constructor() {}
  ngOnInit() {}


  public scrollTop() {
    const doc = (document.body.scrollTop !== 0) ? document.body : document.documentElement;
    TweenLite.to(doc, 1, {
      scrollTop: 0,
      ease: Power4.easeOut
    });
  }


  private scroll(evt) {
    let scrollTop: number = evt.target.scrollTop || window.pageYOffset;
    this.isHidden = scrollTop <= this.scrollTopOffset;
  }
}
