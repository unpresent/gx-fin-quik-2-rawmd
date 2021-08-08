package ru.gxfin.quik.services;

import ru.gxfin.common.data.AbstractDtoEntityConvertor;
import ru.gxfin.common.data.ObjectCreateException;
import ru.gxfin.gate.quik.model.internal.QuikSecuritiesPackage;
import ru.gxfin.gate.quik.model.internal.QuikSecurity;
import ru.gxfin.gate.quik.model.memdata.QuikSecuritiesMemoryRepository;
import ru.gxfin.quik.entities.SecurityEntitiesPackage;
import ru.gxfin.quik.entities.SecurityEntity;

public class SeciritiesTransformer extends AbstractDtoEntityConvertor<QuikSecurity, QuikSecuritiesPackage, SecurityEntity, SecurityEntitiesPackage> {
    @Override
    public void setDtoFromEntity(QuikSecurity destination, SecurityEntity source) {
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
    public void setEntityFromDto(SecurityEntity destination, QuikSecurity source) {
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
    protected QuikSecurity getOrCreateDtoByEntity(SecurityEntity source) throws ObjectCreateException {
        final var id = source.getCode() + ":" + source.getClassCode();
        return QuikSecuritiesMemoryRepository.ObjectsFactory.getOrCreateObject(id);
    }

    @Override
    protected SecurityEntity getOrCreateEntityByDto(QuikSecurity source) {
        // В нашем случае пр загрузке данных из Kafka в БД не требуется осуществлять предварительный поиск объекта в БД.
        // При сохранении в БД будет осуществляться дедубликация (MERGE).
        return new SecurityEntity();
    }
}
