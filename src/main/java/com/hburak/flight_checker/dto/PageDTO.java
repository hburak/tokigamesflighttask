package com.hburak.flight_checker.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PageDTO<T> {
    private List<T> content;
    private long totalElements;
    private long totalPages;
}
