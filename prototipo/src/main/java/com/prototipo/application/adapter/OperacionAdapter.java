package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.OperacionDto;
import com.prototipo.application.port.OperacionAbstract;
import com.prototipo.application.useCase.OperacionService;
import com.prototipo.domain.model.OperacionDomain;

public class OperacionAdapter implements OperacionService {

    private OperacionAbstract operacionAbstract;
    private MapperApplicationAbstract mapperApplicationAbstract;

    public OperacionAdapter(OperacionAbstract operacionAbstract,
                            MapperApplicationAbstract mapperApplicationAbstract) {

        this.operacionAbstract = operacionAbstract;
        this.mapperApplicationAbstract = mapperApplicationAbstract;
    }

    @Override
    public OperacionDomain findOperacionByIdSoliService(Long id) {
        OperacionDto operacionDto = operacionAbstract.findOperacionByIdSoliAbstract(id);
        return mapperApplicationAbstract.mapearAbstract(operacionDto, OperacionDomain.class);
    }
}
