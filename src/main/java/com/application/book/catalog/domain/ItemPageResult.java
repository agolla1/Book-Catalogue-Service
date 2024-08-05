package com.application.book.catalog.domain;

import java.util.List;
import org.springframework.data.domain.Page;

public record ItemPageResult<T>(
        List<T> data,
        long totalElements,
        int pageNumber,
        int totalPages,
        boolean isFirst,
        boolean isLast,
        boolean hasNext,
        boolean hasPrevious) {

    // TODO: Remove this constructor in favor of static method
    public ItemPageResult(Page<T> page) {
        this(
                page.getContent(),
                page.getTotalElements(),
                page.getNumber() + 1,
                page.getTotalPages(),
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious());
    }

    /**
     * Convinience method to return an instance of PagedResult from Page
     *
     * @param page
     */
    public static <T> ItemPageResult<T> fromPage(Page<T> page) {
        return new ItemPageResult<T>(
                page.getContent(),
                page.getTotalElements(),
                page.getNumber() + 1,
                page.getTotalPages(),
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious());
    }
}
