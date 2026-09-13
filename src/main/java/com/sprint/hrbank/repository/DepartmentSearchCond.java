package com.sprint.hrbank.repository;

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
