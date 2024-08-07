package com.application.book.catalog.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.application.book.catalog.common.TestHelper;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;

public class BookItemServiceTest {

    @InjectMocks private BookItemService bookItemService;

    @Mock private BookItemRepository bookItemRepository;

    @Mock private ProductMapper productMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnEmptyListForWhenNoResults() {
        when(bookItemRepository.findAllBy(any(TextCriteria.class), any(Pageable.class)))
                .thenReturn(TestHelper.emptyPageOfProduct());
        ItemPageResult<ItemDto> actualPage =
                bookItemService.searchBookItemsByCriteria("search text", 1);

        assertThat(actualPage).isNotNull();
        assertThat(actualPage.data().isEmpty()).isTrue();
        assertThat(actualPage.isFirst()).isTrue();
        assertThat(actualPage.hasNext()).isFalse();
        assertThat(actualPage.pageNumber()).isEqualTo(1);
        // TODO: update the assertion
        // since pagenumber is auto incremented by 1, this results in descrepancy between total
        // pages and current pages number
        // e.g. if no results, then pagenumber = 1, but, totalPages is 0
        assertThat(actualPage.totalPages()).isZero();
        assertThat(actualPage.totalElements()).isZero();

        verify(bookItemRepository, times(1))
                .findAllBy(any(TextCriteria.class), any(Pageable.class));
    }

    @Test
    public void shouldReturnSortedListForMatchedSearches() {
        Page<ItemDto> expectedProductModels = TestHelper.pageOfSortedProductModels();
        when(bookItemRepository.findAllBy(any(TextCriteria.class), any(Pageable.class)))
                .thenReturn(TestHelper.pageOfSortedProducts());

        when(productMapper.toModel(any(BookItem.class)))
                .thenReturn(
                        expectedProductModels.getContent().get(0),
                        expectedProductModels.getContent().get(1));

        ItemPageResult<ItemDto> actualPage = bookItemService.searchBookItemsByCriteria("Dog", 1);

        assertThat(actualPage).isNotNull();
        assertThat(actualPage.data().isEmpty()).isFalse();
        assertThat(actualPage.isFirst()).isTrue();
        assertThat(actualPage.hasNext()).isFalse();
        assertThat(actualPage.pageNumber()).isEqualTo(1);
        assertThat(actualPage.totalPages()).isOne();
        assertThat(actualPage.totalElements()).isEqualTo(2);
        assertThat(actualPage.data().get(0).code()).isEqualTo("P200");
        assertThat(actualPage.data().get(1).code()).isEqualTo("P201");

        verify(bookItemRepository, times(1))
                .findAllBy(any(TextCriteria.class), any(Pageable.class));
        verify(productMapper, times(2)).toModel(any(BookItem.class));
    }

    @Test
    public void testDeleteProduct_whenProductExists_shouldReturnDeletedBookItem() {
        String code = "P109";
        BookItem bookItem = createBookItem(code);
        when(bookItemRepository.findByCode(code)).thenReturn(Optional.of(bookItem));
        when(bookItemRepository.save(bookItem)).thenReturn(Optional.of(bookItem).get());

        bookItemService.deleteBookItem(code);

        Optional<BookItem> deletedProduct = bookItemRepository.findByCode(code);

        assertTrue(deletedProduct.isPresent());
        assertEquals(code, deletedProduct.get().getCode());
        assertTrue(deletedProduct.get().isDeleted());
    }

    @Test
    public void testDeleteProduct_whenProductDoesNotExist_shouldThrowBookItemNotFoundException() {
        String code = "P1090";
        when(bookItemRepository.findByCode(code)).thenReturn(Optional.empty());

        Assertions.assertThrows(
                ProductNotFoundException.class,
                () -> {
                    bookItemService.deleteBookItem(code);
                });
    }

    private BookItem createBookItem(String code) {
        return new BookItem(
                null,
                code,
                "The Little Prince",
                "Moral allegory and spiritual autobiography, The Little Prince is the most translated book in the French language.",
                "https://images.gr-assets.com/books/1367545443l/157993.jpg",
                new BigDecimal("16.50"),
                BigDecimal.ZERO,
                false);
    }
}
