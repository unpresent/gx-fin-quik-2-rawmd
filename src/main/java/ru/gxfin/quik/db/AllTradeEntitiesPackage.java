package ru.gxfin.quik.db;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.gxfin.common.data.AbstractDataPackage;

@JsonIgnoreProperties({"allCount"})
public class AllTradeEntitiesPackage extends AbstractDataPackage<AllTradeEntity> {
}
