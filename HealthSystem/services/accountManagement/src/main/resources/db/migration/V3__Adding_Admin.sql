-- Create admin user
INSERT INTO users (username, password, role, is_deleted, is_activated)
VALUES (
    'admin',
    '$2a$10$PqLE2i5GPKJgaWod/CtAveLr5Tf47fpyU0yPxl/YxutGA1lwTvIwy', -- password: Admin123!
    'ADMIN',
    FALSE,
    TRUE
);

INSERT INTO contact_info (userid)
SELECT id FROM users WHERE username = 'admin';

INSERT INTO contact_info_contacts (contact_info_id, contact_type, contact_value)
SELECT ci.id, 'EMAIL', 'admin@example.com'
FROM contact_info ci
JOIN users u ON ci.userid = u.id
WHERE u.username = 'admin';


INSERT INTO address (country, city, street_name, street_number, label, userid)
SELECT 'UAE', 'Dubai', 'Sheikh Zayed Road', '101', 'Admin Office', id FROM users WHERE username = 'admin';
