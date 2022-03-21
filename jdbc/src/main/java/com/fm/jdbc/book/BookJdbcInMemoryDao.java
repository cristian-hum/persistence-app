package com.fm.jdbc.book;

import java.util.*;
import java.util.stream.Collectors;

// TODO: implement this using jdbc prepared statements
// use a map as an in-memory database
public class BookJdbcInMemoryDao implements BookRepository {

    private static Map<Long, Book> db = new HashMap<>();

    @Override
    public Book create(Book book) {
        Long newId = Long.valueOf(db.size());
        book.setId(newId);
        db.put(newId, book);
        return book;
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return db.values()
                .stream()
                .filter(book -> book.getAuthor() == author)
                .toList();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return db.values()
                .stream()
                .filter(book -> book.getId() == id)
                .findFirst();
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        return db.values()
                .stream()
                .filter(book -> book.getTitle() == title)
                .findFirst();
    }

    @Override
    public Book update(Long id, Book book) {
        db.replace(id, book);
        return book;
    }

    @Override
    public void delete(Long id) {
        db.remove(id);
    }

}
