export class PageProperties {
  id: number = 0;
  currentPage: number = 0;
  pageSize: number = 10;
  sortBy: string = 'nombre';
  direction: string = 'ASC';

  totalPages: number = 0;
  totalElements: number = 0;
}
