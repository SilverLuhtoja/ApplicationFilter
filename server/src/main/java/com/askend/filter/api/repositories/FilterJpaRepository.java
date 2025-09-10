package com.askend.filter.api.repositories;

import com.askend.filter.api.repositories.entities.FilterEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilterJpaRepository extends JpaRepository<FilterEntity, Long> {

  Optional<FilterEntity> findById(Long id);

}
