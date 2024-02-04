/*Создание таблицы users*/
CREATE TABLE IF NOT EXISTS users (
    id varchar(50),
    name varchar(50) NOT NULL,
    password varchar(100) NOT NULL,
    role varchar(20) NOT NULL
);

/*Создание таблицы tickets*/
CREATE TABLE IF NOT EXISTS tickets (
    requester_id varchar(50),
    manager_id varchar(50),
    description varchar(50) NOT NULL,
    creation_time datetime NOT NULL,
    last_status_change_time varchar(20) NOT NULL,
    commentary varchar(200),
    status varchar(20)
);