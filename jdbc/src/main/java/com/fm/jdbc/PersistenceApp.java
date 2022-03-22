package com.fm.jdbc;

import com.fm.jdbc.book.Book;
import com.fm.jdbc.book.BookJdbcInMemoryDao;
import com.fm.jdbc.book.BookJdbcMysqlDao;
import com.fm.jdbc.book.BookRepository;
import com.fm.jdbc.util.ApplicationProperties;
import com.fm.jdbc.util.PropertiesLoader;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.*;
import java.time.LocalDate;

public class PersistenceApp {
    public static void main(String[] args) {
        BookJdbcMysqlDao bookJdbcMysqlDao = new BookJdbcMysqlDao();
        BookJdbcInMemoryDao bookJdbcInMemoryDao = new BookJdbcInMemoryDao();

        System.out.println("With Mysql DAO");
        useJdbcDao(bookJdbcMysqlDao);
        System.out.println("\nWith in memory DAO");
        useJdbcDao(bookJdbcInMemoryDao);
    }

    private static void useJdbcDao(BookRepository bookJdbcDao) {
        if (bookJdbcDao.getClass().isAssignableFrom(BookJdbcMysqlDao.class)) {
            initialiseDB();
        } else {
            bookJdbcDao.create(new Book(0L, "", "", null));
            bookJdbcDao.create(new Book(1L, "LOTR", "JRR Tolkien", null));
            bookJdbcDao.create(new Book(2L, "The caves of steel", "Asimov", null));
        }

        System.out.println("create");
        Book book = new Book(null, "The Hobbit", "JRR Tolkien", LocalDate.of(1937, 9, 21));
        System.out.println(bookJdbcDao.create(book).toString());

        System.out.println("findAll");
        bookJdbcDao.findAll().forEach(book1 -> System.out.println(book1.toString()));

        System.out.println("findByAuthor - JRR Tolkien");
        bookJdbcDao.findByAuthor("JRR Tolkien").forEach(book1 -> System.out.println(book1.toString()));

        System.out.println("findById - 1");
        bookJdbcDao.findById(1L).ifPresent(book1 -> System.out.println(book1.toString()));

        System.out.println("findByTitle - The Hobbit");
        bookJdbcDao.findByTitle("The Hobbit").ifPresent(book1 -> System.out.println(book1.toString()));

        System.out.println("update");
        System.out.println("was");
        bookJdbcDao.findById(1L).ifPresent(book1 -> System.out.println(book1.toString()));
        System.out.println("with new book");
        System.out.println(book.toString());
        System.out.println("becomes");
        System.out.println(bookJdbcDao.update(1L, book).toString());

        System.out.println("delete - id 2");
        System.out.println("was");
        bookJdbcDao.findAll().forEach(book1 -> System.out.println(book1.toString()));
        bookJdbcDao.delete(2L);
        System.out.println("becomes");
        bookJdbcDao.findAll().forEach(book1 -> System.out.println(book1.toString()));
    }

    //MYSQL drops and creates schema, builds books table and adds 2 examples
    private static void initialiseDB() {
        ApplicationProperties ap = PropertiesLoader.loadProperties();

        try (final Connection connection = DriverManager.getConnection(ap.getUrl(), ap.getUsername(), ap.getPassword())
        ) {
            if (connection != null) {
                System.out.println("Connection established to mysql DB, initialising using schema.sql");
                ScriptRunner sr = new ScriptRunner(connection);
                Reader reader = new BufferedReader(new FileReader("jdbc/src/main/resources/schema.sql"));
                sr.runScript(reader);
            }
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
