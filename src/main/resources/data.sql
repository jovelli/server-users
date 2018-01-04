--Sequences are created at @SequenceGenerator inside User and Post beans
insert into user (id, name, birth_date, national_id) values (USERSEQUENCE.nextval, 'Juan', sysdate(), '321.123.321-12');
insert into user (id, name, birth_date, national_id) values (USERSEQUENCE.nextval, 'Mario', sysdate(), '543.234.645-13');
insert into user (id, name, birth_date, national_id) values (USERSEQUENCE.nextval, 'Velasquez', sysdate(), '654.432.876-42');
insert into user (id, name, birth_date, national_id) values (USERSEQUENCE.nextval, 'Pablo', sysdate(), '392.858.301-54');
insert into user (id, name, birth_date, national_id) values (USERSEQUENCE.nextval, 'Maria', sysdate(), '987.253.045-63');
insert into user (id, name, birth_date, national_id) values (USERSEQUENCE.nextval, 'Antonieta', sysdate(), '963.629.495-11');

insert into post (id, message, created_date, user_id) values (POSTSEQUENCE.nextval, 'This is First post for user 1', sysdate(), 1);
insert into post (id, message, created_date, user_id) values (POSTSEQUENCE.nextval, 'This is Second post for user 1', sysdate(), 1);
insert into post (id, message, created_date, user_id) values (POSTSEQUENCE.nextval, 'This is First post for user 2', sysdate(), 2);
insert into post (id, message, created_date, user_id) values (POSTSEQUENCE.nextval, 'This is Second post for user 2', sysdate(), 2);
insert into post (id, message, created_date, user_id) values (POSTSEQUENCE.nextval, 'This is First post for user 3', sysdate(), 3);