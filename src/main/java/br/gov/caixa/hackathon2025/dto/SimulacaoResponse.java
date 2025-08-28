package br.gov.caixa.hackathon2025.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

public class SimulacaoResponse {
    
    @JsonProperty("idSimulacao")
    private Long idSimulacao;
    
    @JsonProperty("codigoProduto")
    private Integer codigoProduto;
    
    @JsonProperty("descricaoProduto")
    private String descricaoProduto;
    
    @JsonProperty("taxaJuros")
    private BigDecimal taxaJuros;
    
    @JsonProperty("resultadoSimulacao")
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
