-- TODO: save all db objects here
CREATE TABLE books(
                          ID BIGINT NOT NULL AUTO_INCREMENT,
                          title VARCHAR(255),
                          author VARCHAR(255),
                          publishDate date,
                          PRIMARY KEY (`ID`)
);

insert into books values (1, "LOTR", "JRR Tolkien", )
