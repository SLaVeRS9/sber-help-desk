insert into users (id, name, password, role) values ('smirnovav', 'Smirnov Alexey', 'password1', 'ADMIN');
insert into users (id, name, password, role) values ('ivanovap', 'Ivanov Andrey', 'password2', 'MANAGER');
insert into users (id, name, password, role) values ('bakinasv', 'Bakina Svetlana', 'password3', 'MANAGER');
insert into users (id, name, password, role) values ('egorovamr', 'Egorova Marina', 'password4', 'USER');

insert into tickets (requester_id, manager_id, description, creation_time, last_status_change_time, commentary, status) values ('egorovamr', 'bakinasv', 'ticket 1', '2024-01-26 12:52:00', '2024-01-26 12:52:00', 'not filled yet', 'NEW');
insert into tickets (requester_id, manager_id, description, creation_time, last_status_change_time, commentary, status) values ('egorovamr', 'bakinasv', 'ticket 2', '2024-01-27 12:30:00', '2024-01-28 11:00:00', 'not filled yet', 'INPROGRESS');
insert into tickets (requester_id, manager_id, description, creation_time, last_status_change_time, commentary, status) values ('egorovamr', 'ivanovap', 'ticket 3', '2024-01-27 12:31:00', '2024-01-28 08:45:26', 'Resolved', 'CLOSED');
