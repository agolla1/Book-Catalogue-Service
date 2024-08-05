package com.application.book.catalog.domain;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookItemService {
    private static final int PAGE_SIZE = 20;

    private final BookItemRepository bookItemRepository;
    private final ProductMapper productMapper;

    /**
     * @param pageNo
     * @return
     */
    public ItemPageResult<ItemDto> getProducts(int pageNo) {
        int page = pageNo <= 1 ? 0 : pageNo - 1;
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.Direction.ASC, "name");
        Page<ItemDto> productsPage =
                bookItemRepository.findAll(pageable).map(productMapper::toModel);
        return new ItemPageResult<>(productsPage);
    }

    /**
     * @param pageNo
     * @return
     */
    private int getRepositoryPageNo(int pageNo) {
        return pageNo <= 1 ? 0 : pageNo - 1;
    }

    /**
     * @param code
     * @return
     */
    public Optional<ItemDto> getProductByCode(String code) {
        return bookItemRepository.findByCode(code).map(productMapper::toModel);
    }

    /**
     * @param searchCriteria
     * @param page
     * @return
     */
    public ItemPageResult<ItemDto> searchProductsByCriteria(String searchCriteria, int page) {
        Page<ItemDto> productPage =
                bookItemRepository
                        .findAllBy(
                                textCriteriaMatching(searchCriteria),
                                pageableSortOf(getRepositoryPageNo(page)))
                        .map(productMapper::toModel);
        return ItemPageResult.fromPage(productPage);
    }

    /**
     * @param searchCriteria
     * @return
     */
    private TextCriteria textCriteriaMatching(String searchCriteria) {
        return new TextCriteria().matchingPhrase(searchCriteria);
    }

    /**
     * @param page
     * @return
     */
    private Pageable pageableSortOf(int page) {
        return PageRequest.of(page, PAGE_SIZE, Sort.Direction.ASC, "name");
    }

    /**
     * @param createProductModel
     * @return
     */
    public ItemDto createProduct(CreateProductModel createProductModel) {
        boolean existsProductByCode =
                bookItemRepository.existsProductByCode(createProductModel.code());
        if (existsProductByCode) {
            throw new ProductAlreadyExistsException(createProductModel.code());
        }
        BookItem bookItem = productMapper.fromCreateProductModel(createProductModel);
        BookItem savedBookItem = bookItemRepository.save(bookItem);
        return productMapper.toModel(savedBookItem);
    }

    /** @param code */
    public void deleteProduct(String code) {
        BookItem bookItem =
                bookItemRepository
                        .findByCode(code)
                        .orElseThrow(() -> new ProductNotFoundException(code));
        bookItem.setDeleted(true);
        bookItemRepository.save(bookItem);
    }
}
