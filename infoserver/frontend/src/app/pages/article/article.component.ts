import { ToastComponent, ToastType } from './../../components/toast/toast.component';
import { Response } from './../../models/Response';
import { ArticleComment } from './ArticleComment';
import { LoaderComponent } from './../../components/loader/loader.component';
import { LoginService } from './../../services/login/login.service';
import { Validators } from '@angular/forms';
import { FormGroup, FormBuilder } from '@angular/forms';
import { RatingComponent } from './../../components/rating/rating.component';
import { ScrollService } from './../../services/scroll/scroll.service';
import { HeaderComponent } from './../../components/header/header.component';
import { Component, OnInit, ViewEncapsulation, ViewChild } from '@angular/core';
import {Article} from "../../models/Article";
import {NewsService} from "../../services/news/news.service";
import {ActivatedRoute} from "@angular/router";
import {Title} from "@angular/platform-browser";

@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.scss'],
  providers: [NewsService, LoginService],
  encapsulation: ViewEncapsulation.None,
  host: {
    '(window:scroll)': 'scroll($event)',
  }
})
export class ArticleComponent implements OnInit {
  
  errors: Response = null;
  authenticated: boolean;
  bgPos: string = "0px";
  article: Article;
  user: any;

  commentForm: FormGroup;
  commentFormLoading: boolean = false;
  commentName: string = "";

  @ViewChild("header") header: HeaderComponent;
  @ViewChild("rating") rating: RatingComponent;
  @ViewChild("loader") loader: LoaderComponent;
  @ViewChild("formLoader") formLoader: LoaderComponent;
  @ViewChild("toast") toast: ToastComponent;

  constructor(
    private newsService: NewsService,
    private loginService: LoginService,
    private route: ActivatedRoute,
    private title: Title,
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.header.active = false;
    this.authenticated = this.loginService.isAuthenticated();

    if(this.authenticated) {
      this.commentForm = this.fb.group({
        content: ['', Validators.required]
      });
      this.user = this.loginService.retrieveUser();
    }
    this.route.params.subscribe(r => {
      this.getArticle(r.id);
    });
  }



  
  public like(thumbType: string) {
    this.rating.ratingLoader.enable();
    this.newsService.like(this.article.id, thumbType)
      .then(res => this.article.rating = res)
      .then(() => this.rating.ratingLoader.disable());
  }



  public addComment() {
    if(this.commentForm.valid) {
      this.formLoader.enable();
      this.newsService.addComment(this.article.id, this.commentForm.value)
        .then(res => {
          this.article.comments.unshift(res);
          this.article.commentsCount++;
          this.commentForm.reset();
          this.checkCommentLetter(this.article.commentsCount);
        })
        .then(() => {
          this.formLoader.disable();
        });
    }
  }


  public editComment(c: ArticleComment) {
    c.processing = true;
    this.newsService.editComment({id: c.id, content: c.content, user: {username: c.user.username}})
      .then(res => {
        if(res.status == 200) {
          c.processing = false;
        }
      });

  }


  public deleteComment(c: ArticleComment) {
    c.processing = true;
    this.newsService.deleteComment({id: c.id, user: {username: c.user.username}})
      .then(res => {
        this.article.comments.splice(this.article.comments.indexOf(c), 1);
        this.article.commentsCount--;
        this.toast
          .setToastType(ToastType.SUCCESS)
          .setMessage(res.content)
          .show();
        this.checkCommentLetter(this.article.commentsCount);
      });
  }



  


  private getArticle(id): void {
    this.newsService.getArticleById(id)
      .then(res => {
        if(res.status == 200) {
          this.article = res.content;
          this.title.setTitle(`${this.article.title} - Info SERVICE`);
          this.checkCommentLetter(this.article.commentsCount);
        }
        else {
          this.errors = res;
          this.title.setTitle(`404 - Info SERVICE`);
          this.header.active = true;
        }
      })
      .then(() => {
        this.loader.disable();
      });
  }


  private scroll(evt): void {
    this.header.active = ScrollService.setHeaderActive(evt, ".article__header", ".header");
    this.bgPos = ScrollService.setParallax(evt);
  }


  private checkCommentLetter(num: number) {
    if(num == 1) {
      this.commentName = "Komentarz";
    }
    else if(
      (num > 20 && num % 10 >= 2 && num % 10 <= 4) ||
      (num % 10 >= 2 && num % 10 <= 4)
    ) {
      this.commentName = "Komentarze";
    }
    else {
      this.commentName = "Komentarzy";
    }
  }
}
