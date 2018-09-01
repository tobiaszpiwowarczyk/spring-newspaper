import { Component, Input, OnChanges, Renderer } from '@angular/core';
import {Pagination} from "./Pagination";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.scss'],
  host: {
    "(window:resize)": "resize($event)"
  }
})
export class PaginationComponent implements OnChanges {
  
  private begin: number;
  private end: number;
  private step: number = 5;

  @Input() paginationObj: Pagination;

  pages: number[] = [];
  page: number = 0;
  params: any = {};

  constructor(
    private route: ActivatedRoute,
    private renderer: Renderer
  ) {}

  ngOnChanges(): void {
    if(this.paginationObj !== null) {
      this.page = parseInt(this.route.snapshot.params['page'], 10);
      this.renderNums();
      this.route.queryParams.subscribe(q => {
        this.params = q;
      });
      this.renderer.listenGlobal("window", "load", (e) => this.resize(e));
    }
  }

  public prev(): number {
    return this.page - 1;
  }
  public next(): number {
    return this.page + 1;
  }


  private renderNums() {
    this.pages.splice(0, this.pages.length);

    this.begin = this.page - Math.floor(this.step / 2);
    this.end = this.page + Math.floor(this.step / 2);

    if(this.begin < Math.floor(this.step / 2)) {
      this.begin = 1;
      this.end = this.step;
    }
    else if(this.end >= this.paginationObj.totalPages) {
      this.end = this.paginationObj.totalPages;
      this.begin = this.end - this.step - 1;
    }

    if (this.paginationObj.totalPages > 0) {
      for (let i = this.begin; i <= this.end; i++) {
        this.pages.push(i);
      }
    }
  }

  private resize(e: any) {
    if(e.currentTarget.innerWidth < 768) {
      this.step = 3;
    }
    else {
      this.step = 5;
    }
    this.renderNums();
  }
}
