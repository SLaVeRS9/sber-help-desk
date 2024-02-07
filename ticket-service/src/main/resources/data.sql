/*Наполнение таблицы users*/
insert into users (id, name, password, role) values ('smirnovav', 'Smirnov Alexey', '$2a$12$HHSybys2IZOGkIev4I0nuOYQkNvtjVamreLkz8iRazn0jHABlrsT6', 'ADMIN');
insert into users (id, name, password, role) values ('ivanovap', 'Ivanov Andrey', '$2a$10$HI1EpUmJ63gTBIPc7RwhlujG70u2eiMIKp6Zq8aIlKbyfQZ9CAmsW', 'MANAGER');
insert into users (id, name, password, role) values ('bakinasv', 'Bakina Svetlana', '$2a$10$BnmEUVOJ9Ly9WiGlBsu3dOP.0nDgdC0DZVZ9yhlwY6ARQgX4NTQVy', 'MANAGER');
insert into users (id, name, password, role) values ('egorovamr', 'Egorova Marina', '$2a$10$Lpgs25qu/yihLyjC7/.Bbehu9OgmoNGnZ32SFggfqjpqNWrVG.ugWs', 'USER');

/*Наполнение таблицы tickets*/
insert into tickets (title, requester_id, manager_id, description, created_at, status_updated_at, status) values ('title1', 'egorovamr', 'bakinasv', 'ticket 1', '2024-01-26 12:52:00', '2024-01-26 12:52:00', 'NEW');
insert into tickets (title, requester_id, manager_id, description, created_at, status_updated_at, status) values ('title2','egorovamr', 'bakinasv', 'ticket 2', '2024-01-27 12:30:00', '2024-01-28 11:00:00', 'IN_PROGRESS');
insert into tickets (title, requester_id, manager_id, description, created_at, status_updated_at, status) values ('title3','egorovamr', 'ivanovap', 'ticket 3', '2024-01-27 12:31:00', '2024-01-28 08:45:26', 'CLOSED');
