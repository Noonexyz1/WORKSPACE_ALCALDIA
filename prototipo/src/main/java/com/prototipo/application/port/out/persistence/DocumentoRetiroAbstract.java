package com.prototipo.application.port.out.persistence;

import com.prototipo.domain.model.DocumentoRetiro;

import java.util.List;

public interface DocumentoRetiroAbstract {
    DocumentoRetiro getRetiroDocuByFkFoto(Long idFotocopia);
    void aprobarDocumentoRetiro(DocumentoRetiro documentoRetiro);
    DocumentoRetiro getDocumentoRetiroById(Long id);
    List<DocumentoRetiro> guardarListaDocumentoRetiro(List<DocumentoRetiro> listDocuRetiSave);
}
