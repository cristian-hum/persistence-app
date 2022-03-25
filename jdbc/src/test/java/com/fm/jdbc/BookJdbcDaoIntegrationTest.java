package com.fm.jdbc;

import com.fm.jdbc.book.Book;
import com.fm.jdbc.book.BookJdbcInMemoryDao;
import com.fm.jdbc.book.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

// COMPLETED: implement this as an integration test
// both implementations should work
class BookJdbcDaoIntegrationTest {

//        private static final BookRepository bookRepository = new BookJdbcMysqlDao();
    private static final BookRepository bookRepository = new BookJdbcInMemoryDao();

    @BeforeEach
    void setUp() {
        bookRepository.findAll()
                .forEach(book -> bookRepository.delete(book.getId()));
    }

    @Test
    void whenCreate_shouldReturnABook() {
        // given
        Book book = new Book(null, "title", "author", LocalDate.of(2020, 1, 1));

        // when

        Book actualBook = bookRepository.create(book);

        // then
        assertThat(actualBook.getTitle()).isEqualTo(book.getTitle());

        assertAll(
                () -> Assertions.assertNotNull(actualBook.getId(), "ID null => fail"),
                () -> Assertions.assertEquals(actualBook.getAuthor(), book.getAuthor(), "Book author property not persisted"),
                () -> Assertions.assertEquals(book.getTitle(), actualBook.getTitle(), "Book title property not persisted"),
                () -> Assertions.assertEquals(book.getPublishDate(), actualBook.getPublishDate(), "Book publishDate property not persisted")
        );
    }

    @Test
    void findAll() {
        //given - multiple objects in db - @BeforeAll populated db & @Test create()

        //when - you use the findAll method from the repository
        List<Book> foundBooks = bookRepository.findAll();

        //then - you get the list of books
        assertThat(foundBooks).isEmpty();

        //TODO ? test method does not check if the total number of results is the same, or the validity of the data
    }

    @Test
    void findByAuthor() {
        //given - multiple objects in db - @BeforeAll populated db & @Test create()

        //when - you use the findByAuthor method from the repository
        List<Book> foundBooksWithAuthor = bookRepository.findByAuthor("author1");

        //then - you get the list of books, with 1 result in it
        assertAll(
                () -> Assertions.assertEquals(1, foundBooksWithAuthor.size()),
                () -> Assertions.assertEquals("author1", foundBooksWithAuthor.get(0).getAuthor())
        );
    }

    @Test
    void findById() {
        //given - multiple objects in db - @BeforeAll populated db & @Test create()

        //when - you use the findByAuthor method from the repository
        Optional<Book> foundBooksWithId = bookRepository.findById(1L);

        //then - you get the list of books, with 1 result in it
        assertAll(
                () -> Assertions.assertTrue(foundBooksWithId.isPresent()),
                () -> Assertions.assertEquals(1L, foundBooksWithId.get().getId())
        );
    }

    @Test
    void findByTitle() {
        //given - multiple objects in db - @BeforeAll populated db & @Test create()

        //when - you use the findByAuthor method from the repository
        Optional<Book> foundBooksWithTitle = bookRepository.findByTitle("title1");

        //then - you get the list of books, with 1 result in it
        assertAll(
                () -> Assertions.assertTrue(foundBooksWithTitle.isPresent()),
                () -> Assertions.assertEquals("title1", foundBooksWithTitle.get().getTitle())
        );
    }

    @Test
    void update() {
        //given - a fresh book in the db
        Book initialBook = bookRepository
                .create(new Book(null, "initialTitle", "initialAuthor", LocalDate.of(2000, 1, 1)));

        //when - we replace the initial book`s contents with new contents
        Book anotherBook = new Book(null, "updatedTitle", "updatedAuthor", LocalDate.of(2001, 1, 1));
        bookRepository.update(initialBook.getId(), anotherBook);

        //then - we will have the new contents for the old id
        Optional<Book> bookOptional = bookRepository.findById(initialBook.getId());
        Book retrievedBook = bookOptional.get();
        assertAll(
                () -> assertTrue(bookOptional.isPresent()),
                () -> Assertions.assertEquals(anotherBook.getTitle(), retrievedBook.getTitle()),
                () -> Assertions.assertEquals(anotherBook.getAuthor(), retrievedBook.getAuthor()),
                () -> Assertions.assertEquals(anotherBook.getPublishDate(), retrievedBook.getPublishDate())
        );
    }

    @Test
    void delete() {
        //given - an id of an existing book
        Long validId = bookRepository
                .create(new Book(null, "initialTitle", "initialAuthor", LocalDate.of(2000, 1, 1))).getId();

        //when - we try to delete the entry for that id
        bookRepository.delete(validId);

        //then - the db no longer has that entry
        assertFalse(bookRepository.findById(validId).isPresent());
    }

}