import { Rating } from './../../models/Rating';
import { News } from './../../models/News';
import { LoginService } from './../login/login.service';
import { User } from './../../models/User';
import { Http, Headers } from '@angular/http';
import { Injectable } from '@angular/core';
import "rxjs/add/operator/map";
import "rxjs/add/operator/toPromise";

@Injectable()
export class UserService {

  headers: Headers;

  constructor(
    private http: Http
  ) {
    this.headers = new Headers({
      "Content-Type": "application/json",
      "Authorization": localStorage.getItem(LoginService.TOKEN_STRING)
    });
  }


  public getProfile(): Promise<User> {
    return this.http.get("/api/users", {headers: this.headers})
        .map(res => res.json())
        .toPromise()
        .then(res => {
          if(res.status == 200) {
            let x = res.content;
            return new User({
              id: x.id,
              username: x.username,
              password: x.password,
              firstName: x.firstName,
              lastName: x.lastName,
              email: x.email,
              avatar: x.avatar,
              bgImage: x.bgImage,
              posts: (x.posts == null) ? [] : x.posts.map(n => new News({
                id: n.id,
                title: n.title,
                description: n.description,
                category: n.category,
                date: new Date(n.date),
                tags: n.tags,
                thumbnail: n.image.thumbnail,
                rating: new Rating({
                  value: n.rating.value
                }),
                commentsCount: n.commentsCount
              }))
            });
          }
        });
  }

}
