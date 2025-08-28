package br.gov.caixa.hackathon2025.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.List;

@Schema(description = "Resposta paginada com lista de simulações")
public class ListaSimulacaoResponse {
    
    @JsonProperty("pagina")
    @Schema(description = "Número da página atual", example = "1")
    private Integer pagina;
    
    @JsonProperty("qtdRegistros")
    @Schema(description = "Quantidade total de registros", example = "150")
    private Long qtdRegistros;
    
    @JsonProperty("qtdRegistrosPagina")
    @Schema(description = "Quantidade de registros na página", example = "10")
    private Integer qtdRegistrosPagina;
    
    @JsonProperty("registros")
    @Schema(description = "Lista de simulações resumidas")
    private List<SimulacaoResumo> registros;
    
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
    
    @Schema(description = "Resumo de uma simulação")
    public static class SimulacaoResumo {
        @JsonProperty("idSimulacao")
        @Schema(description = "ID da simulação", example = "1")
        private Long idSimulacao;
        
        @JsonProperty("valorDesejado")
        @Schema(description = "Valor desejado", example = "50000.00")
        private BigDecimal valorDesejado;
        
        @JsonProperty("prazo")
        @Schema(description = "Prazo em meses", example = "24")
        private Integer prazo;
        
        @JsonProperty("valorTotalParcelas")
        @Schema(description = "Valor total das parcelas", example = "68000.00")
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
