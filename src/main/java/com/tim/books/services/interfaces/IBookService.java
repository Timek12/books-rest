package com.tim.books.services.interfaces;

import com.tim.books.domain.Book;

import java.util.Optional;

public interface IBookService {
    Book create(Book book);
    Optional<Book> findById(String isbn);
}
