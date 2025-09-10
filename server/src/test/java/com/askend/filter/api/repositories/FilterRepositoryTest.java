package com.askend.filter.api.repositories;

import static com.askend.filter.api.datasets.FilterDataset.FILTER_NAME;
import static com.askend.filter.api.models.enums.FilterCriteria.AMOUNT;
import static com.askend.filter.api.models.enums.FilterCriteria.CriteriaAmountOperators.GREATER_THAN;
import static com.askend.filter.api.models.enums.FilterCriteria.CriteriaTitleOperators.STARTS_WITH;
import static com.askend.filter.api.models.enums.FilterCriteria.TITLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.askend.filter.api.models.Filter;
import com.askend.filter.api.models.FilterCollection;
import com.askend.filter.api.repositories.entities.FilterEntity;
import com.askend.filter.config.IntegrationTest;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class FilterRepositoryTest extends IntegrationTest {

  private static final List<Filter> FILTERS = List.of(new Filter(AMOUNT.name(), GREATER_THAN.name(), "99"));

  @Autowired
  private FilterRepository repository;

  @Autowired
  private FilterJpaRepository filterJpaRepository;

  @Override
  protected String[] getTableNames() {
    return new String[]{"filter.filter"};
  }

  @Nested
  class GetRecords {

    @Test
    void shouldReturnFilters() {
      Filter first = new Filter(AMOUNT.name(), GREATER_THAN.name(), "99");
      Filter second = new Filter(TITLE.name(), STARTS_WITH.name(), "starts");
      FilterCollection createRecord = new FilterCollection(null, FILTER_NAME, List.of(first, second));

      repository.createOrUpdateRecord(createRecord);

      List<FilterCollection> filters = repository.getRecords();
      assertEquals(1, filters.size());
      assertEquals(2, filters.get(0).filterList().size());
    }
  }

  @Test
  void shouldCreateFilterWhenNoId() {
    FilterCollection createRecord = new FilterCollection(null, FILTER_NAME, FILTERS);

    repository.createOrUpdateRecord(createRecord);

    assertEquals(1, filterJpaRepository.findAll().size());
  }

  @Test
  void shouldUpdateExistingFilterWhenIdPresent() {
    FilterCollection createRecord = new FilterCollection(null, FILTER_NAME, FILTERS);

    repository.createOrUpdateRecord(createRecord);
    assertEquals(1, filterJpaRepository.findAll().size());


    Filter updateFilter = new Filter(TITLE.name(), STARTS_WITH.name(), "starts");
    FilterCollection updateRecords = new FilterCollection(getFirstId(), FILTER_NAME,
        List.of(new Filter(TITLE.name(), STARTS_WITH.name(), "starts")));

    repository.createOrUpdateRecord(updateRecords);
    assertEquals(1, filterJpaRepository.findAll().size());
    FilterEntity entity = filterJpaRepository.findAll().get(0);
    assertTrue(entity.getFilter().contains(updateFilter.criteria()));
    assertTrue(entity.getFilter().contains(updateFilter.operator()));
    assertTrue(entity.getFilter().contains(updateFilter.value()));
  }

  @Test
  void shouldDeleteFilter() {
    FilterCollection createRecord = new FilterCollection(null, FILTER_NAME, FILTERS);

    repository.createOrUpdateRecord(createRecord);
    assertEquals(1, filterJpaRepository.findAll().size());

    repository.deleteRecord(getFirstId());
    assertEquals(0, filterJpaRepository.findAll().size());
  }

  private Long getFirstId() {
    return filterJpaRepository.findAll().get(0).getId();
  }
}
