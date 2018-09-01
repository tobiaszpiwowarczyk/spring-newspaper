
import {News} from "../../models/News";
import {Pagination} from "../../components/pagination/Pagination";
export class Home {
  pagination: Pagination;
  news: News[];

  constructor(values: Object = {}) {
    Object.assign(this, values);
  }
}
