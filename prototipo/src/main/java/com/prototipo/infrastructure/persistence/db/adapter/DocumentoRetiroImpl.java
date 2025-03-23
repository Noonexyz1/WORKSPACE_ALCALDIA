package com.prototipo.infrastructure.persistence.db.adapter;

import com.prototipo.application.port.out.persistence.DocumentoRetiroAbstract;
import com.prototipo.domain.model.DocumentoRetiro;
import com.prototipo.infrastructure.persistence.db.repository.DocumentoRetiroRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DocumentoRetiroImpl implements DocumentoRetiroAbstract {

    @Autowired
    private DocumentoRetiroRepository documentoRetiroRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public DocumentoRetiro getRetiroDocuByFkFoto(Long idFotocopia) {
        return documentoRetiroRepository.findByFkFotocopia(idFotocopia);
    }
}
