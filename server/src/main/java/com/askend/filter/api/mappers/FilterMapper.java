package com.askend.filter.api.mappers;

import com.askend.filter.api.controllers.resources.CreateFilterResource;
import com.askend.filter.api.models.Filter;
import com.askend.filter.api.models.FilterCollection;
import com.askend.filter.api.repositories.entities.FilterEntity;
import com.askend.filter.config.MapStructConfig;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", config = MapStructConfig.class)
public interface FilterMapper {

  ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  FilterCollection toDomain(CreateFilterResource resource);

  @Mapping(target = "filterList", expression = "java(fromJson(entity.getFilter()))")
  FilterCollection toDomain(FilterEntity entity);

  @Mapping(target = "filter", expression = "java(toJson(filterCollection.filterList()))")
  FilterEntity toEntity(FilterCollection filterCollection);

  default List<Filter> fromJson(String json) {
    try {
      return OBJECT_MAPPER.readValue(json, new TypeReference<>() {
      });
    } catch (Exception e) {
      throw new RuntimeException("Failed to deserialize filter JSON", e);
    }
  }

  default String toJson(List<Filter> filters) {
    try {
      return OBJECT_MAPPER.writeValueAsString(filters);
    } catch (Exception e) {
      throw new RuntimeException("Failed to serialize filter JSON", e);
    }
  }
}
