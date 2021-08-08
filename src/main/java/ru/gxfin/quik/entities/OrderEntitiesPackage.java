package ru.gxfin.quik.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.junit.jupiter.api.Order;
import ru.gxfin.common.data.AbstractEntitiesPackage;

@JsonIgnoreProperties({"allCount"})
public class OrderEntitiesPackage extends AbstractEntitiesPackage<OrderEntity> {
}
