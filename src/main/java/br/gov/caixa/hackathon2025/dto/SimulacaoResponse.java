package br.gov.caixa.hackathon2025.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.List;

@Schema(description = "Resposta da simulação de empréstimo")
public class SimulacaoResponse {
    
    @JsonProperty("idSimulacao")
    @Schema(description = "ID único da simulação", example = "1")
    private Long idSimulacao;
    
    @JsonProperty("codigoProduto")
    @Schema(description = "Código do produto de empréstimo", example = "1")
    private Integer codigoProduto;
    
    @JsonProperty("descricaoProduto")
    @Schema(description = "Descrição do produto de empréstimo", example = "Empréstimo Pessoal")
    private String descricaoProduto;
    
    @JsonProperty("taxaJuros")
    @Schema(description = "Taxa de juros aplicada", example = "1.50")
    private BigDecimal taxaJuros;
    
    @JsonProperty("resultadoSimulacao")
    @Schema(description = "Lista de resultados por tipo de amortização")
    private List<ResultadoSimulacaoDto> resultadoSimulacao;
    
    // Getters and Setters
    public Long getIdSimulacao() {
        return idSimulacao;
    }
    
    public void setIdSimulacao(Long idSimulacao) {
        this.idSimulacao = idSimulacao;
    }
    
    public Integer getCodigoProduto() {
        return codigoProduto;
    }
    
    public void setCodigoProduto(Integer codigoProduto) {
        this.codigoProduto = codigoProduto;
    }
    
    public String getDescricaoProduto() {
        return descricaoProduto;
    }
    
    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }
    
    public BigDecimal getTaxaJuros() {
        return taxaJuros;
    }
    
    public void setTaxaJuros(BigDecimal taxaJuros) {
        this.taxaJuros = taxaJuros;
    }
    
    public List<ResultadoSimulacaoDto> getResultadoSimulacao() {
        return resultadoSimulacao;
    }
    
    public void setResultadoSimulacao(List<ResultadoSimulacaoDto> resultadoSimulacao) {
        this.resultadoSimulacao = resultadoSimulacao;
    }
}
