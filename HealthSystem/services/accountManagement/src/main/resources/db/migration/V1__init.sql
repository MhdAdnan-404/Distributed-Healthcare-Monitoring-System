CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    language VARCHAR(50)
);



CREATE TABLE patient (
    id INTEGER PRIMARY KEY,
    name VARCHAR(255),
    date_of_birth DATE,
    allergies VARCHAR(255),
    FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE
);
