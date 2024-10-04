package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.*;
import com.prototipo.application.port.AprobacionAbstract;
import com.prototipo.application.port.ResponsableAbstract;
import com.prototipo.application.port.SolicitudAbstract;
import com.prototipo.application.port.UsuarioAbastract;
import com.prototipo.application.useCase.AprobacionService;
import com.prototipo.domain.model.AprobacionDomain;
import com.prototipo.domain.model.SolicitudDomain;
import com.prototipo.infrastructure.persistence.db.entity.Solicitud;

import java.util.ArrayList;
import java.util.List;

public class AprobacionAdapter implements AprobacionService {

    private AprobacionAbstract aprobacionAbstract;
    private MapperApplicationAbstract mapperApplicationAbstract;
    private ResponsableAbstract responsableAbstract;
    private UsuarioAbastract usuarioAbastract;
    private SolicitudAbstract solicitudAbstract;

    public AprobacionAdapter(AprobacionAbstract aprobacionAbstract,
                             MapperApplicationAbstract mapperApplicationAbstract,
                             ResponsableAbstract responsableAbstract,
                             UsuarioAbastract usuarioAbastract,
                             SolicitudAbstract solicitudAbstract) {

        this.aprobacionAbstract = aprobacionAbstract;
        this.mapperApplicationAbstract = mapperApplicationAbstract;
        this.responsableAbstract = responsableAbstract;
        this.usuarioAbastract = usuarioAbastract;
        this.solicitudAbstract = solicitudAbstract;
    }

    @Override
    public AprobacionDomain findAprovacionByIdSoliService(Long idSolicitud) {
        AprobacionDto aprobacionDto = aprobacionAbstract.findAprovacionByIdSoliAbstract(idSolicitud);
        return mapperApplicationAbstract.mapearAbstract(aprobacionDto, AprobacionDomain.class);
    }

    @Override
    public List<AprobacionDomain> listaDeSolicitudesService(Long idSupervisor) {
        // 2. Obtener el responsable asociado al usuario (supervisor) usando su ID
        ResponsableDto respDto = responsableAbstract.buscarResponsablePorFkUsuario(idSupervisor);

        // 3. Obtener el ID de la unidad correspondiente al responsable
        Long id = respDto.getFkUnidad().getId();
        //ya tengo el ID de la unidad, ahora me voy a buscar a la tabla solicitud

        // 4. Buscar todas las solicitudes asociadas a la unidad del responsable
        List<SolicitudDto> listDto = solicitudAbstract.getListaSolicitudesByUnidad(id);
        List<Long> listIdSoli = listDto.stream().map(SolicitudDto::getId).toList();

        // 5. Para cada solicitud, buscar su aprobación correspondiente, PERO AQUELLOS UE SON "Pendiente"
        List<AprobacionDto> aprobacionDtos = listIdSoli.stream()
                .map(x -> aprobacionAbstract.findAprovacionByIdSoliAbstract(x))
                .toList();

        // 6. Convertir las aprobaciones obtenidas en objetos de dominio
        return aprobacionDtos.stream()
                .map(aprobacionDto -> mapperApplicationAbstract.mapearAbstract(aprobacionDto, AprobacionDomain.class))
                .toList();
    }
}
