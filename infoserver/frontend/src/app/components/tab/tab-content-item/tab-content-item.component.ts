import { ActivatedRoute } from '@angular/router';
import { Component, Input, ElementRef, DoCheck } from '@angular/core';

@Component({
  selector: 'app-tab-content-item',
  templateUrl: './tab-content-item.component.html',
  styleUrls: ['./tab-content-item.component.scss']
})
export class TabContentItemComponent implements DoCheck {

  @Input() id: string;

  active: boolean = false;

  constructor(
    private activatedRoute: ActivatedRoute,
    private el: ElementRef
  ) {}
  ngDoCheck() {
    this.activatedRoute.queryParams.subscribe(p => {
      this.active = (this.id === p[this.el.nativeElement.parentNode.id]);
    });
  }

}
