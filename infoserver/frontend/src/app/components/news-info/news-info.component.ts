import {Component, Input, ViewEncapsulation} from '@angular/core';

@Component({
  selector: 'news-info',
  templateUrl: './news-info.component.html',
  styleUrls: ['./news-info.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class NewsInfoComponent {

  @Input("media-type") mediaType: string = "icon";
  @Input() theme: string = "grey";
  @Input() src: string;

  constructor() {}

}
