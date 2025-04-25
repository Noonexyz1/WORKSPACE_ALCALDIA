import {FotocopiaResponse} from "./FotocopiaResponse";

export class DocumentoRetiroResponse {
  id: number = 0;
  totalCopia: number = 0;
  totalUsado: number = 0;
  totalDisponible: number = 0;
  fkFotocopia: FotocopiaResponse = new FotocopiaResponse();
}
