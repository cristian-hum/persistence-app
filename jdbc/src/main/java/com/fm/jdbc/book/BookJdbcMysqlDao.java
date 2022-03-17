package com.fm.jdbc.book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

// TODO: implement this using jdbc prepared statements
// use a mysql database
// use try with resources
public class BookJdbcMysqlDao implements BookRepository {


    @Override
    public Book create(Book book) {
//        try (final Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/database-name",
//                "my-username", "my-secret-pw");) {
//            // use Connection object here
//        } catch (
//                SQLException e) {
//            e.printStackTrace();
//            // exception handling
//        }

        return null;
    }

    @Override
    public List<Book> findAll() {
        return null;
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return null;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        return Optional.empty();
    }

    @Override
    public Book update(Long id, Book book) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
