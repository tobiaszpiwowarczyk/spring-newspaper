import { ActivatedRoute, Router } from '@angular/router';
import { Component, Input, ElementRef, DoCheck } from '@angular/core';

@Component({
  selector: 'app-tab-header-item',
  templateUrl: './tab-header-item.component.html',
  styleUrls: ['./tab-header-item.component.scss']
})
export class TabHeaderItemComponent implements DoCheck {

  @Input() id: string;
  active: boolean = false;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private el: ElementRef
  ) {}
  ngDoCheck() {
    this.activatedRoute.queryParams.subscribe(p => {
      this.active = (this.id === p[this.el.nativeElement.parentNode.id]);
    });
  }


  public navigate() {
    const params = {};
    params[this.el.nativeElement.parentNode.id] = this.id;
    
    this.router.navigate([this.router.url.split("?")[0]], {queryParams: params});
  }
}
