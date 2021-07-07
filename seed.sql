INSERT INTO tb_client (clientCpf, name, address, email, phone, instagram) VALUES ('13901823034','Douglas Arantes', 'Wonderful place, 360', 'douglas@gmail.com', '(34) 99988-7766', '@douglas');
INSERT INTO tb_client (clientCpf, name, address, email, phone, instagram) VALUES ('40197353897','Matheus Ferreira', 'Wonderful place, 320', 'matheus@gmail.com', '(34) 99988-7766', '@matheus');
INSERT INTO tb_client (clientCpf, name, address, email, phone, instagram) VALUES ('13547893283','Junior Golveia', 'Wonderful place, 330', 'alcino@gmail.com', '(34) 99988-7766', '@alcino');
INSERT INTO tb_client (clientCpf, name, address, email, phone, instagram) VALUES ('45985723389','Thiago Lima', 'Wonderful place, 340', 'thiago@gmail.com', '(34) 99988-7766', '@thiago');
INSERT INTO tb_client (clientCpf, name, address, email, phone, instagram) VALUES ('01293182411','Jonathan Barbossa', 'Wonderful place, 360', 'barbossa@gmail.com', '(34) 99988-7766', '@jon');

INSERT INTO tb_glasses (id, imageLink, prescription) VALUES ('GLA-X1','www.link.com.br', 'OD - 5, OE - 4.3 EE+');
INSERT INTO tb_glasses (id, imageLink, prescription) VALUES ('GLA-X2','www.link.com.br', 'OD - 2.2, OE - 4.9 CC+');
INSERT INTO tb_glasses (id, imageLink, prescription) VALUES ('GLA-X3','www.link.com.br', 'OD - 4, OE - 2.2 CE+');
INSERT INTO tb_glasses (id, imageLink, prescription) VALUES ('GLA-X4','www.link.com.br', 'OD - 2.2, OE - 1.3 EE+ C+');
INSERT INTO tb_glasses (id, imageLink, prescription) VALUES ('GLA-X5','www.link.com.br', 'OD - 7, OE - 8.5 CC+');

INSERT INTO tb_order (cod, orderDate, price, discount, client_cpf, glasses_id) VALUES ('ORD-BR1','2021-08-22',490.0,25.0,'45985723389','GLA-X1');