-- COMPLETED: save all db objects here

DROP SCHEMA book_list;

CREATE SCHEMA IF NOT EXISTS book_list;
USE book_list;

CREATE TABLE books(
                      ID BIGINT NOT NULL AUTO_INCREMENT,
                      title VARCHAR(255),
                      author VARCHAR(255),
                      publishDate date,
                      PRIMARY KEY (`ID`)
);

insert into books values (1, "LOTR", "JRR Tolkien", null);
insert into books values (2, "The caves of steel", "Asimov", null);
