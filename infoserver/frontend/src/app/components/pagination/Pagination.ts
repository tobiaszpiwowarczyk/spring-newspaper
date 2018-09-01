
export class Pagination {
  totalPages?: number;
  currentPage?: number;
  isFirst?: boolean;
  isLast?: boolean;

  constructor(values: Object = {}) {
    Object.assign(this, values);
  }
}
