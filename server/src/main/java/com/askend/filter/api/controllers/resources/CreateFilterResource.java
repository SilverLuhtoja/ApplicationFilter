package com.askend.filter.api.controllers.resources;

import com.askend.filter.api.models.Filter;
import jakarta.annotation.Nullable;
import java.util.List;

public record CreateFilterResource(@Nullable Long id, String name, List<Filter> filterList) {

}
