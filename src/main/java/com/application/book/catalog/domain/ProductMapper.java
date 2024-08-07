package com.application.book.catalog.domain;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ItemDto toModel(BookItem bookItem) {
        var discount = BigDecimal.ZERO;
        if (bookItem.getDiscount() != null) {
            discount = bookItem.getDiscount();
        }
        return new ItemDto(
                bookItem.getId(),
                bookItem.getCode(),
                bookItem.getName(),
                bookItem.getDescription(),
                bookItem.getImageUrl(),
                bookItem.getPrice(),
                discount,
                bookItem.getPrice().subtract(discount));
    }

    public BookItem fromCreateBookItemModel(CreateProductModel createProductModel) {
        return new BookItem(
                null,
                createProductModel.code(),
                createProductModel.name(),
                createProductModel.description(),
                createProductModel.imageUrl(),
                createProductModel.price(),
                BigDecimal.ZERO,
                false);
    }
}
