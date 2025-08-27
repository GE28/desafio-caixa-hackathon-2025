package br.gov.caixa.hackathon2025.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

public class ListaSimulacaoResponse {
    
    @JsonProperty("pagina")
    private Integer pagina;
    
    @JsonProperty("qtdRegistros")
    private Long qtdRegistros;
    
    @JsonProperty("qtdRegistrosPagina")
    private Integer qtdRegistrosPagina;
    
    @JsonProperty("registros")
    private List<SimulacaoResumo> registros;
    
    // Constructors
    public ListaSimulacaoResponse() {}
    
    // Getters and Setters
    public Integer getPagina() {
        return pagina;
    }
    
    public void setPagina(Integer pagina) {
        this.pagina = pagina;
    }
    
    public Long getQtdRegistros() {
        return qtdRegistros;
    }
    
    public void setQtdRegistros(Long qtdRegistros) {
        this.qtdRegistros = qtdRegistros;
    }
    
    public Integer getQtdRegistrosPagina() {
        return qtdRegistrosPagina;
    }
    
    public void setQtdRegistrosPagina(Integer qtdRegistrosPagina) {
        this.qtdRegistrosPagina = qtdRegistrosPagina;
    }
    
    public List<SimulacaoResumo> getRegistros() {
        return registros;
    }
    
    public void setRegistros(List<SimulacaoResumo> registros) {
        this.registros = registros;
    }
    
    public static class SimulacaoResumo {
        @JsonProperty("idSimulacao")
        private Long idSimulacao;
        
        @JsonProperty("valorDesejado")
        private BigDecimal valorDesejado;
        
        @JsonProperty("prazo")
        private Integer prazo;
        
        @JsonProperty("valorTotalParcelas")
        private BigDecimal valorTotalParcelas;
        
        // Constructors
        public SimulacaoResumo() {}
        
        public SimulacaoResumo(Long idSimulacao, BigDecimal valorDesejado, Integer prazo, BigDecimal valorTotalParcelas) {
            this.idSimulacao = idSimulacao;
            this.valorDesejado = valorDesejado;
            this.prazo = prazo;
            this.valorTotalParcelas = valorTotalParcelas;
        }
        
        // Getters and Setters
        public Long getIdSimulacao() {
            return idSimulacao;
        }
        
        public void setIdSimulacao(Long idSimulacao) {
            this.idSimulacao = idSimulacao;
        }
        
        public BigDecimal getValorDesejado() {
            return valorDesejado;
        }
        
        public void setValorDesejado(BigDecimal valorDesejado) {
            this.valorDesejado = valorDesejado;
        }
        
        public Integer getPrazo() {
            return prazo;
        }
        
        public void setPrazo(Integer prazo) {
            this.prazo = prazo;
        }
        
        public BigDecimal getValorTotalParcelas() {
            return valorTotalParcelas;
        }
        
        public void setValorTotalParcelas(BigDecimal valorTotalParcelas) {
            this.valorTotalParcelas = valorTotalParcelas;
        }
    }
}
