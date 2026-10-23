INSERT INTO auteurs (nom, prenom, nationalite) VALUES
                                                   ('Hugo', 'Victor', 'Française'),
                                                   ('Rowling', 'J.K.', 'Britannique'),
                                                   ('Camus', 'Albert', 'Française'),
                                                   ('Tolkien', 'J.R.R.', 'Britannique'),
                                                   ('Asimov', 'Isaac', 'Américaine');

INSERT INTO livres (auteur_id, titre, isbn, prix, anneePublication, stock) VALUES
                                                                               (1, 'Les Misérables', '978-2-07-040850-4', 12.50, 1862, 5),
                                                                               (1, 'Notre-Dame de Paris', '978-2-07-041239-6', 9.90, 1831, 3),
                                                                               (2, 'Harry Potter à l''école des sorciers', '978-2-07-054127-0', 8.90, 1997, 10),
                                                                               (2, 'Harry Potter et la Chambre des secrets', '978-2-07-054128-7', 8.90, 1998, 7),
                                                                               (3, 'L''Étranger', '978-2-07-036024-8', 6.50, 1942, 4),
                                                                               (3, 'La Peste', '978-2-07-036042-2', 7.80, 1947, 6),
                                                                               (4, 'Le Seigneur des Anneaux', '978-2-267-02700-4', 25.00, 1954, 3),
                                                                               (4, 'Le Hobbit', '978-2-267-02600-7', 15.00, 1937, 0);

INSERT INTO emprunts (livre_id, emprunteur, emailEmprunteur, dateEmprunt, dateRetourPrevue, dateRetourEffective, rendu) VALUES
                                                                                                                            (1, 'Marie Dupont', 'marie.dupont@email.com', '2026-09-01', '2026-09-15', '2026-09-10', true),
                                                                                                                            (2, 'Jean Martin', 'jean.martin@email.com', '2026-09-05', '2026-09-19', null, false),
                                                                                                                            (3, 'Sophie Bernard', 'sophie.bernard@email.com', '2026-09-20', '2026-10-04', null, false);