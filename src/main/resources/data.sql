INSERT INTO address_tb (id, street, number, complement, city, state, country, zip_code, created_at)
VALUES (999, 'Av. caminho do Mar', '2227', 'Loja', 'São Paulo', 'SP', 'Brasil', '09609-000', CURRENT_TIMESTAMP);

INSERT INTO address_tb (id, street, number, complement, city, state, country, zip_code, created_at)
VALUES (1000, 'R. Regina Maria de Lourdes Nascimento', '587', 'Chacara', 'São Paulo', 'SP', 'Brasil', '09609-000', CURRENT_TIMESTAMP);



INSERT INTO collection_center_tb (name, address_id)
VALUES ('ONG Dog Feliz', 1000);

INSERT INTO collection_center_tb (name, address_id)
VALUES ('PetShop PetLand', 999);

