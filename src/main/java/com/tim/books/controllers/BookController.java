package com.tim.books.controllers;

import com.tim.books.domain.Book;
import com.tim.books.services.implementations.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<Book> createBook(@PathVariable final String isbn, @RequestBody final Book book) {
        book.setIsbn(isbn);
        final Book savedBook = bookService.create(book);
        final ResponseEntity<Book> response = new ResponseEntity<Book>(savedBook, HttpStatus.CREATED);
        return response;
    }
}
