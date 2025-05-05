export class PageProperties {
  id: number = 0;
  currentPage: number = 0;
  pageSize: number = 10;
  sortBy: string = 'id';
  direction: string = 'DESC';

  totalPages: number = 0;
  totalElements: number = 0;
}
