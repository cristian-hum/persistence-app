-- TODO: save all db objects here
CREATE TABLE book(
                          ID BIGINT NOT NULL AUTO_INCREMENT,
                          title VARCHAR(255),
                          author VARCHAR(255),
                          publishDate date,
                          PRIMARY KEY (`ID`)
);

insert into book values (1, "LOTR", "JRR Tolkien", )
