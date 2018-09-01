
import {User} from "./User";
import { Rating } from "./Rating";

export class News {
  id: string;
  title: string;
  description: string;
  category: string;
  thumbnail: string;
  date: Date;
  user: User;
  tags: string[];
  rating: Rating;
  commentsCount: number;


  constructor(values: Object = {}) {
    Object.assign(this, values);
  }
}
