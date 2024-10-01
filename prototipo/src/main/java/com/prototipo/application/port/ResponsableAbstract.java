package com.prototipo.application.port;

import com.prototipo.application.modelDto.ResponsableDto;

public interface ResponsableAbstract {
    void aprobarSolicitudAbstract(Long idSolicitud);
    ResponsableDto guardarResponsable(ResponsableDto respDto);
}
