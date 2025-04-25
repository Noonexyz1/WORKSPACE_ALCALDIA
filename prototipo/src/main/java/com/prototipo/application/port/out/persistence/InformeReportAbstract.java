package com.prototipo.application.port.out.persistence;

import com.prototipo.domain.model.InformeReport;

public interface InformeReportAbstract {
    InformeReport getInformeReport(Long idSolicitud);
}
