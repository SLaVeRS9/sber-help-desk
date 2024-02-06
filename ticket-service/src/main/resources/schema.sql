/*Создание таблицы users*/
CREATE TABLE IF NOT EXISTS users (
    user_id varchar(50),
    name varchar(50) NOT NULL,
    password varchar(100) NOT NULL,
    role varchar(20) NOT NULL
);

/*Создание таблицы tickets*/
CREATE TABLE IF NOT EXISTS tickets (
    title varchar(100),
    requester_id varchar(50),
    manager_id varchar(50),
    description varchar(50) NOT NULL,
    created_at datetime NOT NULL,
    status_updated_at varchar(20) NOT NULL,
    status varchar(20)
);