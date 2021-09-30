package ru.gx.fin.quik.converters;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gx.data.jpa.AbstractDtoFromEntityConverter;
import ru.gx.fin.gate.quik.model.internal.QuikSecuritiesPackage;
import ru.gx.fin.gate.quik.model.internal.QuikSecurity;
import ru.gx.fin.gate.quik.model.memdata.QuikSecuritiesMemoryRepository;
import ru.gx.fin.quik.entities.SecurityEntity;

import static lombok.AccessLevel.*;

public class SecurityDtoFromEntityConverter extends AbstractDtoFromEntityConverter<QuikSecurity, QuikSecuritiesPackage, SecurityEntity> {
    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private QuikSecuritiesMemoryRepository quikSecuritiesMemoryRepository;

    public void fillDtoFromEntity(@NotNull final QuikSecurity destination, @NotNull final SecurityEntity source) {
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
    protected QuikSecurity getOrCreateDtoByEntity(@NotNull final SecurityEntity source) {
        final var id = source.getClassCode() + ":" + source.getCode();
        var result = this.quikSecuritiesMemoryRepository.getByKey(id);
        if (result == null) {
            result = new QuikSecurity();
        }
        return result;
    }
}
