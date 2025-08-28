# Simulador de Empréstimo - API

API para simulação de empréstimo com cálculo automático das modalidades SAC e PRICE, armazenamento das simulações e integração com Azure Event Hub.

---

## Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Como Executar](#como-executar)
- [Endpoints da API](#endpoints-da-api)
- [Exemplos de Uso](#exemplos-de-uso)
- [Configuração Event Hub](#configuração-event-hub)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Testes](#testes)

---

## Sobre o Projeto

Esta API permite realizar simulações de empréstimo com base em produtos financeiros pré-configurados, calculando automaticamente as parcelas utilizando os sistemas de amortização **SAC** (Sistema de Amortização Constante) e **PRICE** (Tabela Price).

**Funcionalidades principais:**
- Simulação automática com ambos os sistemas de amortização
- Validação de produtos financeiros baseada em valor e prazo
- Armazenamento das simulações em banco de dados H2
- Envio assíncrono para Azure Event Hub
- Relatórios de volume de simulações
- Telemetria de performance da API

---

## Tecnologias Utilizadas

- **Java 21**
- **Quarkus Framework** (Supersonic Subatomic Java Framework)
- **Hibernate ORM with Panache** (Persistência de dados)
- **H2 Database** (Banco em memória para desenvolvimento)
- **Azure Event Hubs** (Mensageria para integração)
- **Jackson** (Serialização JSON)
- **OpenAPI/Swagger** (Documentação da API)
- **Maven** (Gerenciamento de dependências)

---

## Como Executar

### Pré-requisitos
- Java 21+
- Maven 3.8+

### Execução Local

1. **Clone o projeto:**
```bash
git clone <repository-url>
cd simulador-emprestimo
```

2. **Execute em modo de desenvolvimento:**
```bash
./mvnw quarkus:dev
```

3. **Acesse a aplicação:**
- API Base: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui
- Dev UI: http://localhost:8080/q/dev

### Execução com Package

```bash
# Gerar o JAR
./mvnw package

# Executar
java -jar target/quarkus-app/quarkus-run.jar
```

---

## Endpoints da API

### Simulações

| Método | Endpoint                    | Descrição                                    |
|--------|-----------------------------|----------------------------------------------|
| POST   | `/simulacao`                | Realiza nova simulação de empréstimo        |
| GET    | `/simulacao`                | Lista simulações com paginação              |

### Relatórios

| Método | Endpoint                           | Descrição                                    |
|--------|------------------------------------|----------------------------------------------|
| GET    | `/relatorios/volume/{data}`        | Volume de simulações por data específica    |
| GET    | `/relatorios/volume/agrupadas`     | Volumes agrupados por data                   |

### Telemetria

| Método | Endpoint                    | Descrição                                    |
|--------|-----------------------------|----------------------------------------------|
| GET    | `/telemetria`               | Estatísticas de performance da API           |
| GET    | `/telemetria/{data}`        | Telemetria por data específica               |

---

## Exemplos de Uso

### Realizar Simulação

**POST** `/simulacao`

```json
{
  "valorDesejado": 50000.00,
  "prazo": 24
}
```

**Resposta:**
```json
{
  "idSimulacao": 1,
  "codigoProduto": 1,
  "descricaoProduto": "Empréstimo Pessoal",
  "taxaJuros": 1.50,
  "resultadoSimulacao": [
    {
      "tipo": "SAC",
      "parcelas": [
        {
          "numero": 1,
          "valorAmortizacao": 2083.33,
          "valorJuros": 750.00,
          "valorPrestacao": 2833.33
        }
      ]
    },
    {
      "tipo": "PRICE",
      "parcelas": [
        {
          "numero": 1,
          "valorAmortizacao": 1897.67,
          "valorJuros": 750.00,
          "valorPrestacao": 2647.67
        }
      ]
    }
  ]
}
```

### Listar Simulações

**GET** `/simulacao?pagina=1&tamanho=10`

```json
{
  "pagina": 1,
  "qtdRegistros": 150,
  "qtdRegistrosPagina": 10,
  "registros": [
    {
      "idSimulacao": 1,
      "valorDesejado": 50000.00,
      "prazo": 24,
      "valorTotalParcelas": 68000.00
    }
  ]
}
```

---

## Configuração Event Hub

A aplicação integra com Azure Event Hub para envio assíncrono das simulações. Configure as variáveis de ambiente:

```properties
# application.properties
eventhub.connection-string=${EVENTHUB_CONNECTION_STRING}
eventhub.entity-path=${EVENTHUB_ENTITY_PATH:simulacoes}
```

**Variáveis de ambiente:**
```bash
# Linux/Mac
export EVENTHUB_CONNECTION_STRING="Endpoint=sb://..."
export EVENTHUB_ENTITY_PATH="simulacoes"

# Windows PowerShell
$env:EVENTHUB_CONNECTION_STRING="Endpoint=sb://..."
$env:EVENTHUB_ENTITY_PATH="simulacoes"
```

> **Nota:** A aplicação funciona normalmente mesmo sem o Azure Event Hub configurado.

---

## Estrutura do Projeto

```
src/
├── main/java/br/gov/caixa/hackathon2025/
│   ├── resource/          # Controllers REST
│   ├── service/           # Lógica de negócio
│   ├── dto/              # Objetos de transferência
│   ├── entity/           # Entidades JPA
│   ├── repository/       # Repositórios de dados
│   ├── config/           # Configurações
│   └── exception/        # Tratamento de erros
└── test/                 # Testes automatizados
```

---

## Testes

Execute os testes automatizados:

```bash
# Todos os testes
./mvnw test

# Testes específicos
./mvnw test -Dtest=SimulacaoResourceTest
```

**Coverage dos testes:**
- Testes de integração dos endpoints
- Testes unitários dos cálculos SAC e PRICE
- Validação de regras de negócio

---

## Documentação Adicional

- [Exemplos detalhados da API](SWAGGER_EXAMPLES.md)
- [Configuração do Event Hub](EVENTHUB.md)
- [Instruções do Hackathon](hackaton_instrucoes_2025.md)

---

## Desenvolvimento

**Executar em modo desenvolvimento:**
```bash
./mvnw quarkus:dev
```

**Gerar executável nativo:**
```bash
./mvnw package -Dnative
```

Para mais informações sobre Quarkus: https://quarkus.io/
