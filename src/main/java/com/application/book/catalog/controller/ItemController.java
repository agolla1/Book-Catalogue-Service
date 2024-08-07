package com.application.book.catalog.controller;

import com.application.book.catalog.domain.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/item")
@RequiredArgsConstructor
public class ItemController {
    private final BookItemService bookItemService;

    @GetMapping
    public ItemPageResult<ItemDto> getProducts(@RequestParam(name = "page") int pageNo) {
        return bookItemService.getBookItems(pageNo);
    }

    @GetMapping("/{code}")
    public ResponseEntity<ItemDto> getProductByCode(@PathVariable String code) {
        return bookItemService
                .getBookItemByCode(code)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ProductNotFoundException(code));
    }

    @GetMapping("/search")
    public ItemPageResult<ItemDto> searchProducts(
            @RequestParam(name = "query") String query,
            @RequestParam(required = false, defaultValue = "1", name = "page") int page) {
        return bookItemService.searchBookItemsByCriteria(query, page);
    }

    @PostMapping
    public ResponseEntity<ItemDto> createProduct(
            @RequestBody @Valid CreateProductModel createProductModel) {

        ItemDto itemDto = bookItemService.createBookItem(createProductModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemDto);
    }

    @DeleteMapping("/{code}")
    public void deleteProduct(@PathVariable String code) {
        bookItemService.deleteBookItem(code);
    }
}
