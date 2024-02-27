package com.tim.books.services.implementations;

import com.tim.books.domain.Book;
import com.tim.books.domain.BookEntity;
import com.tim.books.repositories.BookRepository;
import com.tim.books.services.interfaces.IBookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService implements IBookService {

    private final BookRepository bookRepository;
    public BookService(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public boolean isBookExists(Book book) {
        return bookRepository.existsById(book.getIsbn());
    }

    @Override
    public Book save(final Book book) {
        final BookEntity bookEntity = bookToBookEntity(book);
        final BookEntity savedBookEntity = bookRepository.save(bookEntity);
        return bookEntityToBook(savedBookEntity);
    }

    @Override
    public Optional<Book> findById(String isbn) {
        final Optional<BookEntity> foundBook = bookRepository.findById(isbn);
        return foundBook.map(book -> bookEntityToBook(book));
    }

    @Override
    public List<Book> listBooks() {
        final List<BookEntity> foundBooks = bookRepository.findAll();
        return foundBooks.stream().map(book -> bookEntityToBook(book)).collect(Collectors.toList());
    }

    private BookEntity bookToBookEntity(Book book) {
        return BookEntity.builder()
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .build();
    }

    private Book bookEntityToBook(BookEntity bookEntity) {
        return Book.builder()
                .author(bookEntity.getAuthor())
                .isbn(bookEntity.getIsbn())
                .title(bookEntity.getTitle())
                .build();
    }
}
