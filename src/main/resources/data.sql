-- Ativa extensão de UUID
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Cargos
INSERT INTO cargo (id, nome) VALUES (uuid_generate_v4(), 'ADMIN');
INSERT INTO cargo (id, nome) VALUES (uuid_generate_v4(), 'USER');

-- Usuários
INSERT INTO usuario (nome, cpf, data_nascimento, username, senha, telefone_principal, telefone_secundario, id_cargo)
VALUES
  (uuid_generate_v4(), 'wicar pessoa', '12345678901', '1990-01-01', 'wicar@email.com', '$2a$10$dESjVmu/R9RuxHuvHzjZce/wd10N2WFjKYjNS0DU1ql50fgNxw/hG', '31987654321', '31912345678', (SELECT id FROM cargo WHERE nome = 'ADMIN')),
  (uuid_generate_v4(), 'ian marcelino', '03721783311', '1990-01-01', 'ian@email.com', '$2a$10$dESjVmu/R9RuxHuvHzjZce/wd10N2WFjKYjNS0DU1ql50fgNxw/hG', '31987654321', '31912345678', (SELECT id FROM cargo WHERE nome = 'USER'));

-- Clientes
INSERT INTO cliente (id, nome, cpf, data_nascimento, email, rua, numero, complemento, cep)
VALUES
  (1, 'Maria da Silva', '12345678900', '1985-05-10', 'maria@gmail.com', 'Rua A', '100', 'ap 101', '30100-000'),
  (2, 'João Pereira', '98765432100', '1990-08-22', 'joao@gmail.com', 'Rua B', '200', '', '30200-000');

-- Pagamentos
INSERT INTO pagamento (id, forma_pagamento)
VALUES
  (uuid_generate_v4(), 'CARTAO'),
  (uuid_generate_v4(), 'DINHEIRO');

-- Produtos
INSERT INTO produto (id, nome, valor, quantidade_estoque, id_categoria, id_subcategoria)
VALUES
  (uuid_generate_v4(), 'Anel de Ouro', 350.00, 10,
    (SELECT id FROM categoria WHERE nome = 'Categoria Joias'),
    (SELECT id FROM subcategoria WHERE nome = 'Anéis')),
  (uuid_generate_v4(), 'Pulseira de Prata', 180.00, 20,
    (SELECT id FROM categoria WHERE nome = 'Categoria Semi Joias'),
    (SELECT id FROM subcategoria WHERE nome = 'Pulseiras'));

-- Histórico de valores dos produtos
INSERT INTO historico_valor (id, data, valor, id_produto)
VALUES
  (uuid_generate_v4(), '2025-01-01', 300.00, (SELECT id FROM produto WHERE nome = 'Anel de Ouro')),
  (uuid_generate_v4(), '2025-02-01', 350.00, (SELECT id FROM produto WHERE nome = 'Anel de Ouro'));

-- Vendas
INSERT INTO venda (id, data, id_usuario, id_cliente, id_pagamento)
VALUES
  (uuid_generate_v4(), '2025-04-01',
    (SELECT id FROM usuario WHERE nome = 'wicar pessoa'),
    1,
    (SELECT id FROM pagamento WHERE nome = 'CARTAO'));

-- Compra
INSERT INTO compra (id, data, fornecedor, id_usuario, id_pagamento, quantidade_parcelas)
VALUES
  (uuid_generate_v4(), '2025-03-15', 'Fornecedor Joias LTDA',
    (SELECT id FROM usuario WHERE nome = 'ian marcelino'),
     (SELECT id FROM pagamento WHERE nome = 'CARTAO'), 3);

-- Compra Itens
INSERT INTO compra_item (id, produto_id, valor_unitario, quantidade_venda, id_compra)
VALUES
  (uuid_generate_v4(), (SELECT id FROM produto WHERE nome = 'Anel de Ouro'), 300.00, 5, (SELECT id FROM compra WHERE data = '2025-03-15'));

-- Acertos
INSERT INTO acerto (id, data, observacao)
VALUES
  (uuid_generate_v4(), '2025-04-10', 'Acerto de estoque abril');

-- Acerto Itens
INSERT INTO acerto_item (id, id_produto, id_acerto, data, quantidade, valor, observacao)
VALUES
  (uuid_generate_v4(), (SELECT id FROM produto WHERE nome = 'Anel de Ouro'), (SELECT id FROM acerto WHERE data = '2025-04-10'), '2025-04-10', 3, 350.00, 'Reposição estoque');
