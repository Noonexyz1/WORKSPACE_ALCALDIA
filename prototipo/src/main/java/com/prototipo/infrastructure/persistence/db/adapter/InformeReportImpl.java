package com.prototipo.infrastructure.persistence.db.adapter;

import com.prototipo.application.port.out.persistence.InformeReportAbstract;
import com.prototipo.domain.model.InformeReport;
import com.prototipo.infrastructure.persistence.db.repository.InformeReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InformeReportImpl implements InformeReportAbstract {

    @Autowired
    private InformeReportRepository informeReportRepository;

    @Override
    public InformeReport getInformeReport(Long idSolicitud) {
        Object[][] result = informeReportRepository.getInformeReport(idSolicitud);
        String nombreTo = result[0][0] + " " + result[0][1] + " " + result[0][2] + " " + result[0][3];
        String nombreFrom = result[0][4] + " " +  result[0][5] + " " + result[0][6] + " " + result[0][7];

        InformeReport informeReport = InformeReport.builder()
                .funcionarioTo(nombreTo)
                .funcionarioFrom(nombreFrom)
                .funcionarioToCargo(result[0][8].toString())
                .funcionarioFromCargo(result[0][9].toString())
                .cite(result[0][10].toString())
                .fecha(result[0][11].toString())
                .cantidadSumado(result[0][12].toString())
                .nombreUnidad(result[0][13].toString())
                .descripcion(result[0][14].toString())
                .build();
        return informeReport;
    }
}
