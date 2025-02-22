export interface PageResponse<T> {
  page: number;            // Número de página actual
  size: number;            // Tamaño de la página
  sortBy: string;          // Campo de ordenamiento
  direction: string;       // Dirección del ordenamiento

  content: T[];            // Los elementos de la página actual
  totalPages: number;      // Total de páginas disponibles
  totalElements: number;   // Total de elementos en la BD
}
