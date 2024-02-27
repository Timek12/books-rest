package com.tim.books.controllers;

import com.tim.books.domain.Book;
import com.tim.books.services.implementations.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<Book> createUpdateBook(@PathVariable final String isbn, @RequestBody final Book book) {
        book.setIsbn(isbn);
        final Boolean isBookExists = bookService.isBookExists(book);
        final Book savedBook = bookService.save(book);

        if(isBookExists){
            return new ResponseEntity<Book>(savedBook, HttpStatus.OK);
        }

        return new ResponseEntity<Book>(savedBook, HttpStatus.CREATED);
    }

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity retriveBook(@PathVariable final String isbn) {
        final Optional<Book> foundBook = bookService.findById(isbn);
        return foundBook.map(book -> new ResponseEntity<Book>(book, HttpStatus.OK))
                .orElse(new ResponseEntity<Book>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/books")
    public ResponseEntity<List<Book>> listBooks() {
        return new ResponseEntity<List<Book>>(bookService.listBooks(), HttpStatus.OK);
    }

}
