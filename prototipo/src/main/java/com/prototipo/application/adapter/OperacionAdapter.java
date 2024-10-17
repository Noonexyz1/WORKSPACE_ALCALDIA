package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.OperacionDto;
import com.prototipo.application.port.OperacionAbstract;
import com.prototipo.application.useCase.OperacionService;
import com.prototipo.domain.model.Operacion;

public class OperacionAdapter implements OperacionService {

    private OperacionAbstract operacionAbstract;
    private MapperApplicationAbstract mapperApplicationAbstract;

    public OperacionAdapter(OperacionAbstract operacionAbstract,
                            MapperApplicationAbstract mapperApplicationAbstract) {

        this.operacionAbstract = operacionAbstract;
        this.mapperApplicationAbstract = mapperApplicationAbstract;
    }

    @Override
    public Operacion findOperacionByIdSoliService(Long id) {
        OperacionDto operacionDto = operacionAbstract.findOperacionByIdSoliAbstract(id);
        if (operacionDto == null) {
            return null;
        }
        return mapperApplicationAbstract.mapearAbstract(operacionDto, Operacion.class);
    }
}
