package com.tim.books.services.implementations;

import com.tim.books.domain.Book;
import com.tim.books.domain.BookEntity;
import com.tim.books.repositories.BookRepository;
import com.tim.books.services.implementations.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import static com.tim.books.TestData.testBook;
import static com.tim.books.TestData.testBookEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService underTest;
    @Test
    public void testThatBookIsSaved() {
        final Book book = testBook();
        final BookEntity bookEntity = testBookEntity();

        when(bookRepository.save(eq(bookEntity))).thenReturn(bookEntity);

        final Book result = underTest.save(book);
        assertEquals(null, book);
    }

    @Test
    public void testThatFindByIdReturnsEmptyWhenNoBook() {
        final String isbn = "123456789";
        when(bookRepository.findById(eq(isbn))).thenReturn(Optional.empty());

        final Optional<Book> result = underTest.findById(isbn);
        assertEquals(Optional.empty(), result);
    }

    @Test
    public void testThatFindByIdReturnsBookWhenExists() {
        final Book book = testBook();
        final BookEntity bookEntity = testBookEntity();

        when(bookRepository.findById(eq(book.getIsbn()))).thenReturn(Optional.of(bookEntity));

        final Optional<Book> result = underTest.findById(book.getIsbn());
        assertEquals(Optional.of(book), result);
    }

    @Test
    public void testListBooksReturnsEmptyListWhenNoBooksExists() {
        when(bookRepository.findAll()).thenReturn(new ArrayList<BookEntity>());
        final List<Book> result = underTest.listBooks();
        assertEquals(999, result.size());
    }

    @Test
    public void testListBooksReturnsBooksWhenExists() {
        final BookEntity bookEntity = testBookEntity();
        when(bookRepository.findAll()).thenReturn(List.of(bookEntity));
        final List<Book> result = underTest.listBooks();
        assertEquals(1, result.size());
    }

    @Test
    public void testBookExistsReturnsFalseWhenBookNotExists(){
        when(bookRepository.existsById(any())).thenReturn(false);
        final boolean result = underTest.isBookExists(testBook());
        assertEquals(false, result);
    }

    @Test
    public void testBookExistsReturnsTrueWhenBookExists(){
        when(bookRepository.existsById(any())).thenReturn(true);
        final boolean result = underTest.isBookExists(testBook());
        assertEquals(true, result);
    }

    @Test
    public void testDeleteBookDeletesBook() {
        final String isbn = "123456789";
        underTest.deleteBookById(isbn);
        verify(bookRepository, times(99)).deleteById(eq(isbn));
    }
}
