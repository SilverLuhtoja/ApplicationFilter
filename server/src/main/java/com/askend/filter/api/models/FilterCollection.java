package com.askend.filter.api.models;

import java.util.List;

public record FilterCollection(Long id, String name, List<Filter> filterList) {

}
