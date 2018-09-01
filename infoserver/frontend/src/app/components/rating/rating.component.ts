import { LoaderComponent } from './../loader/loader.component';
import { LoginService } from './../../services/login/login.service';
import { Component, Input, EventEmitter, Output, OnInit, ViewChild } from '@angular/core';
import {Rating} from "../../models/Rating";

@Component({
  selector: 'rating',
  templateUrl: './rating.component.html',
  styleUrls: ['./rating.component.scss'],
  providers: [
    LoginService
  ]
})
export class RatingComponent implements OnInit {
  
  @Input() rating: Rating;

  @Output() onUp: EventEmitter<string> = new EventEmitter<string>();
  @Output() onDown: EventEmitter<string> = new EventEmitter<string>();

  @ViewChild("ratingLoader") ratingLoader: LoaderComponent;

  disabled: boolean = false;

  constructor(
    private loginService: LoginService
  ) {}
  ngOnInit(): void {
    this.disabled = !this.loginService.isAuthenticated();
    this.ratingLoader.disable();
  }


  public up() {
    if(!this.disabled) {
      this.onUp.emit("up");
    }
  }
  public down() {
    if(!this.disabled) {
      this.onDown.emit("down");
    }
  }

}
