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
    public List<AprobacionDomain> listaDeSolicitudesService(Long idSupervisor) {
        //TODO---- Buscar por id de responsable todos aquellas solicitudes que le pertencen a su unidad
        //1 buscar en la tabla Responsable
        //2 encontrar el ID de la unida del que es responsable el idSupervisor
        //3 encontrar todas las Solicitudes correspondientes a esa unidad
        // aunque seria mucho mejor ir directamente a la tabla Aprobacion porque esta tabla tiene con el
        // todos los campos foraneos como Id de solicitud, y esta arrastra consigo el id de unidad

        //busco directamente en la tabla Aprobacion y filtro todos los registros por fkSolicitud luego accedo
        //a a traves de esta llave foranea, el fkUnidadId del cual ya se el Id de la unidad correspondiente

        List<AprobacionDto> aprobacionDtos = aprobacionAbstract.listaDeSolicitudesAbstract();
        List<AprobacionDomain> aprobacionDomains = aprobacionDtos.stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, AprobacionDomain.class))
                .toList();
        return aprobacionDomains;
    }
}
