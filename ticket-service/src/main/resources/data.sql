/*Наполнение таблицы users*/
insert into users (user_id, name, password, role) values ('smirnovav', 'Smirnov Alexey', 'password1', 'ADMIN');
insert into users (user_id, name, password, role) values ('ivanovap', 'Ivanov Andrey', 'password2', 'MANAGER');
insert into users (user_id, name, password, role) values ('bakinasv', 'Bakina Svetlana', 'password3', 'MANAGER');
insert into users (user_id, name, password, role) values ('egorovamr', 'Egorova Marina', 'password4', 'USER');

/*Наполнение таблицы tickets*/
insert into tickets (title, requester_id, manager_id, description, created_at, status_updated_at, status) values ('title1', 'egorovamr', 'bakinasv', 'ticket 1', '2024-01-26 12:52:00', '2024-01-26 12:52:00', 'NEW');
insert into tickets (title, requester_id, manager_id, description, created_at, status_updated_at, status) values ('title2','egorovamr', 'bakinasv', 'ticket 2', '2024-01-27 12:30:00', '2024-01-28 11:00:00', 'IN_PROGRESS');
insert into tickets (title, requester_id, manager_id, description, created_at, status_updated_at, status) values ('title3','egorovamr', 'ivanovap', 'ticket 3', '2024-01-27 12:31:00', '2024-01-28 08:45:26', 'CLOSED');
