package br.gov.caixa.hackathon2025.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Schema(description = "Volume de simulações por data")
public class VolumeSimulacaoResponse {
    
    @JsonProperty("dataReferencia")
    @Schema(description = "Data de referência", example = "2025-01-15")
    private LocalDate dataReferencia;
    
    @JsonProperty("simulacoes")
    @Schema(description = "Volume de simulações agrupado por produto")
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
    
    @Schema(description = "Volume de simulações por produto")
    public static class SimulacaoPorProduto {
        @JsonProperty("codigoProduto")
        @Schema(description = "Código do produto", example = "1")
        private Integer codigoProduto;
        
        @JsonProperty("descricaoProduto")
        @Schema(description = "Descrição do produto", example = "Empréstimo Pessoal")
        private String descricaoProduto;
        
        @JsonProperty("taxaMediaJuro")
        @Schema(description = "Taxa média de juros", example = "1.50")
        private BigDecimal taxaMediaJuro;
        
        @JsonProperty("valorMedioPrestacao")
        @Schema(description = "Valor médio das prestações", example = "2500.00")
        private BigDecimal valorMedioPrestacao;
        
        @JsonProperty("valorTotalDesejado")
        @Schema(description = "Valor total desejado", example = "500000.00")
        private BigDecimal valorTotalDesejado;
        
        @JsonProperty("valorTotalCredito")
        @Schema(description = "Valor total de crédito", example = "680000.00")
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
