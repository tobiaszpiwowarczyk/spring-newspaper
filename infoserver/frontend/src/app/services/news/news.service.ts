import { Response } from './../../models/Response';
import { ArticleComment } from './../../pages/article/ArticleComment';
import { LoginService } from './../login/login.service';
import { Injectable } from '@angular/core';

import { News } from "../../models/News";
import {Http, Headers} from "@angular/http";
import "rxjs/add/operator/map";
import "rxjs/add/operator/toPromise";
import {Article} from "../../models/Article";
import {User} from "../../models/User";
import {Rating} from "../../models/Rating";
import {Home} from "../../pages/home/Home";
import {Pagination} from "../../components/pagination/Pagination";

@Injectable()
export class NewsService {

  private headers: Headers;

  constructor(private http: Http) {
    this.headers = new Headers();
    this.headers.append("Content-Type", "application/json");
  }





  public getArticles(page: number, size: number): Promise<Response> {
    return this.http.get(
      `/api/posts/page/${page - 1}?size=${size}`,
      {headers: this.headers}
    )
    .map(res => res.json())
    .toPromise()
    .then(res => {
      if(res.status == 200) {
        let x = res.content;

        return new Response({
          status: res.status,
          content: new Home({
            pagination: new Pagination({
              totalPages: x.totalPages,
              currentPage: x.currentPage,
              isFirst: x.first,
              isLast: x.last
            }),
            news: x.content.map(n => new News({
              id: n.id,
              title: n.title,
              description: n.description,
              category: n.category,
              thumbnail: n.image.thumbnail,
              date: new Date(n.date),
              user: new User(n.user),
              tags: n.tags,
              rating: new Rating(n.rating),
              commentsCount: n.commentsCount
            }))
          })
        });
      }
    })
    .catch(err => Promise.reject(err));
  }



  public getArticlesByTag(page: number, size: number, tag: string): Promise<Response> {
    return this.http.get(
      `/api/posts/page/${page - 1}/tag/${tag}?size=${size}`,
      {headers: this.headers}
    )
    .map(res => res.json())
    .toPromise()
    .then(res => {
      if(res.status == 200) {
        let x = res.content;

        return new Response({
          status: res.status,
          content: new Home({
            pagination: new Pagination({
              totalPages: x.totalPages,
              currentPage: x.currentPage,
              isFirst: x.first,
              isLast: x.last
            }),
            news: x.content.map(n => new News({
              id: n.id,
              title: n.title,
              description: n.description,
              category: n.category,
              thumbnail: n.image.thumbnail,
              date: new Date(n.date),
              user: new User(n.user),
              tags: n.tags,
              rating: new Rating(n.rating),
              commentsCount: n.commentsCount
            }))
          })
        });
      }
    })
    .catch(err => Promise.reject(err));
  }


  public getArticlesByCategory(page: number, size: number, category: string): Promise<Response> {
    return this.http.get(
      `/api/posts/page/${page - 1}/category/${category}?size=${size}`, 
      {headers: this.headers}
    )
    .map(res => res.json())
    .toPromise()
    .then(res => {
      if(res.status == 200) {
        let x = res.content;

        return new Response({
          status: res.status,
          content: new Home({
            pagination: new Pagination({
              totalPages: x.totalPages,
              currentPage: x.currentPage,
              isFirst: x.first,
              isLast: x.last
            }),
            news: x.content.map(n => new News({
              id: n.id,
              title: n.title,
              description: n.description,
              category: n.category,
              thumbnail: n.image.thumbnail,
              date: new Date(n.date),
              user: new User(n.user),
              tags: n.tags,
              rating: new Rating(n.rating),
              commentsCount: n.commentsCount
            }))
          })
        });
      }
    })
    .catch(err => Promise.reject(err));
  }


  public getArticleById(id: string): Promise<Response> {
    return this.http.get(`/api/posts/id/${id}`, {headers: this.headers})
      .map(res => res.json())
      .toPromise()
      .then(res => {
        if(res.status == 200) {
          let x = res.content;
          return Promise.resolve(
            new Response({
              status: res.status,
              content: new Article({
                id: x.id,
                title: x.title,
                description: x.description,
                content: x.content,
                image: x.image.landscape,
                date: x.date,
                tags: x.tags,
                user: new User(x.user),
                category: x.category,
                rating: new Rating(x.rating),
                comments: x.comments.map(c => new ArticleComment({
                  id: c.id,
                  content: c.content,
                  date: c.date,
                  user: new User(c.user)
                })),
                commentsCount: x.commentsCount
              })
            })
          );
        }
        else {
          return Promise.resolve(new Response(res));
        }
      })
      .catch(err => Promise.reject(err));
  }



  public getTagsSuggests(tag: string): Promise<string[]> {
    return this.http.get(`api/posts/tags-suggests/${tag}`, {headers: this.headers})
      .map(res => res.json())
      .toPromise()
      .then(res => res.content)
      .catch(err => Promise.reject(err));
  } 






  public like(id: string, thumbType: string): Promise<Rating> {
    if(!this.headers.has("Authorization")) {
      this.headers.append("Authorization", localStorage.getItem(LoginService.TOKEN_STRING));
    }
    return this.http.post(
      `/api/posts/id/${id}/like?thumbType=${thumbType}`, null,
      {
        headers: this.headers
      }
    )
    .map(res => res.json())
    .toPromise()
    .then(res => {
      if(res.status == 200) {
        return new Rating(res.content);
      }
    })
    .catch(err => Promise.reject(err));
  }




  public addComment(postId: string, content: any): Promise<ArticleComment> {
    if(!this.headers.has("Authorization")) {
      this.headers.append("Authorization", localStorage.getItem(LoginService.TOKEN_STRING));
    }

    return this.http.post(`/api/comments/${postId}/add-comment`, JSON.stringify(content), {headers: this.headers})
      .map(res => res.json())
      .toPromise()
      .then(res => {
        if(res.status = 200) {
          let x = res.content;
          return new ArticleComment({
            id: x.id,
            content: x.content,
            date: x.date,
            user: new User(x.user)
          });
        }
      })
      .catch(err => Promise.reject(err));
  }



  public editComment(c: any): Promise<Response> {
    if(!this.headers.has("Authorization")) {
      this.headers.append("Authorization", localStorage.getItem(LoginService.TOKEN_STRING));
    }

    return this.http.post
    (
      `api/comments/edit-comment`, 
      JSON.stringify(c), 
      {headers: this.headers}
    )
    .map(res => res.json())
    .toPromise()
    .then(res => {
      if(res.status = 200) {
        let x = res.content;
        return new Response({
          status: res.status,
          content: new ArticleComment({
            id: x.id,
            content: x.content,
            date: x.date,
            user: new User(x.user)
          })
        });
      }
    })
    .catch(err => Promise.reject(err));

  }



  public deleteComment(c: any): Promise<Response> {
    if(!this.headers.has("Authorization")) {
      this.headers.append("Authorization", localStorage.getItem(LoginService.TOKEN_STRING));
    }

    return this.http.delete
      (
        `/api/comments/remove-comment`,  
        {
          headers: this.headers,
          body: JSON.stringify(c)
        }
      )
      .map(res => res.json())
      .toPromise()
      .catch(err => Promise.reject(err));

  }
}
