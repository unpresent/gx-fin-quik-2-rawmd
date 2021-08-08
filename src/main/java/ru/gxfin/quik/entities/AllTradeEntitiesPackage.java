package ru.gxfin.quik.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.gxfin.common.data.AbstractEntitiesPackage;

@JsonIgnoreProperties({"allCount"})
public class AllTradeEntitiesPackage extends AbstractEntitiesPackage<AllTradeEntity> {
}
