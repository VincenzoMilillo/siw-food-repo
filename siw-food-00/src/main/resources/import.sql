insert into cuoco (id, name, surname, birthdate, photo) values(nextval('cuoco_seq'), 'Bruno', 'Barbieri', '1958-08-25', 'barbieri.png');
insert into cuoco (id, name, surname, birthdate, photo) values(nextval('cuoco_seq'), 'Antonino', 'Cannavacciuolo', '1990-10-13', 'canna.png');
insert into cuoco (id, name, surname, birthdate, photo) values(nextval('cuoco_seq'), 'Ernst', 'Knam', '1963-03-08', 'er.png');

insert into ricetta (id, name, photo, description, cuoco_id) values(nextval('ricetta_seq'), 'Carbonara', 'carbonara.png', 'pasta con uova', '1');
insert into ricetta (id, name, photo, description, cuoco_id) values(nextval('ricetta_seq'), 'Amatriciana', 'amatriciana.png', 'pasta con pomodoro', '51');
insert into ricetta (id, name, photo, description, cuoco_id) values(nextval('ricetta_seq'), 'Cacio e Pepe', 'cacioepepe.png', 'pasta con il formaggio', '101');

insert into ingrediente(id, name, season, photo, description) values(nextval('ingrediente_seq'), 'Patata', 'Tutte le stagioni', 'patata.png', 'La patata è un tubero');
INSERT INTO ingrediente(id, name, season, photo, description) VALUES (nextval('ingrediente_seq'), 'Carota', 'Inverno', 'carota.png', 'La carota è una radice');
INSERT INTO ingrediente(id, name, season, photo, description) VALUES (nextval('ingrediente_seq'), 'Cipolla', 'Inverno', 'cipolla.png', 'La cipolla è un bulbo');
INSERT INTO ingrediente(id, name, season, photo, description) VALUES (nextval('ingrediente_seq'), 'Pomodoro', 'Estate', 'pomodoro.png', 'Il pomodoro è un frutto');
INSERT INTO ingrediente(id, name, season, photo, description) VALUES (nextval('ingrediente_seq'), 'Spinaci', 'Primavera', 'spinaci.png', 'Gli spinaci sono una verdura a foglia verde');
INSERT INTO ingrediente(id, name, season, photo, description) VALUES (nextval('ingrediente_seq'), 'Mela', 'Autunno', 'mela.png', 'La mela è un frutto');
INSERT INTO ingrediente(id, name, season, photo, description) VALUES (nextval('ingrediente_seq'), 'Banana', 'Tutte le stagioni', 'banana.png', 'La banana è un frutto');
INSERT INTO ingrediente(id, name, season, photo, description) VALUES (nextval('ingrediente_seq'), 'Limone', 'Tutte le stagioni', 'limone.png', 'Il limone è un agrume');
INSERT INTO ingrediente(id, name, season, photo, description) VALUES (nextval('ingrediente_seq'), 'Peperone', 'Estate', 'peperone.png', 'Il peperone è un ortaggio');
INSERT INTO ingrediente(id, name, season, photo, description) VALUES (nextval('ingrediente_seq'), 'Zucchina', 'Estate', 'zucchina.png', 'La zucchina è un ortaggio');
INSERT INTO ingrediente(id, name, season, photo, description) VALUES (nextval('ingrediente_seq'), 'Melanzana', 'Estate', 'melanzana.png', 'La melanzana è un ortaggio');
