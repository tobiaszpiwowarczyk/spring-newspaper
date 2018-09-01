import { LoginService } from './../login/login.service';
import { User } from './../../models/User';
import {Injectable} from "@angular/core";
import {Http, Headers} from "@angular/http";
import "rxjs/add/operator/map";
import "rxjs/operator/toPromise";
import {News} from "../../models/News";
import {Rating} from "../../models/Rating";

@Injectable()
export class AuthorService {

  private headers: Headers;

  constructor(private http: Http) {
    this.headers = new Headers({
      "Content-Type": "application/json",
      "Authorization": localStorage.getItem(LoginService.TOKEN_STRING)
    });
  }



  public getAuthorById(id: string): Promise<any> {
    return this.http.get(`/api/users/id/${id}`, { headers: this.headers })
      .map(res => res.json())
      .toPromise()
      .then(res => {
        if(res.status === 200) {
          let x = res.content;
          return new User({
            id: x.id,
            firstName: x.firstName,
            lastName: x.lastName,
            avatar: x.avatar,
            bgImage: x.bgImage,
            email: x.email,
            role: x.role,
            sex: x.userSex,
            posts: x.posts.map(n => new News({
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
        else {
          return res.content;
        }
      });
  }

}
