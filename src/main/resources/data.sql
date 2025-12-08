-- ADDRESS
INSERT INTO address_tb (zip_code, street, number, complement, city, state, country, created_at) VALUES
('01001-000', 'Rua das Flores', 123, 'Apto 12', 'São Paulo', 'SP', 'Brasil', CURRENT_TIMESTAMP),
('20040-020', 'Avenida Central', 456, NULL, 'Rio de Janeiro', 'RJ', 'Brasil', CURRENT_TIMESTAMP),
('30110-017', 'Rua Diamantina', 78, 'Casa 2', 'Belo Horizonte', 'MG', 'Brasil', CURRENT_TIMESTAMP),
('80010-200', 'Alameda Silva', 910, NULL, 'Curitiba', 'PR', 'Brasil', CURRENT_TIMESTAMP),
('70040-010', 'Quadra 3 Bloco B', 25, 'Sala 203', 'Brasília', 'DF', 'Brasil', CURRENT_TIMESTAMP),
('88015-200', 'Rua do Comércio', 55, NULL, 'Florianópolis', 'SC', 'Brasil', CURRENT_TIMESTAMP),
('59020-300', 'Avenida Praia Azul', 802, 'Apto 404', 'Natal', 'RN', 'Brasil', CURRENT_TIMESTAMP),
('40020-000', 'Ladeira dos Ventos', 12, 'Fundos', 'Salvador', 'BA', 'Brasil', CURRENT_TIMESTAMP),
('64001-100', 'Rua Piauí', 345, NULL, 'Teresina', 'PI', 'Brasil', CURRENT_TIMESTAMP),
('66010-120', 'Travessa Belém', 76, 'Galpão 1', 'Belém', 'PA', 'Brasil', CURRENT_TIMESTAMP);
-- USERS
INSERT INTO user_tb (
    id, type, name, document, phone, mail_address, password,
    address_id, receive_notifications, created_at, is_admin
) VALUES
-- ADMIN
(default, 'PF', 'Admin Master', '00000000000', '11999990000', 'admin@dogfeliz.com',
'$2a$12$o1OJ.rQ5z8KBWlZlZiIV7ezJbzn8SLQghy9jNQnIh3KeRyTUaPweG', 1, true, NOW(), true),

-- USERS COMUNS
(default, 'PF', 'João Silva', '11111111111', '11988887777', 'joao@gmail.com',
'$2a$10$12zv7XVkbSD0uz56ZfIu2Oo7vpH.Hq1D9T8p45r8oC9ceakUtgVXS', 2, true, NOW(), false),

(default, 'PJ', 'Maria Santos', '22222222222', '11977776666', 'maria@gmail.com',
'$2a$10$sf2E3qk4u1EzO4k0qQpp0e7r2UedsbMfGrZj9m854eZpgpQwOxf1e', 3, true, NOW(), false),

(default, 'PF', 'Carlos Almeida', '33333333333', '11966665555', 'carlos@gmail.com',
'$2a$10$7bIzUc5xLKk/XWYtkpo66.mAANhU2gRe7.a/Tqn8ThM9x65M754X2', 4, true, NOW(), false),

(default, 'PJ', 'Ana Oliveira', '44444444444', '11955554444', 'ana@gmail.com',
'$2a$10$H6xdmgrVV6U2t6k7FEY48uSAJoMoLmYV0rVikSWK1FPmvR06lpnke', 5, true, NOW(), false),

(default, 'PF', 'Rafael Torres', '55555555555', '11944443333', 'rafael@gmail.com',
'$2a$10$G3JygVJ2UO0iU1O9nbNereg2Yp0yOFK8bzOhk9F6c4QOjPmYx8gvW', 6, true, NOW(), false),

(default, 'PF', 'Fernanda Souza', '66666666666', '11933332222', 'fernanda@gmail.com',
'$2a$10$dkkYpflue8LjCA5BjXVbVedjTH3jnnZOj0RGH6/A0EODPPgg8xesG', 7, true, NOW(), false),

(default, 'PJ', 'Gabriel Lima', '77777777777', '11922221111', 'gabriel@gmail.com',
'$2a$10$3ktU8q8a2wKQWy8WBCQf/O0B/6Y1nfw/3Tk8Vt.1fTzVTcGqYpgIW', 8, true, NOW(), false),

(default, 'PJ', 'Juliana Rocha', '88888888888', '11911110000', 'juliana@gmail.com',
'$2a$10$eVlTtfX5wxeLMlQwQ5rOuuDJtosvqqfEFTSUHt76vGY.Cf0MNVF5u', 9, true, NOW(), false),

(default, 'PF', 'Pedro Martins', '99999999999', '11900009999', 'pedro@gmail.com',
'$2a$10$lVHbVsF1cAfaLJaKuZMLgO1XJ1mPK7rRIp1.DLzsHwbY4hJBPviHq', 10, true, NOW(), false);

-- VOLUNTEERS
INSERT INTO volunteer_tb (user_id, available_date)
VALUES
(1, '2025-01-10'),
(2, '2025-01-12'),
(3, '2025-01-15'),
(4, '2025-01-18'),
(5, '2025-01-20'),
(6, '2025-01-22'),
(7, '2025-01-25'),
(8, '2025-01-28'),
(9, '2025-02-02'),
(10, '2025-02-05');

-- SPONSORSHIPS
INSERT INTO sponsorship_tb (id, sponsor_id, type, description, department)
VALUES
(default, 1, 'PF', 'Desejo apoiar com doação mensal de ração', 'Alimentício'),
(default, 2, 'PJ', 'Empresa parceira para eventos anuais', 'Marketing'),
(default, 3, 'PF', 'Posso ajudar com custos veterinários periódicos', 'Veterinário'),
(default, 4, 'PJ', 'Disponibilização de espaço para campanhas de adoção', 'Eventos'),
(default, 5, 'PF', 'Contribuição mensal para compra de medicamentos', 'Saúde'),
(default, 6, 'PJ', 'Doações trimestrais de equipamentos para o abrigo', 'Infraestrutura'),
(default, 7, 'PF', 'Ajudarei com transporte para resgate de animais', 'Logística'),
(default, 8, 'PJ', 'Patrocínio de campanhas digitais de conscientização', 'Marketing'),
(default, 9, 'PF', 'Pretendo doar cobertores e mantas todo inverno', 'Alimentício'),
(default, 10, 'PJ', 'Apoio financeiro anual para reformas do abrigo', 'Infraestrutura');

-- ADOPTION FAIRS
INSERT INTO fair (id, fair_date, fair_hour, address_id, interest)
VALUES
(default, '2025-01-12', '2025-01-12 10:00:00', 1, 15),
(default, '2025-02-05', '2025-02-05 14:30:00', 2, 8),
(default, '2025-03-20', '2025-03-20 09:00:00', 3, 22),
(default, '2025-04-10', '2025-04-10 11:00:00', 4, 5),
(default, '2025-05-18', '2025-05-18 13:00:00', 5, 30),
(default, '2025-06-02', '2025-06-02 16:00:00', 6, 12),
(default, '2025-07-25', '2025-07-25 10:00:00', 7, 18),
(default, '2025-08-14', '2025-08-14 15:00:00', 8, 7),
(default, '2025-09-09', '2025-09-09 09:30:00', 9, 26),
(default, '2025-10-01', '2025-10-01 14:00:00', 10, 11);

INSERT INTO fair_images (fair_id, image_path)
VALUES
(1, 'pet1.jpg'),
(1, 'pet2.jpg'),
(1, 'pet3.jpg');
(2, 'pet1.jpg'),
(2, 'pet2.jpg'),
(2, 'pet3.jpg');
(3, 'pet1.jpg'),
(3, 'pet2.jpg'),
(3, 'pet3.jpg');
(4, 'pet1.jpg'),
(4, 'pet2.jpg'),
(4, 'pet3.jpg');
(5, 'pet1.jpg'),
(5, 'pet2.jpg'),
(5, 'pet3.jpg');
(6, 'pet1.jpg'),
(6, 'pet2.jpg'),
(6, 'pet3.jpg');
(7, 'pet1.jpg'),
(7, 'pet2.jpg'),
(7, 'pet3.jpg');
(8, 'pet1.jpg'),
(8, 'pet2.jpg'),
(8, 'pet3.jpg');
(9, 'pet1.jpg'),
(9, 'pet2.jpg'),
(9, 'pet3.jpg');
(10, 'pet1.jpg'),
(10, 'pet2.jpg'),
(10, 'pet3.jpg');


-- NOTIFICATION
INSERT INTO notification_tb (id, notification_type, fair_id, message, event_date, created_at)
VALUES
(default, 'FAIR', 1, 'A feira de adoção está chegando! Não perca essa oportunidade de adotar um amigo.', '2025-01-12', '2025-01-05T10:30:00Z'),
(default, 'DONATION', NULL, 'Estamos precisando de doações de ração e medicamentos para o abrigo.', '2025-02-01', '2025-01-20T14:15:00Z'),
(default, 'GENERAL', NULL, 'O Abrigo Dog Feliz tem novidades especiais para você!', '2025-01-18', '2025-01-10T09:00:00Z'),
(default, 'VOLUNTEER', NULL, 'Precisamos de voluntários para ajudar no cuidado dos animais.', '2025-01-25', '2025-01-12T16:40:00Z'),
(default, 'FAIR', 2, 'Uma nova feira de adoção foi confirmada! Venha conhecer os animais.', '2025-03-05', '2025-02-20T11:00:00Z'),
(default, 'DONATION', NULL, 'As doações estão baixas! Qualquer ajuda faz diferença.', '2025-02-10', '2025-01-30T17:25:00Z'),
(default, 'GENERAL', NULL, 'Obrigado por fazer parte da família Dog Feliz ❤️', '2025-01-22', '2025-01-15T08:45:00Z'),
(default, 'VOLUNTEER', NULL, 'Estamos organizando um mutirão de limpeza, participe!', '2025-02-15', '2025-02-01T13:00:00Z'),
(default, 'FAIR', 3, 'Terceira feira de adoção do ano confirmada! Esperamos você.', '2025-04-12', '2025-03-10T10:10:00Z'),
(default, 'DONATION', NULL, 'Campanha especial: doe um pacote de ração e ajude dezenas de cães!', '2025-03-01', '2025-02-18T15:55:00Z');

-- NOTIFICATION RECURRENCE
INSERT INTO notification_recurrence_tb (notification_id, recurrence)
VALUES
    (1, '2025-01-10'),
    (2, '2025-02-10'),
    (3, '2025-01-05'),
    (4, '2025-01-20'),
    (5, '2025-03-01'),
    (6, '2025-03-15'),
    (7, '2025-02-01'),
    (8, '2025-02-18'),
    (9, '2025-04-10'),
    (9, '2025-01-30'),
    (9, '2025-05-12');

