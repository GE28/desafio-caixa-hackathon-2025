package br.gov.caixa.hackathon2025.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class VolumeSimulacaoResponse {
    
    @JsonProperty("dataReferencia")
    private LocalDate dataReferencia;
    
    @JsonProperty("simulacoes")
    private List<SimulacaoPorProduto> simulacoes;
    
    // Getters and Setters
    public LocalDate getDataReferencia() {
        return dataReferencia;
    }
    
    public void setDataReferencia(LocalDate dataReferencia) {
        this.dataReferencia = dataReferencia;
    }
    
    public List<SimulacaoPorProduto> getSimulacoes() {
        return simulacoes;
    }
    
    public void setSimulacoes(List<SimulacaoPorProduto> simulacoes) {
        this.simulacoes = simulacoes;
    }
    
    public static class SimulacaoPorProduto {
        @JsonProperty("codigoProduto")
        private Integer codigoProduto;
        
        @JsonProperty("descricaoProduto")
        private String descricaoProduto;
        
        @JsonProperty("taxaMediaJuro")
        private BigDecimal taxaMediaJuro;
        
        @JsonProperty("valorMedioPrestacao")
        private BigDecimal valorMedioPrestacao;
        
        @JsonProperty("valorTotalDesejado")
        private BigDecimal valorTotalDesejado;
        
        @JsonProperty("valorTotalCredito")
        private BigDecimal valorTotalCredito;
        
        // Getters and Setters
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
        
        public BigDecimal getTaxaMediaJuro() {
            return taxaMediaJuro;
        }
        
        public void setTaxaMediaJuro(BigDecimal taxaMediaJuro) {
            this.taxaMediaJuro = taxaMediaJuro;
        }
        
        public BigDecimal getValorMedioPrestacao() {
            return valorMedioPrestacao;
        }
        
        public void setValorMedioPrestacao(BigDecimal valorMedioPrestacao) {
            this.valorMedioPrestacao = valorMedioPrestacao;
        }
        
        public BigDecimal getValorTotalDesejado() {
            return valorTotalDesejado;
        }
        
        public void setValorTotalDesejado(BigDecimal valorTotalDesejado) {
            this.valorTotalDesejado = valorTotalDesejado;
        }
        
        public BigDecimal getValorTotalCredito() {
            return valorTotalCredito;
        }
        
        public void setValorTotalCredito(BigDecimal valorTotalCredito) {
            this.valorTotalCredito = valorTotalCredito;
        }
    }
}
