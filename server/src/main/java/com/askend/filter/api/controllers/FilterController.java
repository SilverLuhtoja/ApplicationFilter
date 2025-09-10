package com.askend.filter.api.controllers;

import com.askend.filter.api.controllers.resources.CreateFilterResource;
import com.askend.filter.api.controllers.resources.SystemFiltersResource;
import com.askend.filter.api.mappers.FilterMapper;
import com.askend.filter.api.models.FilterCollection;
import com.askend.filter.api.services.FilterService;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin("*")
public class FilterController {

  private final FilterService service;
  private final FilterMapper mapper;

  @GetMapping("/filters")
  @Description("Retrieves all system available filters")
  public ResponseEntity<SystemFiltersResource> getSystemFilters() {
    return ResponseEntity.ok(new SystemFiltersResource(service.getSystemFilters()));
  }

  @GetMapping
  @Description("Retrieves all client saved filters")
  public ResponseEntity<List<FilterCollection>> getSavedFilters() {
    return ResponseEntity.ok(service.getFilters());
  }

  @PostMapping
  @Description("Creates or updates existing filter")
  public ResponseEntity<FilterCollection> createOrUpdateFilter(@RequestBody CreateFilterResource resource) {
    FilterCollection filterCollection = mapper.toDomain(resource);

    FilterCollection response = service.createOrUpdateFilter(filterCollection);

    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/delete/{id}")
  @Description("Deletes single client filter by its id")
  public ResponseEntity<String> deleteFilter(@PathVariable Long id) {
    service.deleteFilter(id);
    return ResponseEntity.ok("Filter has been deleted.");
  }
}
