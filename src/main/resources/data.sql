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
(1, 'PF', 'Admin Master', '00000000000', '11999990000', 'admin@dogfeliz.com', 'admin123', 1, true, NOW(), true),
-- USERS COMUNS
(2, 'PF', 'João Silva', '11111111111', '11988887777', 'joao@gmail.com', 'senha1', 2, true, NOW(), false),
(3, 'PJ', 'Maria Santos', '22222222222', '11977776666', 'maria@gmail.com', 'senha2', 3, true, NOW(), false),
(4, 'PF', 'Carlos Almeida', '33333333333', '11966665555', 'carlos@gmail.com', 'senha3', 4, true, NOW(), false),
(5, 'PJ', 'Ana Oliveira', '44444444444', '11955554444', 'ana@gmail.com', 'senha4', 5, true, NOW(), false),
(6, 'PF', 'Rafael Torres', '55555555555', '11944443333', 'rafael@gmail.com', 'senha5', 6, true, NOW(), false),
(7, 'PF', 'Fernanda Souza', '66666666666', '11933332222', 'fernanda@gmail.com', 'senha6', 7, true, NOW(), false),
(8, 'PJ', 'Gabriel Lima', '77777777777', '11922221111', 'gabriel@gmail.com', 'senha7', 8, true, NOW(), false),
(9, 'PJ', 'Juliana Rocha', '88888888888', '11911110000', 'juliana@gmail.com', 'senha8', 9, true, NOW(), false),
(10, 'PF', 'Pedro Martins', '99999999999', '11900009999', 'pedro@gmail.com', 'senha9', 10, true, NOW(), false);

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
(1, 1, 'PF', 'Desejo apoiar com doação mensal de ração', 'Alimentício'),
(2, 2, 'PJ', 'Empresa parceira para eventos anuais', 'Marketing'),
(3, 3, 'PF', 'Posso ajudar com custos veterinários periódicos', 'Veterinário'),
(4, 4, 'PJ', 'Disponibilização de espaço para campanhas de adoção', 'Eventos'),
(5, 5, 'PF', 'Contribuição mensal para compra de medicamentos', 'Saúde'),
(6, 6, 'PJ', 'Doações trimestrais de equipamentos para o abrigo', 'Infraestrutura'),
(7, 7, 'PF', 'Ajudarei com transporte para resgate de animais', 'Logística'),
(8, 8, 'PJ', 'Patrocínio de campanhas digitais de conscientização', 'Marketing'),
(9, 9, 'PF', 'Pretendo doar cobertores e mantas todo inverno', 'Alimentício'),
(10, 10, 'PJ', 'Apoio financeiro anual para reformas do abrigo', 'Infraestrutura');

-- ADOPTION FAIRS
INSERT INTO fair (id, fair_date, fair_hour, address_id, interest)
VALUES
(1, '2025-01-12', '2025-01-12 10:00:00', 1, 15),
(2, '2025-02-05', '2025-02-05 14:30:00', 2, 8),
(3, '2025-03-20', '2025-03-20 09:00:00', 3, 22),
(4, '2025-04-10', '2025-04-10 11:00:00', 4, 5),
(5, '2025-05-18', '2025-05-18 13:00:00', 5, 30),
(6, '2025-06-02', '2025-06-02 16:00:00', 6, 12),
(7, '2025-07-25', '2025-07-25 10:00:00', 7, 18),
(8, '2025-08-14', '2025-08-14 15:00:00', 8, 7),
(9, '2025-09-09', '2025-09-09 09:30:00', 9, 26),
(10, '2025-10-01', '2025-10-01 14:00:00', 10, 11);

-- NOTIFICATION
INSERT INTO notification_tb (id, notification_type, fair_id, message, event_date, created_at)
VALUES
(1, 'FAIR', 1, 'A feira de adoção está chegando! Não perca essa oportunidade de adotar um amigo.', '2025-01-12', '2025-01-05T10:30:00Z'),
(2, 'DONATION', NULL, 'Estamos precisando de doações de ração e medicamentos para o abrigo.', '2025-02-01', '2025-01-20T14:15:00Z'),
(3, 'GENERAL', NULL, 'O Abrigo Dog Feliz tem novidades especiais para você!', '2025-01-18', '2025-01-10T09:00:00Z'),
(4, 'VOLUNTEER', NULL, 'Precisamos de voluntários para ajudar no cuidado dos animais.', '2025-01-25', '2025-01-12T16:40:00Z'),
(5, 'FAIR', 2, 'Uma nova feira de adoção foi confirmada! Venha conhecer os animais.', '2025-03-05', '2025-02-20T11:00:00Z'),
(6, 'DONATION', NULL, 'As doações estão baixas! Qualquer ajuda faz diferença.', '2025-02-10', '2025-01-30T17:25:00Z'),
(7, 'GENERAL', NULL, 'Obrigado por fazer parte da família Dog Feliz ❤️', '2025-01-22', '2025-01-15T08:45:00Z'),
(8, 'VOLUNTEER', NULL, 'Estamos organizando um mutirão de limpeza, participe!', '2025-02-15', '2025-02-01T13:00:00Z'),
(9, 'FAIR', 3, 'Terceira feira de adoção do ano confirmada! Esperamos você.', '2025-04-12', '2025-03-10T10:10:00Z'),
(10, 'DONATION', NULL, 'Campanha especial: doe um pacote de ração e ajude dezenas de cães!', '2025-03-01', '2025-02-18T15:55:00Z');

-- NOTIFICATION RECURRENCE
INSERT INTO notification_recurrence_tb (notification_id, recurrence)
VALUES
    (1, '2025-01-10'),
    (1, '2025-02-10'),
    (2, '2025-01-05'),
    (2, '2025-01-20'),
    (3, '2025-03-01'),
    (3, '2025-03-15'),
    (4, '2025-02-01'),
    (5, '2025-02-18'),
    (6, '2025-04-10'),
    (7, '2025-01-30'),
    (8, '2025-05-12');

