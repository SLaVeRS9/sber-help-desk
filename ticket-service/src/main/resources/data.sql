/*Наполнение таблицы users*/
insert into users (id, name, password, role, date_of_birth) values ('smirnovav', 'Smirnov Alexey', '$2a$12$HHSybys2IZOGkIev4I0nuOYQkNvtjVamreLkz8iRazn0jHABlrsT6', 'ADMIN', '1980-01-26');
insert into users (id, name, password, role, date_of_birth) values ('ivanovap', 'Ivanov Andrey', '$2a$10$HI1EpUmJ63gTBIPc7RwhlujG70u2eiMIKp6Zq8aIlKbyfQZ9CAmsW', 'MANAGER', '1986-02-22');
insert into users (id, name, password, role, date_of_birth) values ('bakinasv', 'Bakina Svetlana', '$2a$10$BnmEUVOJ9Ly9WiGlBsu3dOP.0nDgdC0DZVZ9yhlwY6ARQgX4NTQVy', 'MANAGER', '1990-05-21');
insert into users (id, name, password, role, date_of_birth) values ('egorovamr', 'Egorova Marina', '$2a$12$9QtD97Z9A8cqiiI01/yNlO/WWDQHqi6FFCNy1/vi1Tl8yHzxg.MaK', 'USER', '1993-08-11');

/*Наполнение таблицы tickets*/
insert into tickets (title, ticket_code, requester_id, manager_id, description, created_at, status_updated_at, status) values ('title1', 'SHD-', 'egorovamr', 'bakinasv', 'ticket 1 very very very very very long description', '2024-01-26 12:52:00', '2024-01-26 12:52:00', 'NEW');
insert into tickets (title, ticket_code, requester_id, manager_id, description, created_at, status_updated_at, status) values ('title2', 'SHD-','ivanovap', 'bakinasv', 'ticket 2 very very very very very long description', '2024-01-27 12:30:00', '2024-01-28 11:00:00', 'IN_PROGRESS');
insert into tickets (title, ticket_code, requester_id, manager_id, description, created_at, status_updated_at, status) values ('title3', 'SHD-','smirnovav', 'bakinasv', 'ticket 3 very very very very very long description', '2024-01-27 12:30:00', '2024-01-28 11:00:00', 'IN_PROGRESS');
insert into tickets (title, ticket_code, requester_id, manager_id, description, created_at, status_updated_at, status) values ('title4', 'SHD-','egorovamr', 'ivanovap', 'ticket 4 very very very very very long description', '2024-01-27 12:31:00', '2024-01-28 08:45:26', 'CLOSED');
insert into tickets (title, ticket_code, requester_id, manager_id, description, created_at, status_updated_at, status) values ('title5', 'SHD-','smirnovav', 'bakinasv', 'ticket 5 very very very very very long description', '2024-01-27 12:30:00', '2024-01-28 11:00:00', 'CLOSED');
