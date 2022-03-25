package com.fm.jdbc.book;

import com.fm.jdbc.util.ApplicationProperties;
import com.fm.jdbc.util.JdbcProperties;
import com.fm.jdbc.util.PropertiesLoader;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//COMPLETED: implement this using jdbc prepared statements
// use a mysql database
// use try with resources
public class BookJdbcMysqlDao implements BookRepository {

    private final ApplicationProperties properties = PropertiesLoader.loadProperties();
    private final JdbcProperties jdbcProperties = properties.getJdbcProperties();

    private static final String updateString = "UPDATE books title=?, author=?, publishDate=? WHERE id=?";
    private static final String deleteString = "DELETE FROM books WHERE id=?";
    private static final String findAllString = "SELECT * FROM books";
    private static final String findByString = "SELECT * FROM books WHERE ?=?";

    @Override
    public Book create(Book book) {
        String INSERT_BOOK = "INSERT INTO books SET title=?, author=?, publishDate=?";

        try (Connection connection = DriverManager.getConnection(jdbcProperties.getUrl(), jdbcProperties.getUsername(), jdbcProperties.getPassword());
             PreparedStatement statement = connection.prepareStatement(INSERT_BOOK, Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setObject(3, book.getPublishDate());

            int result = statement.executeUpdate();

            if (result == 0) {
                throw new SQLException("Creating book failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException(("Creating book failed, no ID obtained!"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return book;
    }

    @Override
    public List<Book> findAll() {
        List<Book> result = new ArrayList<>();
        try (final Connection connection = DriverManager.getConnection(jdbcProperties.getUrl(), jdbcProperties.getUsername(), jdbcProperties.getPassword());
             PreparedStatement findAllStatement = connection.prepareStatement(findAllString);
        ) {
            if (connection != null) {
                ResultSet resultSet = findAllStatement.executeQuery();

                if (resultSet == null) {
                    throw new SQLException("No records found in DB!");
                }

                while (resultSet.next()) {
                    Book book = getBook(resultSet);
                    result.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Book> findByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        try (final Connection connection = DriverManager.getConnection(jdbcProperties.getUrl(), jdbcProperties.getUsername(), jdbcProperties.getPassword());
             PreparedStatement statement = connection.prepareStatement(findByString);
        ) {
            if (connection != null) {
                statement.setString(1, "author");
                statement.setString(2, author);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet == null) {
                    throw new SQLException("No records found in DB for the author " + author + "!");
                }

                while (resultSet.next()) {
                    Book book = getBook(resultSet);
                    result.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Optional<Book> findById(Long id) {
        Optional<Book> result = null;
        try (final Connection connection = DriverManager.getConnection(jdbcProperties.getUrl(), jdbcProperties.getUsername(), jdbcProperties.getPassword());
             PreparedStatement statement = connection.prepareStatement(findByString);
        ) {
            if (connection != null) {
                statement.setString(1, "id");
                statement.setLong(2, id);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet == null) {
                    throw new SQLException("No records found in DB for the id " + id + "!");
                }

                if (resultSet.next()) {
                    result = Optional.ofNullable(getBook(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        Optional<Book> result = null;
        try (final Connection connection = DriverManager.getConnection(jdbcProperties.getUrl(), jdbcProperties.getUsername(), jdbcProperties.getPassword());
             PreparedStatement statement = connection.prepareStatement(findByString);
        ) {
            if (connection != null) {
                statement.setString(1, "title");
                statement.setString(2, title);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet == null) {
                    throw new SQLException("No records found in DB for the title " + title + "!");
                }

                if (resultSet.next()) {
                    result = Optional.ofNullable(getBook(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Book update(Long id, Book book) {
        try (final Connection connection = DriverManager.getConnection(jdbcProperties.getUrl(), jdbcProperties.getUsername(), jdbcProperties.getPassword());
             PreparedStatement statement = connection.prepareStatement(updateString);
        ) {
            if (connection != null) {
                statement.setString(1, book.getTitle());
                statement.setString(2, book.getAuthor());
                statement.setObject(3, book.getPublishDate());
                statement.setLong(4, id);

                //TODO validate if using SQL to send the error is better that checking if the user exists and then updating
                int result = statement.executeUpdate();

                if (result == 0) {
                    throw new SQLException("Updating user failed, no rows affected.");
                }

                book.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public void delete(Long id) {
        try (final Connection connection = DriverManager.getConnection(jdbcProperties.getUrl(), jdbcProperties.getUsername(), jdbcProperties.getPassword());
             PreparedStatement statement = connection.prepareStatement(deleteString);
        ) {
            if (connection != null) {
                statement.setLong(1, id);

                int result = statement.executeUpdate();

                if (result == 0) {
                    throw new SQLException("Deleting user failed, no rows affected.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Book getBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String title = resultSet.getString("title");
        String author = resultSet.getString("author");
        LocalDate publishDate = resultSet.getDate("publishDate").toLocalDate();

        return new Book(id, title, author, publishDate);
    }
}
