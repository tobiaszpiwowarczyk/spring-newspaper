import { ArticleComment } from './../pages/article/ArticleComment';
import {User} from "./User";
import {Rating} from "./Rating";

export class Article {

  id: string;
  title: string;
  description: string;
  content: string;
  image: string;
  tags: string[];
  date: Date;
  user: User;
  category: string;
  rating: Rating;
  comments: ArticleComment[];
  commentsCount: number;

  constructor(values: Object = {}) {
    Object.assign(this, values);
  }
}
