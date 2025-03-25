package com.prototipo.infrastructure.persistence.db.adapter;

import com.prototipo.application.port.out.persistence.DocumentoRetiroAbstract;
import com.prototipo.domain.model.DocumentoRetiro;
import com.prototipo.infrastructure.persistence.db.entity.DocumentoRetiroEntity;
import com.prototipo.infrastructure.persistence.db.repository.DocumentoRetiroRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DocumentoRetiroImpl implements DocumentoRetiroAbstract {

    @Autowired
    private DocumentoRetiroRepository documentoRetiroRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public DocumentoRetiro getRetiroDocuByFkFoto(Long idFotocopia) {
        DocumentoRetiroEntity documentoRetiroEntity = documentoRetiroRepository
                .findByFkFotocopia(idFotocopia);
        return modelMapper.map(documentoRetiroEntity, DocumentoRetiro.class);
    }

    @Override
    @Transactional
    public void aprobarDocumentoRetiro(DocumentoRetiro documentoRetiro) {
        DocumentoRetiroEntity documentoRetiroEntity = modelMapper
                .map(documentoRetiro, DocumentoRetiroEntity.class);

        documentoRetiroRepository.save(documentoRetiroEntity);
    }
}
