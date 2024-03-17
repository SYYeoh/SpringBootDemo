CREATE TABLE books
(
    id       SERIAL          PRIMARY KEY,
    title   varchar(30)     NOT NULL,
    author  varchar(30)     NOT NULL,
    publication_year integer    NOT NULL
);