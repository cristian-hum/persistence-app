package com.fm.jdbc.book;

import java.util.*;

// TODO: implement using a map as an in-memory database
public class BookJdbcInMemoryDao implements BookRepository {

    private static Map<Long, Book> db = new HashMap<>();

    @Override
    public Book create(Book book) {
        Long newId = (long) db.size();
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
                .filter(book -> book.getAuthor().equals(author))
                .toList();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return db.values()
                .stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();
    }

    // TODO: check multiple books with same title
    @Override
    public Optional<Book> findByTitle(String title) {
        return db.values()
                .stream()
                .filter(book -> book.getTitle().equals(title))
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
