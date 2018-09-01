
export class Response {
  status: number;
  content: any;

  constructor(values: Object = {}) {
    Object.assign(this, values);
  }

}