insert into ricetta (creatore_id, id, nome, immagine, descrizione) values('1', nextval('ricetta_seq'), 'Carbonara', 'https://foodwhirl.com/wp-content/uploads/2017/02/spaghetti-carbonara-insta-768x762.jpg', 'pasta con uova');
insert into ricetta (creatore_id, id, nome, immagine, descrizione) values('51', nextval('ricetta_seq'), 'Amatriciana', 'https://www.ntacalabria.it/wp-content/uploads/2015/10/piatto_di_vera_amatriciana-1024x681.jpg', 'pasta con pomodoro');
insert into ricetta (creatore_id, id, nome, immagine, descrizione) values('101', nextval('ricetta_seq'), 'Cacio e Pepe', 'https://i0.wp.com/www.thegrguide.com/wp-content/uploads/2019/11/Cacio-e-Pepe-Dinner.jpg?ssl=1', 'pasta con il formaggio');

insert into cuoco (id, nome, cognome, immagine, nascita) values(nextval('cuoco_seq'), 'Bruno', 'Barbieri', 'https://www.occhionotizie.it/wp-content/uploads/2019/01/Bruno-Barbieri.jpg','1958-08-25');
insert into cuoco (id, nome, cognome, immagine, nascita) values(nextval('cuoco_seq'), 'Antonino', 'Cannavacciuolo', 'https://www.salepepe.it/cdn-cgi/image/width=992/files/2014/04/FOXLIFE_CucineDaIncubo_seconda-stagione_Cannavacciuolo-1.jpg','1990-10-13');
insert into cuoco (id, nome, cognome, immagine, nascita) values(nextval('cuoco_seq'), 'Vincenzo', 'Milillo', 'https://th.bing.com/th/id/OIP.EfNjLZJsFSubH_ESUaqUVQHaHa?rs=1&pid=ImgDetMain','2002-09-08');

insert into ingrediente(id, nome, stagionalita, immagine, descrizione) values(nextval('ingrediente_seq'), 'Patata', 'Tutte le stagioni', 'https://ilfattoalimentare.it/wp-content/uploads/2015/06/potatoes-411975_640.jpg', 'La patata è un tubero');
INSERT INTO ingrediente(id, nome, stagionalita, immagine, descrizione) VALUES (nextval('ingrediente_seq'), 'Carota', 'Inverno', 'https://th.bing.com/th/id/OIP.KJ0phz5cuVs2gAzrovT2WQHaJ6?rs=1&pid=ImgDetMain', 'La carota è una radice');
INSERT INTO ingrediente(id, nome, stagionalita, immagine, descrizione) VALUES (nextval('ingrediente_seq'), 'Cipolla', 'Inverno', 'https://th.bing.com/th/id/OIP.sxcbPJ_NbbKBSQ2wB8h1UgHaE7?rs=1&pid=ImgDetMain', 'La cipolla è un bulbo');
INSERT INTO ingrediente(id, nome, stagionalita, immagine, descrizione) VALUES (nextval('ingrediente_seq'), 'Pomodoro', 'Estate', 'https://th.bing.com/th/id/R.c96dff1a1a6aa36b1ebe993dfb28070d?rik=Mv6h4rtSjJhwLQ&pid=ImgRaw&r=0', 'Il pomodoro è un frutto');
INSERT INTO ingrediente(id, nome, stagionalita, immagine, descrizione) VALUES (nextval('ingrediente_seq'), 'Spinaci', 'Primavera', 'https://www.thegreenrevolution.it/wp-content/uploads/2017/08/04_SETT_17-Semina-spinaci-2-768x768.jpg', 'Gli spinaci sono una verdura a foglia verde');
INSERT INTO ingrediente(id, nome, stagionalita, immagine, descrizione) VALUES (nextval('ingrediente_seq'), 'Mela', 'Autunno', 'https://th.bing.com/th/id/OIP.HUMZCyjUpo6jvmkEDeY49AHaGg?rs=1&pid=ImgDetMain', 'La mela è un frutto');
INSERT INTO ingrediente(id, nome, stagionalita, immagine, descrizione) VALUES (nextval('ingrediente_seq'), 'Banana', 'Tutte le stagioni', 'https://th.bing.com/th/id/OIP.HNzpKaH9VXti3TyckgfhAgHaE9?rs=1&pid=ImgDetMain', 'La banana è un frutto');
INSERT INTO ingrediente(id, nome, stagionalita, immagine, descrizione) VALUES (nextval('ingrediente_seq'), 'Limone', 'Tutte le stagioni', 'https://th.bing.com/th/id/R.20fa068b582be4f4941daa16f90790cd?rik=ljFFmHWpZQQZ6w&pid=ImgRaw&r=0', 'Il limone è un agrume');
INSERT INTO ingrediente(id, nome, stagionalita, immagine, descrizione) VALUES (nextval('ingrediente_seq'), 'Peperone', 'Estate', 'https://static.ohga.it/wp-content/uploads/sites/24/2019/02/peperoni.jpg', 'Il peperone è un ortaggio');
INSERT INTO ingrediente(id, nome, stagionalita, immagine, descrizione) VALUES (nextval('ingrediente_seq'), 'Zucchina', 'Estate', 'https://th.bing.com/th/id/OIP.lrYrpnbnr68aF6-bOWMC2gHaE8?rs=1&pid=ImgDetMain', 'La zucchina è un ortaggio');
INSERT INTO ingrediente(id, nome, stagionalita, immagine, descrizione) VALUES (nextval('ingrediente_seq'), 'Melanzana', 'Estate', 'https://th.bing.com/th/id/OIP.kzqoyoVitfy0dv7v0UgIAwHaE8?rs=1&pid=ImgDetMain', 'La melanzana è un ortaggio');
