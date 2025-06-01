import { Injectable } from '@angular/core';
import {ItemPopover, PaginaData, RowIntemData} from "../../share/tabla-responsable/tabla-responsable.component";
import {PageResponse} from "../models/PageResponse";
import {SolicitudResponResponse} from "../models/SolicitudResponResponse";
import {SolicitudResponse} from "../models/SolicitudResponse";

@Injectable({
  providedIn: 'root'
})
export class ModelMapperService {

  constructor() { }

  pageResponseToPaginaDataResponsable(
    response: PageResponse<SolicitudResponResponse>,
    itemPopoverList: ItemPopover[]): PaginaData {

    //Usar .map en lugar de fo..in es mejor
    const rowItemData: RowIntemData[] = response.content
      .map(solicitud => {

        // Mapea cada solicitud a un RowIntemData
        const columData: string[] = [
          solicitud.idSolicitud + '',      // ID (debe ser el primer elemento)
          solicitud.cite || '',
          solicitud.fecha || '',
          solicitud.nomCompleto || '',
          solicitud.nomCargo || '',
          solicitud.nombreUnidad || ''
        ];

        return {
          //accion: [...this.itemPopoverList], // Copia el array para evitar duplicaciones
          accion: itemPopoverList,
          columData: columData
        };

      });

    //Formamos el objeto para enviar al componente Hijo
    return {
      page: response.page,
      size: response.size,
      sortBy: response.sortBy,
      direction: response.direction,
      content: rowItemData,
      totalPages: response.totalPages,
      totalElements: response.totalElements
    };
  }


  pageResponseSoliToPaginaDataResponsable(
    response: PageResponse<SolicitudResponse>,
    itemPopoverList: ItemPopover[]): PaginaData {

    //Usar .map en lugar de fo..in es mejor
    const rowItemData: RowIntemData[] = response.content
      .map(solicitud => {

        // Mapea cada solicitud a un RowIntemData
        const columData: string[] = [
          solicitud.id + '',      // ID (debe ser el primer elemento)
          solicitud.cite || '',
          solicitud.fecha || '',
          solicitud.descripcion || '',
        ];

        return {
          //accion: [...this.itemPopoverList], // Copia el array para evitar duplicaciones
          accion: itemPopoverList,
          columData: columData
        };

      });

    //Formamos el objeto para enviar al componente Hijo
    return {
      page: response.page,
      size: response.size,
      sortBy: response.sortBy,
      direction: response.direction,
      content: rowItemData,
      totalPages: response.totalPages,
      totalElements: response.totalElements
    };
  }
}
