import { LoaderComponent } from './../../components/loader/loader.component';
import { ToastComponent, ToastType } from './../../components/toast/toast.component';
import { LoginService } from './../../services/login/login.service';
import { Title } from '@angular/platform-browser';
import { Component, OnInit, ViewChild } from '@angular/core';
import {FormGroup, FormBuilder, Validators} from "@angular/forms";
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  providers: [
    LoginService
  ]
})
export class LoginComponent implements OnInit {

  submitted: boolean = false;
  loginForm: FormGroup;

  @ViewChild("toast") toast: ToastComponent;
  @ViewChild("mainLoader") mainLoader: LoaderComponent;
  @ViewChild("formLoader") formLoader: LoaderComponent;

  constructor(
    private fb: FormBuilder,
    private title: Title,
    private loginService: LoginService,
    private router: Router
  ) {}
  ngOnInit() {
    if(this.loginService.isAuthenticated()) {
      this.router.navigate(["/home", 1]);
    }
    this.title.setTitle("Info SERVICE - Zaloguj się");

    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });

    this.mainLoader.disable();
  }


  login() {
    if(this.loginForm.valid) {
      this.formLoader.enable();
      this.loginService.login(this.loginForm.value)
        .then(res => {
          if(res.status == 200) {
            localStorage.setItem(LoginService.TOKEN_STRING, res.content.token);
            localStorage.setItem(LoginService.USER_DATA_STRING, JSON.stringify(res.content.userData));
            this.router.navigate(["/home", 1]);
          }
          else {
            this.toast.setToastType(ToastType.DANGER)
              .setMessage(res.content)
              .show();
            this.formLoader.disable();
          }
        });
    }
  }
}
