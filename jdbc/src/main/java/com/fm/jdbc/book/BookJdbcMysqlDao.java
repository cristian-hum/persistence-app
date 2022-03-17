package com.fm.jdbc.book;

import com.fm.jdbc.util.ApplicationProperties;
import com.fm.jdbc.util.PropertiesLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

// TODO: implement this using jdbc prepared statements
// use a mysql database
// use try with resources
public class BookJdbcMysqlDao implements BookRepository {

    ApplicationProperties ap = PropertiesLoader.loadProperties();

    @Override
    public Book create(Book book) {
        try (final Connection connection = DriverManager.getConnection(ap.getUrl(), ap.getUsername(), ap.getPassword());) {
            // use Connection object here
            if (connection != null) {

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
