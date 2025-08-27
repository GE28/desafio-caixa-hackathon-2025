CREATE SCHEMA IF NOT EXISTS dbo;

CREATE TABLE dbo.PRODUTO (
    CO_PRODUTO int NOT NULL primary key,
    NO_PRODUTO varchar(200) NOT NULL,
    PC_TAXA_JUROS numeric(10, 9) NOT NULL,
    NU_MINIMO_MESES smallint NOT NULL,
    NU_MAXIMO_MESES smallint NULL,
    VR_MINIMO numeric(18, 2) NOT NULL,
    VR_MAXIMO numeric(18, 2) NULL
);
-- CREATE TABLE PRODUTO (
--     CO_PRODUTO int NOT NULL PRIMARY KEY,
--     NO_PRODUTO varchar(200) NOT NULL,
--     PC_TAXA_JUROS decimal(10, 9) NOT NULL,
--     NU_MINIMO_MESES smallint NOT NULL,
--     NU_MAXIMO_MESES smallint,
--     VR_MINIMO decimal(18, 2) NOT NULL,
--     VR_MAXIMO decimal(18, 2)
-- );

INSERT INTO dbo.PRODUTO (CO_PRODUTO, NO_PRODUTO, PC_TAXA_JUROS,
NU_MINIMO_MESES, NU_MAXIMO_MESES, VR_MINIMO, VR_MAXIMO)
VALUES (1, 'Produto 1', 0.017900000, 0, 24, 200.00, 10000.00);

INSERT INTO dbo.PRODUTO (CO_PRODUTO, NO_PRODUTO, PC_TAXA_JUROS,
NU_MINIMO_MESES, NU_MAXIMO_MESES, VR_MINIMO, VR_MAXIMO)
VALUES (2, 'Produto 2', 0.017500000, 25, 48, 10001.00, 100000.00);

INSERT INTO dbo.PRODUTO (CO_PRODUTO, NO_PRODUTO, PC_TAXA_JUROS,
NU_MINIMO_MESES, NU_MAXIMO_MESES, VR_MINIMO, VR_MAXIMO)
VALUES (3, 'Produto 3', 0.018200000, 49, 96, 100000.01, 1000000.00);

INSERT INTO dbo.PRODUTO (CO_PRODUTO, NO_PRODUTO, PC_TAXA_JUROS,
NU_MINIMO_MESES, NU_MAXIMO_MESES, VR_MINIMO, VR_MAXIMO)
VALUES (4, 'Produto 4', 0.015100000, 96, null, 1000000.01, null);

-- -- mssql
-- CREATE TABLE IF NOT EXISTS simulacao (
--     id_simulacao BIGINT IDENTITY(1,1) PRIMARY KEY,
--     codigo_produto INT NOT NULL,
--     valor_desejado DECIMAL(18,2) NOT NULL,
--     prazo INT NOT NULL,
--     taxa_juros DECIMAL(10,9) NOT NULL,
--     valor_total_parcelas DECIMAL(18,2) NOT NULL,
--     data_simulacao DATETIME2 NOT NULL DEFAULT GETDATE(),
--     tempo_resposta_ms BIGINT
-- );
-- h2
CREATE TABLE IF NOT EXISTS simulacao (
    id_simulacao BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo_produto INT NOT NULL,
    valor_desejado DECIMAL(18,2) NOT NULL,
    prazo INT NOT NULL,
    taxa_juros DECIMAL(10,9) NOT NULL,
    valor_total_parcelas DECIMAL(18,2) NOT NULL,
    data_simulacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    tempo_resposta_ms BIGINT
);

-- -- Índices para melhor performance
-- CREATE INDEX IF NOT EXISTS idx_simulacao_data ON simulacao(data_simulacao);
-- CREATE INDEX IF NOT EXISTS idx_simulacao_produto ON simulacao(codigo_produto);
CREATE INDEX idx_simulacao_data ON simulacao(data_simulacao);
CREATE INDEX idx_simulacao_produto ON simulacao(codigo_produto);