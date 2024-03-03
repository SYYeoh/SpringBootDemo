INSERT INTO books (id, title, author, publication_year)
VALUES  (1, 'Sample title 1', 'Sample author 1', 2001),
        (2, 'Sample title 2', 'Sample author 2', 2002),
        (3, 'Sample title 3', 'Sample author 3', 2003),
        (4, 'Sample title 4', 'Sample author 4', 2004),
        (5, 'Sample title 5', 'Sample author 5', 2005);

--select setval('id', COALESCE((select max(id) + 1 from books), 1));