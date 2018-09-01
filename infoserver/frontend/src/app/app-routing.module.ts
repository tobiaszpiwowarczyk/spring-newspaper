import { ProfileComponent } from './pages/profile/profile.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HomeComponent} from "./pages/home/home.component";
import {NotFoundComponent} from "./pages/not-found/not-found.component";
import {AboutComponent} from "./pages/about/about.component";
import {ArticleComponent} from "./pages/article/article.component";
import {AuthorPostsComponent} from "./pages/author-posts/author-posts.component";
import {LoginComponent} from "./pages/login/login.component";

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'home/:page', component: HomeComponent },
  { path: "about", component: AboutComponent },
  { path: "article/:id", component: ArticleComponent },
  { path: "author/:id", component: AuthorPostsComponent },
  { path: "profile", component: ProfileComponent },
  { path: "", redirectTo: "/home/1", pathMatch: "full" },
  { path: "**", redirectTo: "/notfound", pathMatch: "full" },
  { path: "notfound", component: NotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
