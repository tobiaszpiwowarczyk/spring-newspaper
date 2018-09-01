import { Article } from './../../models/Article';
import { User } from './../../models/User';
export class ArticleComment {
  id: string;
  content: string;
  date: Date;
  user: User;
  post: Article;

  processing: boolean = false;
  
  constructor(values: Object = {}) {
    Object.assign(this, values);
  }

}