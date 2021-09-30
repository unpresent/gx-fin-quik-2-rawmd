package ru.gx.fin.quik.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.gx.data.jpa.AbstractEntitiesPackage;

@JsonIgnoreProperties({"allCount"})
public class AllTradeEntitiesPackage extends AbstractEntitiesPackage<AllTradeEntity> {
}
