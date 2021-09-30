package ru.gx.fin.quik.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gx.fin.quik.entities.OrderEntity;
import ru.gx.fin.quik.entities.OrderEntityId;

@Repository
public interface OrdersRepository extends JpaRepository<OrderEntity, OrderEntityId> {
}
