package com.askend.filter.api.services;

import com.askend.filter.api.exceptions.InvalidValueException;
import com.askend.filter.api.models.FilterCollection;
import com.askend.filter.api.models.enums.FilterCriteria;
import com.askend.filter.api.repositories.FilterRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FilterService {

  private final FilterRepository repository;
  private final FilterValidator validator;

  public Map<String, List<String>> getSystemFilters() {
    return Arrays.stream(FilterCriteria.values())
        .collect(Collectors.toMap(Enum::name, FilterCriteria::getOperators));
  }

  public List<FilterCollection> getFilters() {
    return repository.getRecords();
  }

  public FilterCollection createOrUpdateFilter(FilterCollection filterCollection) {
    validateFilterCollection(filterCollection);
    filterCollection.filterList().forEach(validator::validate);
    return repository.createOrUpdateRecord(filterCollection);
  }

  public void deleteFilter(Long id) {
    repository.deleteRecord(id);
  }

  private void validateFilterCollection(FilterCollection filterCollection) {
    if (filterCollection.name() == null || filterCollection.name().isBlank()) {
      throw new InvalidValueException("Filter collection name must be provided.");
    }

    if (filterCollection.filterList() == null || filterCollection.filterList().isEmpty()) {
      throw new InvalidValueException("Filters must be provided.");
    }
  }
}
