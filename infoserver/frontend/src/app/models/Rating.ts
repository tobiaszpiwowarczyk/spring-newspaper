
export class Rating {
  thumbsUp: any[];
  thumbsDown: any[];
  value: number;

  constructor(values: Object = {}) {
    Object.assign(this, values);
  }
}
