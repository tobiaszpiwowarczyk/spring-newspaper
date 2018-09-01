import { Router, ActivatedRoute } from '@angular/router';
import { NewsService } from './../../services/news/news.service';
import { LoaderComponent } from './../loader/loader.component';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { LoginService } from './../../services/login/login.service';
import { Component, OnInit, Input, ViewChild, ElementRef, Renderer, DoCheck } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
  providers: [
    LoginService,
    NewsService
  ]
})
export class HeaderComponent implements OnInit, DoCheck {

  @Input() active: boolean = true;

  @ViewChild("searchLoader") searchLoader: LoaderComponent;
  @ViewChild("searchBox") searchBox: ElementRef;

  page: number;
  params: any;

  authenticated: boolean = false;
  menuOpened: boolean = false;
  searchOpened: boolean = false;
  suggestsShown: boolean = false;
  suggests: string[];
  userData: any;

  searchForm: FormGroup;



  constructor(
    private loginService: LoginService,
    private newsService: NewsService,
    private fb: FormBuilder,
    private renderer: Renderer,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {

    this.page = parseInt(this.route.snapshot.params['page'], 10);
    this.params = this.route.snapshot.queryParams;

    this.authenticated = this.loginService.isAuthenticated();
    
    if(this.authenticated) {
      this.userData = JSON.parse(localStorage.getItem(LoginService.USER_DATA_STRING));
    }

    this.searchForm = this.fb.group({
      tag: ['', Validators.required]
    });

    this.renderer.listenGlobal("document", "click", (evt) => {
      if(evt.target.className !== "header__search-box__suggests__item") {
        this.suggestsShown = false;
      }
    });
    this.renderer.listenGlobal("document", "keyup", (evt) => {
      if(evt.keyCode === 27) {
        this.searchOpened = false;
        this.suggestsShown = false;
      }
    });
  }

  ngDoCheck() {
    this.page = parseInt(this.route.snapshot.params['page'], 10);
  }




  public isActive(route: any[]): boolean {
    return this.router.isActive(this.router.createUrlTree(route), false);
  }


  public toggleMenu() {
    this.menuOpened = !this.menuOpened;
  }

  public toggleSearch() {
    if(!this.searchOpened) {
      this.searchOpened = true;
      this.searchBox.nativeElement.focus();
    }
  }


  public showSuggests(e: any) {
    this.suggestsShown = e.target.value.length > 0;
    if(this.suggestsShown) {
      this.searchLoader.enable();
      this.newsService.getTagsSuggests(e.target.value)
        .then(res => this.suggests = res)
        .then(() => this.searchLoader.disable());
    }
  }

  public closeSuggests() {
    this.suggestsShown = false;
    this.searchForm.reset();
  }


  public logout(): void {
    this.loginService.logout();
    window.location.reload();
  }
}
