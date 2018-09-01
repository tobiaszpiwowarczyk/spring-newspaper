import { DropdownModule } from './components/dropdown/dropdown.module';
import { TabModule } from './components/tab/tab.module';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { NewsComponent } from './components/news/news.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { AboutComponent } from './pages/about/about.component';
import { ArticleComponent } from './pages/article/article.component';
import { RibbonComponent } from './components/ribbon/ribbon.component';
import { NewsInfoComponent } from './components/news-info/news-info.component';
import { AuthorPostsComponent } from './pages/author-posts/author-posts.component';
import {PercentagePipe} from "./pipes/percentage/percentage.pipe";
import { RatingComponent } from './components/rating/rating.component';
import { PaginationComponent } from './components/pagination/pagination.component';
import { BackToTopComponent } from './components/back-to-top/back-to-top.component';
import { DateComponent } from './components/date/date.component';
import { LoginComponent } from './pages/login/login.component';
import { TextInputComponent } from './components/text-input/text-input.component';
import { ButtonModule } from './directives/button/button.directive';
import { SwitchComponent } from './components/switch/switch.component';
import { ToastComponent } from './components/toast/toast.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { HeaderComponent } from './components/header/header.component';
import { TextAreaComponent } from './components/text-area/text-area.component';
import { CommentComponent } from './components/comment/comment.component';
import { LoaderComponent } from './components/loader/loader.component';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    NewsComponent,
    NotFoundComponent,
    AboutComponent,
    ArticleComponent,
    RibbonComponent,
    NewsInfoComponent,
    AuthorPostsComponent,
    PercentagePipe,
    RatingComponent,
    PaginationComponent,
    BackToTopComponent,
    DateComponent,
    LoginComponent,
    TextInputComponent,
    SwitchComponent,
    ToastComponent,
    ProfileComponent,
    HeaderComponent,
    TextAreaComponent,
    CommentComponent,
    LoaderComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    AppRoutingModule,
    ButtonModule,
    DropdownModule,
    TabModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
