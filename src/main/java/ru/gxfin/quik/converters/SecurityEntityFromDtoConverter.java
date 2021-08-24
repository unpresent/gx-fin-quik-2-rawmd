package ru.gxfin.quik.converters;

import ru.gxfin.common.data.AbstractEntityFromDtoConverter;
import ru.gxfin.gate.quik.model.internal.QuikSecurity;
import ru.gxfin.quik.entities.SecurityEntitiesPackage;
import ru.gxfin.quik.entities.SecurityEntity;

public class SecurityEntityFromDtoConverter extends AbstractEntityFromDtoConverter<SecurityEntity, SecurityEntitiesPackage, QuikSecurity> {

    @Override
    public void fillEntityFromDto(SecurityEntity destination, QuikSecurity source) {
        destination
                .setCode(source.getCode())
                .setClassCode(source.getClassCode())
                .setName(source.getName())
                .setShortName(source.getShortName())
                .setClassName(source.getClassName())
                .setFaceValue(source.getFaceValue())
                .setFaceUnit(source.getFaceUnit())
                .setScale(source.getScale())
                .setMaturityDate(source.getMaturityDate())
                .setLotSize(source.getLotSize())
                .setIsinCode(source.getIsinCode())
                .setMinPriceStep(source.getMinPriceStep());
    }

    @Override
    protected SecurityEntity getOrCreateEntityByDto(QuikSecurity source) {
        // В нашем случае при загрузке данных из Kafka в БД не требуется осуществлять предварительный поиск объекта в БД.
        // При сохранении в БД будет осуществляться дедубликация (MERGE).
        return new SecurityEntity();
    }
}
