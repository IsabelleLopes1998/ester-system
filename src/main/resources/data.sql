-- Ativa extensão de UUID
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
ALTER TABLE usuario ALTER COLUMN created_at SET DEFAULT now();
ALTER TABLE usuario ALTER COLUMN updated_at SET DEFAULT now();
ALTER TABLE cargo ALTER COLUMN created_at SET DEFAULT now();
ALTER TABLE cargo ALTER COLUMN updated_at SET DEFAULT now();
ALTER TABLE cliente ALTER COLUMN created_at SET DEFAULT now();
ALTER TABLE cliente ALTER COLUMN updated_at SET DEFAULT now();
ALTER TABLE produto ALTER COLUMN created_at SET DEFAULT now();
ALTER TABLE produto ALTER COLUMN updated_at SET DEFAULT now();
ALTER TABLE categoria ALTER COLUMN created_at SET DEFAULT now();
ALTER TABLE categoria ALTER COLUMN updated_at SET DEFAULT now();
ALTER TABLE subcategoria ALTER COLUMN created_at SET DEFAULT now();
ALTER TABLE subcategoria ALTER COLUMN updated_at SET DEFAULT now();
ALTER TABLE historico_valor ALTER COLUMN created_at SET DEFAULT now();
ALTER TABLE historico_valor ALTER COLUMN updated_at SET DEFAULT now();
ALTER TABLE venda ALTER COLUMN created_at SET DEFAULT now();
ALTER TABLE venda ALTER COLUMN updated_at SET DEFAULT now();
ALTER TABLE pagamento ALTER COLUMN created_at SET DEFAULT now();
ALTER TABLE pagamento ALTER COLUMN updated_at SET DEFAULT now();
ALTER TABLE compra ALTER COLUMN created_at SET DEFAULT now();
ALTER TABLE compra ALTER COLUMN updated_at SET DEFAULT now();
ALTER TABLE compra_item ALTER COLUMN created_at SET DEFAULT now();
ALTER TABLE compra_item ALTER COLUMN updated_at SET DEFAULT now();
-- Cargos
INSERT INTO cargo (id, nome) VALUES (uuid_generate_v4(), 'ADMIN');
INSERT INTO cargo (id, nome) VALUES (uuid_generate_v4(), 'USER');

-- Usuários
INSERT INTO usuario (id,nome, cpf, data_nascimento, username, senha, telefone_principal, telefone_secundario, id_cargo)
VALUES
  (uuid_generate_v4(), 'wicar pessoa', '12345678901', '1990-01-01', 'wicar@email.com', '$2a$10$5fB45wvRP9P04kOvR5OQte6zZ7ZC8AIbJHqYXHX0BU0mXWiodDTZW', '31987654321', '31912345678', (SELECT id FROM cargo WHERE nome = 'ADMIN')),
  (uuid_generate_v4(), 'ian marcelino', '03721783311', '1990-01-01', 'ian@email.com', '$2a$10$5fB45wvRP9P04kOvR5OQte6zZ7ZC8AIbJHqYXHX0BU0mXWiodDTZW', '31987654321', '31912345678', (SELECT id FROM cargo WHERE nome = 'USER'));

-- Clientes
INSERT INTO cliente ( nome, cpf, data_nascimento, email, rua, numero, complemento, cep, primeiro_telefone, segundo_telefone)
VALUES
  ('Maria da Silva', '123.456.789-00', '1985-05-10', 'maria@gmail.com', 'Rua A', '100', 'ap 101', '30100-000', '(85) 98765-4321', '(85) 91234-5678'),
  ('João Pereira', '987.654.321-00', '1990-08-22', 'joao@gmail.com', 'Rua B', '200', '', '30200-000', '(85) 97654-3210', '(85) 91111-1111');

-- Categorias
INSERT INTO categoria (id, nome, descricao)
VALUES
  (uuid_generate_v4(), 'Categoria Joias', 'joias'),
  (uuid_generate_v4(), 'Categoria Semi Joias', 'semi joias');

-- Subcategorias
-- Lembrando que estamos pegando os IDs das categorias criadas acima
INSERT INTO subcategoria (id, nome, descricao, id_categoria)
VALUES
  (uuid_generate_v4(), 'Anéis', 'joias', (SELECT id FROM categoria WHERE nome = 'Categoria Joias')),
  (uuid_generate_v4(), 'Pulseiras', 'semi joias', (SELECT id FROM categoria WHERE nome = 'Categoria Semi Joias'));




