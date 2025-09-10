package com.askend.filter.api.repositories.entities;

import static org.hibernate.type.SqlTypes.JSON;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@Table(schema = "filter", name = "filter")
public class FilterEntity extends BaseEntity {

  @NotNull
  private String name;

  @Column(nullable = false)
  @JdbcTypeCode(JSON)
  private String filter;
}
