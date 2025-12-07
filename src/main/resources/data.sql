INSERT INTO address_tb (id, street, number, complement, city, state, country, zip_code, created_at)
VALUES (10, 'Rua da Adoção', '500', 'Galpão B', 'São Paulo', 'SP', 'Brasil', '05000-000', CURRENT_TIMESTAMP);

INSERT INTO address_tb (id, street, number, complement, city, state, country, zip_code, created_at)
VALUES (20, 'Av. Paulista', '2000', 'Térreo', 'São Paulo', 'SP', 'Brasil', '01310-100', CURRENT_TIMESTAMP);



INSERT INTO collection_center_tb (name, address_id)
VALUES ('ONG Casa do Vira-Lata', 10);

INSERT INTO collection_center_tb (name, address_id)
VALUES ('PetShop Amigo Fiel', 20);

