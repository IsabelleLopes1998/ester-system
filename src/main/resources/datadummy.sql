-- Ativa extensão de UUID
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Cargos
INSERT INTO cargo (id, nome) VALUES (uuid_generate_v4(), 'ADMIN');
INSERT INTO cargo (id, nome) VALUES (uuid_generate_v4(), 'USER');

-- Usuários
INSERT INTO usuario (id, nome, cpf, data_nascimento, username, senha, telefone_principal, telefone_secundario, id_cargo)
VALUES
  (uuid_generate_v4(), 'wicar pessoa', '12345678901', '1990-01-01', 'wicar@email.com', '$2a$10$dESjVmu/R9RuxHuvHzjZce/wd10N2WFjKYjNS0DU1ql50fgNxw/hG', '31987654321', '31912345678', (SELECT id FROM cargo WHERE nome = 'ADMIN')),
  (uuid_generate_v4(), 'ian marcelino', '03721783311', '1990-01-01', 'ian@email.com', '$2a$10$dESjVmu/R9RuxHuvHzjZce/wd10N2WFjKYjNS0DU1ql50fgNxw/hG', '31987654321', '31912345678', (SELECT id FROM cargo WHERE nome = 'USER'));
