import { Response } from './../../models/Response';
import { LoaderComponent } from './../../components/loader/loader.component';
import { User } from './../../models/User';
import { Component, OnInit, ViewEncapsulation, ViewChild } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Title} from "@angular/platform-browser";

import {NewsService} from "../../services/news/news.service";
import {Home} from "./Home";


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  providers: [NewsService],
  encapsulation: ViewEncapsulation.None
})
export class HomeComponent implements OnInit {
  
  errors: Response = null;

  size: number = 10;
  homeHeaderText: string;

  home: Home;
  user: User;

  @ViewChild("loader") loader: LoaderComponent;



  constructor(
    private newsService: NewsService,
    private route: ActivatedRoute,
    private title: Title
  ) {}
  ngOnInit(): void {   
    this.route.params.subscribe(res => {
      this.route.queryParams.subscribe(q => {
        if(q.tag) {
          this.getArticlesByTag(res.page, q.tag);
          this.homeHeaderText = `<b>#${q.tag}</b> - Najnowsze wiadomości`;
          this.title.setTitle(`#${q.tag} - Najnowsze wiadomości - Info SERVICE`);
        }
        else if (q.category) {
          this.getArticlesByCategory(res.page, q.category);
          this.homeHeaderText = `Najnowsze wiadomości z kategorii <b>${q.category.toUpperCase()}</b>`;
          this.title.setTitle(`Najnowsze wiadomości z kategorii ${q.category.toUpperCase()} - INFO SERVICE`);
        }
        else {
          this.getArticles(parseInt(res.page, 10));
          this.homeHeaderText = "Najnowsze wiadomości";
          this.title.setTitle("Najnowsze wiadomości - Info SERVICE");
        }
      });
    });
  }



  private getArticles(page: number): void {
    this.newsService.getArticles(page, this.size)
      .then(response => this.home = response.content)
      .catch(err => {
        this.errors = new Response({
          status: err.status,
          content: "Serwis chwilowo niedostępny"
        });
      })
      .then(() => this.loader.disable());
  }


  private getArticlesByTag(page: number, tag: string) {
    this.newsService.getArticlesByTag(page, this.size, tag)
      .then(response => this.home = response.content)
      .catch(err => {
        this.errors = new Response({
          status: err.status,
          content: "Serwis chwilowo niedostępny"
        });
      })
      .then(() => this.loader.disable());
  }


  private getArticlesByCategory(page: number, category: string) {
    this.newsService.getArticlesByCategory(page, this.size, category)
      .then(res => this.home = res.content)
      .catch(err => {
        this.errors = new Response({
          status: err.status,
          content: "Serwis chwilowo niedostępny"
        });
      })
      .then(() => this.loader.disable());
  }
}
