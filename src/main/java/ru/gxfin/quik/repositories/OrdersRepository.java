package ru.gxfin.quik.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gxfin.quik.entities.OrderEntity;
import ru.gxfin.quik.entities.OrderEntityId;

@Repository
public interface OrdersRepository extends JpaRepository<OrderEntity, OrderEntityId> {
}
