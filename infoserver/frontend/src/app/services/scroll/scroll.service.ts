import { Injectable } from '@angular/core';

@Injectable()
export class ScrollService {


  public static setParallax(evt: any, offset?: number): string {

    const scrollTop = evt.target.scrollTop || window.pageYOffset;
    offset = offset || 0.4;

    return (scrollTop * offset) + "px";
  }


  
  public static setHeaderActive(evt: any, parent: string, child: string): boolean {
    const scrollTop = evt.target.scrollTop || window.pageYOffset;

    const parentHeight: any = document.querySelector(parent).clientHeight;
    const childHeight: number = document.querySelector(child).clientHeight;

    return scrollTop >= parentHeight - childHeight;

  }

}
