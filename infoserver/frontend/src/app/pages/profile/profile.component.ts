import { LoaderComponent } from './../../components/loader/loader.component';
import { ScrollService } from './../../services/scroll/scroll.service';
import { HeaderComponent } from './../../components/header/header.component';
import { Title } from '@angular/platform-browser';
import { User } from './../../models/User';
import { UserService } from './../../services/user/user.service';
import { Component, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
  providers: [
    UserService
  ],
  host: {
    "(window:scroll)": "scroll($event)"
  }
})
export class ProfileComponent implements OnInit {

  page: number;
  bgPos: string = "0px";
  user: User;

  @ViewChild("header") header: HeaderComponent;
  @ViewChild("loader") loader: LoaderComponent;

  constructor(
    private userService: UserService,
    private title: Title
  ) {}
  ngOnInit() {

    this.header.active = false;

    this.userService.getProfile()
      .then(res => {
        this.user = res;
        this.title.setTitle(`${this.user.firstName} ${this.user.lastName} - Info SERVICE`);
      })
      .then(() => this.loader.disable());
  }

  private scroll(evt: any): void {
    this.header.active = ScrollService.setHeaderActive(evt, ".profile__header", ".header");
    this.bgPos = ScrollService.setParallax(evt);
  }

}
