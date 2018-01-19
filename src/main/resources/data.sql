--Sequences are created at @SequenceGenerator inside User and Post beans

insert into users (name, BIRTH_DATE, national_id) values ('Juan', current_date(), '321.123.321-12');
insert into users (name, BIRTH_DATE, national_id) values ('Mario', current_date(), '543.234.645-13');
insert into users (name, BIRTH_DATE, national_id) values ('Velasquez', current_date(), '654.432.876-42');
insert into users (name, BIRTH_DATE, national_id) values ('Pablo', current_date(), '392.858.301-54');
insert into users (name, BIRTH_DATE, national_id) values ('Maria', current_date(), '987.253.045-63');
insert into users (name, BIRTH_DATE, national_id) values ('Antonieta', current_date(), '963.629.495-11');

insert into posts (message, created_date, user_id) values ('This is First post for user 1', current_date(), 1);
insert into posts (message, created_date, user_id) values ('This is Second post for user 1', current_date(), 1);
insert into posts (message, created_date, user_id) values ('This is First post for user 2', current_date(), 2);
insert into posts (message, created_date, user_id) values ('This is Second post for user 2', current_date(), 2);
insert into posts (message, created_date, user_id) values ('This is First post for user 3', current_date(), 3);