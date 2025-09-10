package com.askend.filter.api.repositories;

import com.askend.filter.api.exceptions.FilterNotFoundException;
import com.askend.filter.api.mappers.FilterMapper;
import com.askend.filter.api.models.FilterCollection;
import com.askend.filter.api.repositories.entities.FilterEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FilterRepository {

  private final FilterJpaRepository filterJpaRepository;
  private final FilterMapper mapper;

  public List<FilterCollection> getRecords() {
    List<FilterEntity> entities = filterJpaRepository.findAll();
    return entities.stream().map(mapper::toDomain).toList();
  }

  public FilterCollection createOrUpdateRecord(FilterCollection filterCollection) {
    if (filterCollection.id() == null) {
      return createNewEntity(filterCollection);
    }

    FilterEntity entity = filterJpaRepository.findById(filterCollection.id()).orElseThrow(FilterNotFoundException::new);
    return updateEntity(entity, filterCollection);
  }

  public void deleteRecord(Long id) {
    FilterEntity entity = filterJpaRepository.findById(id)
        .orElseThrow(FilterNotFoundException::new);
    filterJpaRepository.delete(entity);
  }

  private FilterCollection createNewEntity(FilterCollection filterCollection) {
    FilterEntity entity = mapper.toEntity(filterCollection);
    return mapper.toDomain(filterJpaRepository.save(entity));
  }

  private FilterCollection updateEntity(FilterEntity existingEntity, FilterCollection filterCollection) {
    existingEntity.setName(filterCollection.name());
    existingEntity.setFilter(mapper.toJson(filterCollection.filterList()));
    return mapper.toDomain(filterJpaRepository.save(existingEntity));
  }
}
