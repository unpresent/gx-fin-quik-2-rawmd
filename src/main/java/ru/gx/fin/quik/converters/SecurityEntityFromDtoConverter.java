package ru.gx.fin.quik.converters;

import org.jetbrains.annotations.NotNull;
import ru.gx.data.jpa.AbstractEntityFromDtoConverter;
import ru.gx.fin.gate.quik.model.internal.QuikSecurity;
import ru.gx.fin.quik.entities.SecurityEntitiesPackage;
import ru.gx.fin.quik.entities.SecurityEntity;

public class SecurityEntityFromDtoConverter extends AbstractEntityFromDtoConverter<SecurityEntity, SecurityEntitiesPackage, QuikSecurity> {
    @Override
    public void fillEntityFromDto(@NotNull final SecurityEntity destination, @NotNull final QuikSecurity source) {
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
    @NotNull
    protected SecurityEntity getOrCreateEntityByDto(@NotNull final QuikSecurity source) {
        // В нашем случае при загрузке данных из Kafka в БД не требуется осуществлять предварительный поиск объекта в БД.
        // При сохранении в БД будет осуществляться дедубликация (MERGE).
        return new SecurityEntity();
    }
}
