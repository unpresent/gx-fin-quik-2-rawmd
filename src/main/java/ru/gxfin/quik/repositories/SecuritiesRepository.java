package ru.gxfin.quik.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gxfin.quik.entities.SecurityEntity;

@Repository
public interface SecuritiesRepository extends JpaRepository<SecurityEntity, String> {
}
