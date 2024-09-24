package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.AprobacionDto;
import com.prototipo.application.port.AprobacionAbstract;
import com.prototipo.application.useCase.AprobacionService;
import com.prototipo.domain.model.AprobacionDomain;

import java.util.List;

public class AprobacionAdapter implements AprobacionService {

    private AprobacionAbstract aprobacionAbstract;
    private MapperApplicationAbstract mapperApplicationAbstract;

    public AprobacionAdapter(AprobacionAbstract aprobacionAbstract,
                             MapperApplicationAbstract mapperApplicationAbstract) {

        this.aprobacionAbstract = aprobacionAbstract;
        this.mapperApplicationAbstract = mapperApplicationAbstract;
    }

    @Override
    public AprobacionDomain findAprovacionByIdSoliService(Long idSolicitud) {
        AprobacionDto aprobacionDto = aprobacionAbstract.findAprovacionByIdSoliAbstract(idSolicitud);
        return mapperApplicationAbstract.mapearAbstract(aprobacionDto, AprobacionDomain.class);
    }

    @Override
    public List<AprobacionDomain> listaDeSolicitudesService() {
        List<AprobacionDto> aprobacionDtos = aprobacionAbstract.listaDeSolicitudesAbstract();
        List<AprobacionDomain> aprobacionDomains = aprobacionDtos.stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, AprobacionDomain.class))
                .toList();
        return aprobacionDomains;
    }
}
