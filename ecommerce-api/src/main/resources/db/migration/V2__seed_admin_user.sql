INSERT INTO users (
    name,
    email,
    password_hash,
    phone,
    role,
    active,
    created_at,
    updated_at
) VALUES (
    'System Administrator',
    'admin@uberweets.local',
    '$2a$10$dYk8D9YQviczBL/5gm/cJe2q8gX4158DeO/fBVY5V1Z6jyQWIvFKm',
    '11999999999',
    'ADMIN',
    TRUE,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

