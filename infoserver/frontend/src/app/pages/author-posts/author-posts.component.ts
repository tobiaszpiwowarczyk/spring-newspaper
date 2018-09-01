import { LoaderComponent } from './../../components/loader/loader.component';
import { ScrollService } from './../../services/scroll/scroll.service';
import { HeaderComponent } from './../../components/header/header.component';
import { Component, OnInit, ViewChild } from '@angular/core';
import {AuthorService} from "../../services/author/author.service";
import {ActivatedRoute} from "@angular/router";
import { Title } from "@angular/platform-browser";

import {User} from "../../models/User";

@Component({
  selector: 'app-author-posts',
  templateUrl: './author-posts.component.html',
  styleUrls: ['./author-posts.component.scss'],
  providers: [AuthorService],
  host: {
    '(window:scroll)': 'scroll($event)',
  }
})
export class AuthorPostsComponent implements OnInit {

  bgPos: string = "0px";
  user: User;

  @ViewChild("header") header: HeaderComponent;
  @ViewChild("loader") loader: LoaderComponent;

  constructor(
    private authorService: AuthorService,
    private route: ActivatedRoute,
    private title: Title
  ) {}
  ngOnInit() {
    this.header.active = false;
    this.route.params.subscribe(r => {
      this.authorService.getAuthorById(r.id)
        .then(res => {
          this.user = res;
          this.title.setTitle(`${this.user.firstName} ${this.user.lastName} - Info SERVICE`);
        })
        .then(() => {
          this.loader.disable();
        });
    });
  }



  private scroll(evt): void {
    this.header.active = ScrollService.setHeaderActive(evt, ".profile__header", ".header");
    this.bgPos = ScrollService.setParallax(evt);
  }
}
