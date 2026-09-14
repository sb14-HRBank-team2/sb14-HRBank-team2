package com.sprint.hrbank.adapter.persistence.department;

public record DepartmentSearchCond(
    String nameOrDescription,
    Long idAfter,
    String cursor,
    Integer size,
    String sortField,
    String sortDirection) {
  public DepartmentSearchCond {
    if (size == null) {
      size = 10;
    }
    if (sortField == null) {
      sortField = "name";
    }
    if (sortDirection == null) {
      sortDirection = "asc";
    }
  }
}
