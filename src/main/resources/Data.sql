
 -- utente superadmin
insert into utente (id, nome, data_nascita,cognome,is_disattivato, email, password, ruolo) values
(100, 'admin', '2003-12-1','admin', 0,'super-admin@mail.it', '4194d1706ed1f408d5e02d672777019f4d5385c766a8c6ca8acba3167d36a7b9', 'SUPER_ADMIN');


-- utente admin
insert into utente (id, nome, data_nascita,cognome,is_disattivato, email, password, ruolo) values
    (200, 'admin', '2003-12-1','admin', 0,'admin@mail.it', '4194d1706ed1f408d5e02d672777019f4d5385c766a8c6ca8acba3167d36a7b9', 'ADMIN');


-- utente base
insert into utente (id, nome, data_nascita,cognome,is_disattivato, email, password, ruolo) values
    (300, 'utente', '2003-12-1','admin', 0,'utente@mail.it', '4194d1706ed1f408d5e02d672777019f4d5385c766a8c6ca8acba3167d36a7b9', 'CLIENTE');


 insert into utente (id, nome, data_nascita,cognome,is_disattivato, email, password, ruolo) values
     (301, 'utente', '2003-12-1','admin', 0,'utente2@mail.it', '4194d1706ed1f408d5e02d672777019f4d5385c766a8c6ca8acba3167d36a7b9', 'CLIENTE');
