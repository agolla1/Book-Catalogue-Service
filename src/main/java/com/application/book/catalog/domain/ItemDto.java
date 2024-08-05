package com.application.book.catalog.domain;

import java.math.BigDecimal;

public record ItemDto(
        String id,
        String code,
        String name,
        String description,
        String imageUrl,
        BigDecimal price,
        BigDecimal discount,
        BigDecimal salePrice) {}
