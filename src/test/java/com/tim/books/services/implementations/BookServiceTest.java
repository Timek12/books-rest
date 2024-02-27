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
import static com.tim.books.TestData.testBook;
import static com.tim.books.TestData.testBookEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

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

        final Book result = underTest.create(book);
        assertEquals(null, book);
    }

}
