package br.gov.caixa.hackathon2025.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
public class SimulacaoResourceTest {
    
    @Test
    public void testSimulacaoEmprestimoValido() {
        String requestBody = """
            {
                "valorDesejado": 900.00,
                "prazo": 5
            }
            """;
        
        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .post("/simulacao")
            .then()
            .statusCode(200)
            .body("idSimulacao", notNullValue())
            .body("codigoProduto", notNullValue())
            .body("descricaoProduto", notNullValue())
            .body("taxaJuros", notNullValue())
            .body("resultadoSimulacao.size()", is(2))
            .body("resultadoSimulacao[0].tipo", is("SAC"))
            .body("resultadoSimulacao[1].tipo", is("PRICE"))
            .body("resultadoSimulacao[0].parcelas.size()", is(5))
            .body("resultadoSimulacao[1].parcelas.size()", is(5));
    }
    
    @Test
    public void testSimulacaoComValorInvalido() {
        String requestBody = """
            {
                "valorDesejado": -100.00,
                "prazo": 5
            }
            """;
        
        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .post("/simulacao")
            .then()
            .statusCode(400);
    }
    
    @Test
    public void testSimulacaoComPrazoInvalido() {
        String requestBody = """
            {
                "valorDesejado": 900.00,
                "prazo": -1
            }
            """;
        
        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .post("/simulacao")
            .then()
            .statusCode(400);
    }
    
    @Test
    public void testSimulacaoSemProdutoCompativel() {
        String requestBody = """
            {
                "valorDesejado": 50000000.00,
                "prazo": 1000
            }
            """;
        
        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .post("/simulacao")
            .then()
            .statusCode(404);
    }
    
    @Test
    public void testListarSimulacoes() {
        given()
            .when()
            .get("/simulacao")
            .then()
            .statusCode(200)
            .body("pagina", notNullValue())
            .body("qtdRegistros", notNullValue())
            .body("qtdRegistrosPagina", notNullValue())
            .body("registros", notNullValue());
    }
}
