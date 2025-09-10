package com.askend.filter.api.controller;

import static com.askend.filter.api.datasets.FilterDataset.AMOUNT_VALUE;
import static com.askend.filter.api.datasets.FilterDataset.API_BASE_PATH;
import static com.askend.filter.api.datasets.FilterDataset.DELETE_PATH;
import static com.askend.filter.api.datasets.FilterDataset.FILTER_NAME;
import static com.askend.filter.api.datasets.FilterDataset.SYSTEM_FILTERS_PATH;
import static com.askend.filter.api.models.enums.FilterCriteria.AMOUNT;
import static com.askend.filter.api.models.enums.FilterCriteria.CriteriaAmountOperators.GREATER_THAN;
import static com.askend.filter.api.models.enums.FilterCriteria.CriteriaTitleOperators.STARTS_WITH;
import static com.askend.filter.api.models.enums.FilterCriteria.TITLE;
import static java.lang.String.format;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.StreamUtils.copyToString;

import com.askend.filter.api.mappers.FilterMapper;
import com.askend.filter.api.models.Filter;
import com.askend.filter.api.models.FilterCollection;
import com.askend.filter.api.repositories.FilterJpaRepository;
import com.askend.filter.api.repositories.entities.FilterEntity;
import com.askend.filter.config.IntegrationTest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.jdbc.Sql;

class FilterControllerIntTest extends IntegrationTest {

  @Autowired
  private FilterMapper mapper;

  @Autowired
  private FilterJpaRepository jpaRepository;

  @Value("classpath:testdata/create-filter.json")
  private Resource createFilter;

  @Value("classpath:testdata/systemFilters.json")
  private Resource systemFilters;

  @Test
  void shouldReturnSystemFilters() throws Exception {
    mockMvc.perform(get(SYSTEM_FILTERS_PATH))
        .andExpect(status().isOk())
        .andExpect(content().json(getResourceAsString(systemFilters)));
  }

  @Nested
  class GetEvents {

    @Test
    @Sql("/testdata/create-filter.sql")
    void shouldReturnSavedFilters() throws Exception {
      mockMvc.perform(get(API_BASE_PATH))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$[0].filterList[0].criteria", is(AMOUNT.name())))
          .andExpect(jsonPath("$[0].filterList[0].operator", is(GREATER_THAN.name())))
          .andExpect(jsonPath("$[0].filterList[0].value", is(AMOUNT_VALUE)));
    }
  }

  @Nested
  class CreateOrUpdateEvents {

    @Test
    void shouldCreateFilter() throws Exception {
      mockMvc.perform(post(API_BASE_PATH)
              .contentType("application/json")
              .content(getResourceAsString(createFilter)))
          .andExpect(status().isOk());

      List<FilterEntity> entities = jpaRepository.findAll();
      assertEquals(1, entities.size());
      FilterCollection createdFilter = mapper.toDomain(entities.get(0));
      Filter first = createdFilter.filterList().get(0);
      assertEquals(FILTER_NAME, createdFilter.name());
      assertEquals(TITLE.name().toLowerCase(), first.criteria());
      assertEquals(STARTS_WITH.name().toLowerCase(), first.operator());
      assertEquals("Begin", first.value());
    }
  }

  @Nested
  class DeleteEvents {

    @Test
    @Sql("/testdata/create-filter.sql")
    void shouldDeleteFilter() throws Exception {
      Long filterCollectionId = jpaRepository.findAll().get(0).getId();
      String expectedResponseMessage = "Filter has been deleted.";
      mockMvc.perform(delete(format(DELETE_PATH, filterCollectionId)))
          .andExpect(status().isOk())
          .andExpect(content().string(expectedResponseMessage));
    }

    @Test
    void shouldThrowNotFound() throws Exception {
      mockMvc.perform(delete(format(DELETE_PATH,1L)))
          .andExpect(status().isNotFound());
    }
  }

  @Override
  protected String[] getTableNames() {
    return new String[]{"filter.filter"};
  }

  public static String getResourceAsString(Resource resource) {
    try {
      return copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }
}
