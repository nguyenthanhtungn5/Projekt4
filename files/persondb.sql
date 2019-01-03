CREATE TABLE IF NOT EXISTS projekt4.person(
  id int NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  jahres_lohn varchar(255) NOT NULL,
  kontakt_daten varchar(255) NOT NULL,
  skills varchar(255) NOT NULL,
  PRIMARY KEY (id)
);
INSERT INTO projekt4.person (name, jahres_lohn, kontakt_daten, skills)
VALUES ('Sammy Shark', '100.000$', 'sami@example.com', 'PHP,Java');

INSERT INTO projekt4.person (name, jahres_lohn, kontakt_daten, skills)
VALUES ('Robert Breus', '90.000$', 'Robert@example.com', 'Entwickler, BigData');

INSERT INTO projekt4.person(name, jahres_lohn, kontakt_daten, skills)
VALUES ('Tom Nguyen', '80.000$', 'tung@example.com', 'C++,Java, Python');
