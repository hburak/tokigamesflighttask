package com.hburak.flight_checker.enums;

public enum OrderBy {
    DEPARTURE("departureTime"), ARRIVAL("arrivalTime");
    private String orderBy;
    private OrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
    public String getOrderBy() {
        return orderBy;
    }
}