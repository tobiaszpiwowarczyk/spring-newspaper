import { Http, Headers } from '@angular/http';
import { Injectable } from '@angular/core';
import "rxjs/add/operator/map";
import "rxjs/add/operator/toPromise";

@Injectable()
export class LoginService {

  public static readonly TOKEN_STRING = "Token";
  public static readonly USER_DATA_STRING = "userData";

  private headers: Headers;
  private _user: any;


  constructor(
    private http: Http
  ) {
    this.headers = new Headers({
      "Content-Type": "application/json"
    });
    if(this.isAuthenticated()) {
      this._user = this.retrieveUser();
    }
  }


  get user(): any {
    return this.user;
  }

  public isAuthenticated(): boolean {
    return localStorage.getItem(LoginService.TOKEN_STRING) !== null;
  }


  public login(credentials: any): Promise<any> {
    return this.http.post(
      `/api/users/login`,
      JSON.stringify(credentials),
      {headers: this.headers}
    )
    .map(res => res.json())
    .toPromise();
  }

  public retrieveUser() {
    return JSON.parse(localStorage.getItem(LoginService.USER_DATA_STRING));
  }

  public logout(): void {
    localStorage.removeItem(LoginService.TOKEN_STRING);
    localStorage.removeItem(LoginService.USER_DATA_STRING);
  }

}
