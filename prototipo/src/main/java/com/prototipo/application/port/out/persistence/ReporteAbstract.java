package com.prototipo.application.port.out.persistence;

import com.prototipo.domain.model.Reporte;

import java.util.List;

public interface ReporteAbstract {
    List<Reporte> generarReporteMensualPDFAbstract(String mesAnio);
}
