package com.application.book.catalog.domain;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookItem {
    @Id private String id;

    @Indexed(unique = true)
    @NotEmpty(message = "Product code must not be null/empty")
    private String code;

    @NotEmpty(message = "Product name must not be null/empty")
    @TextIndexed
    private String name;

    @TextIndexed private String description;

    private String imageUrl;

    @NotNull(message = "Product price must not be null")
    @DecimalMin("0.1")
    private BigDecimal price;

    private BigDecimal discount;

    private boolean deleted = Boolean.FALSE;
}
