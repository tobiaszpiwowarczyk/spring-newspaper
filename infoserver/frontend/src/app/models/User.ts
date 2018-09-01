
import {News} from "./News";
export class User {

  id: string;
  username: string;
  firstName: string;
  lastName: string;
  email: string;
  avatar: string;
  bgImage: string;
  role: string;
  posts: News[];

  constructor(values: Object = {}) {
    Object.assign(this, values);
  }
}
