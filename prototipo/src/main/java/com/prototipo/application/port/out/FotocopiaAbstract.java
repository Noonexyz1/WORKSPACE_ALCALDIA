package com.prototipo.application.port.out;

import com.prototipo.domain.model.Fotocopia;

import java.util.List;

public interface FotocopiaAbstract {
    List<Fotocopia> getFotocopiasSolicitudAbstract(Long idSolicitud);
    void guardarRegistroFotocopia(Fotocopia fotocopia);
}
