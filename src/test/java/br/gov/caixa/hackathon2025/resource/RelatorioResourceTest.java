package br.gov.caixa.hackathon2025.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
class RelatorioResourceTest {
    @Test
    void testListarSimulacoes() {
        given()
            .queryParam("pagina", 1)
            .queryParam("tamanho", 10)
            .when()
            .get("/relatorios/simulacoes")
            .then()
            .statusCode(200)
            .body("pagina", notNullValue())
            .body("qtdRegistros", notNullValue())
            .body("qtdRegistrosPagina", notNullValue())
            .body("registros", notNullValue());
    }
    
    @Test
    void testObterVolumePorData() {
        String data = LocalDate.now().toString();
        
        given()
            .pathParam("data", data)
            .when()
            .get("/relatorios/volume/{data}")
            .then()
            .statusCode(200)
            .body("dataReferencia", notNullValue())
            .body("simulacoes", notNullValue());
    }
    
    @Test
    void testObterTelemetria() {
        String data = LocalDate.now().toString();
        
        given()
            .pathParam("data", data)
            .when()
            .get("/relatorios/telemetria/{data}")
            .then()
            .statusCode(200)
            .body("dataReferencia", notNullValue())
            .body("listaEndpoints", notNullValue());
    }
    
    @Test
    void testDataInvalida() {
        given()
            .pathParam("data", "data-invalida")
            .when()
            .get("/relatorios/volume/{data}")
            .then()
            .statusCode(400);
    }
}
