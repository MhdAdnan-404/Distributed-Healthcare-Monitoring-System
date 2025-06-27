
CREATE TABLE doctor (
    id INTEGER PRIMARY KEY,
    name VARCHAR(255),
    speciality VARCHAR(50),
    approval_status VARCHAR(50),
    FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE
);

ALTER TABLE patient
ALTER COLUMN date_of_birth TYPE DATE
USING date_of_birth::DATE;


ALTER TABLE users
ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE,
ADD COLUMN is_activated BOOLEAN DEFAULT FALSE;


CREATE TABLE contact_info (
    id SERIAL PRIMARY KEY,
    userid INTEGER NOT NULL UNIQUE,
    CONSTRAINT fk_contact_info_user FOREIGN KEY (userid) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE contact_info_contacts (
    contact_info_id INTEGER NOT NULL,
    contact_type VARCHAR(50) NOT NULL,
    contact_value VARCHAR(255) NOT NULL,
    CONSTRAINT fk_contact_info_contacts FOREIGN KEY (contact_info_id) REFERENCES contact_info(id) ON DELETE CASCADE,
    PRIMARY KEY (contact_info_id, contact_type)
);

CREATE TABLE address (
    id SERIAL PRIMARY KEY,
    country VARCHAR(100),
    city VARCHAR(100),
    street_name VARCHAR(100),
    street_number VARCHAR(50),
    label VARCHAR(50),
    userid  INTEGER NOT NULL,
    CONSTRAINT fk_address_user FOREIGN KEY (userid) REFERENCES users(id) ON DELETE CASCADE
);


CREATE TABLE activation_token (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    userid  INTEGER NOT NULL UNIQUE,
    used BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_activation_user FOREIGN KEY (userid) REFERENCES users(id) ON DELETE CASCADE
);
