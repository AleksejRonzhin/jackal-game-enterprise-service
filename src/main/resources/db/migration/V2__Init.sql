INSERT INTO permissions (permission) VALUES
('USER'),
('ADMIN'),
('UNBLOCKED');

INSERT INTO games (id, title, client_address, service_address) VALUES
(0, 'Jackal', 'http://localhost:3000/', 'http://localhost:8030/' );

INSERT INTO game_modes (id, title, game_id, min_player_number, max_player_number) VALUES
(0, 'DEFAULT', 0, 4, 4);