package com.application.book.catalog.common;

import com.application.book.catalog.domain.BookItem;
import com.application.book.catalog.domain.ItemDto;
import com.application.book.catalog.domain.ProductMapper;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

public class TestHelper {

    private static final String SEARCH_ENDPOINT = "/api/products/search?query=%s";
    public static final String CREATE_PRODUCT_ENDPOINT = "/api/products";

    public static String buildSearchEndpoint(String queryString) {
        return String.format(SEARCH_ENDPOINT, queryString);
    }

    public static Page<ItemDto> emptyPageOfProductModel() {
        return Page.empty(PageRequest.of(0, 2));
    }

    public static Page<BookItem> emptyPageOfProduct() {
        return Page.empty(PageRequest.of(0, 2));
    }

    public static Page<BookItem> pageOfSortedProducts() {
        return new PageImpl<>(stubbedProducts());
    }

    public static Page<ItemDto> pageOfSortedProductModels() {
        ProductMapper productMapper = new ProductMapper();
        return pageOfSortedProducts().map(productMapper::toModel);
    }

    public static List<BookItem> sortableProducts() {
        return stubbedProducts();
    }

    private static List<BookItem> stubbedProducts() {
        return List.of(
                new BookItem(
                        null,
                        "P200",
                        "A Dog with A Ball",
                        "Awesome read about a dog with super ball",
                        null,
                        BigDecimal.TEN,
                        BigDecimal.valueOf(0.1),
                        false),
                new BookItem(
                        null,
                        "P201",
                        "Dog",
                        "Awesome read about a dog with super powers",
                        null,
                        BigDecimal.TEN,
                        BigDecimal.valueOf(0.5),
                        false));
    }
}
