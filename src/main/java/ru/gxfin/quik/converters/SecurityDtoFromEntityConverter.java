package ru.gxfin.quik.converters;

import org.springframework.beans.factory.annotation.Autowired;
import ru.gxfin.common.data.AbstractDtoFromEntityConverter;
import ru.gxfin.gate.quik.model.internal.QuikSecuritiesPackage;
import ru.gxfin.gate.quik.model.internal.QuikSecurity;
import ru.gxfin.gate.quik.model.memdata.QuikSecuritiesMemoryRepository;
import ru.gxfin.quik.entities.SecurityEntity;

public class SecurityDtoFromEntityConverter extends AbstractDtoFromEntityConverter<QuikSecurity, QuikSecuritiesPackage, SecurityEntity> {
    @Autowired
    private QuikSecuritiesMemoryRepository quikSecuritiesMemoryRepository;

    public void fillDtoFromEntity(QuikSecurity destination, SecurityEntity source) {
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
    protected QuikSecurity getOrCreateDtoByEntity(SecurityEntity source) {
        final var id = source.getClassCode() + ":" + source.getCode();
        var result = this.quikSecuritiesMemoryRepository.getByKey(id);
        if (result == null) {
            result = new QuikSecurity();
        }
        return result;
    }
}
