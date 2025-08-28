package br.gov.caixa.hackathon2025.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class TelemetriaResponse {
    @JsonProperty("dataReferencia")
    private LocalDate dataReferencia;
    
    @JsonProperty("listaEndpoints")
    private List<EndpointMetrica> listaEndpoints;
    
    // Getters and Setters
    public LocalDate getDataReferencia() {
        return dataReferencia;
    }
    
    public void setDataReferencia(LocalDate dataReferencia) {
        this.dataReferencia = dataReferencia;
    }
    
    public List<EndpointMetrica> getListaEndpoints() {
        return listaEndpoints;
    }
    
    public void setListaEndpoints(List<EndpointMetrica> listaEndpoints) {
        this.listaEndpoints = listaEndpoints;
    }
    
    public static class EndpointMetrica {
        @JsonProperty("nomeApi")
        private String nomeApi;
        
        @JsonProperty("qtdRequisicoes")
        private Long qtdRequisicoes;
        
        @JsonProperty("tempoMedio")
        private Long tempoMedio;
        
        @JsonProperty("tempoMinimo")
        private Long tempoMinimo;
        
        @JsonProperty("tempoMaximo")
        private Long tempoMaximo;
        
        @JsonProperty("percentualSucesso")
        private BigDecimal percentualSucesso;
        
        // Constructors
        public EndpointMetrica() {}
        
        public EndpointMetrica(String nomeApi, Long qtdRequisicoes, Long tempoMedio, 
                              Long tempoMinimo, Long tempoMaximo, BigDecimal percentualSucesso) {
            this.nomeApi = nomeApi;
            this.qtdRequisicoes = qtdRequisicoes;
            this.tempoMedio = tempoMedio;
            this.tempoMinimo = tempoMinimo;
            this.tempoMaximo = tempoMaximo;
            this.percentualSucesso = percentualSucesso;
        }
        
        // Getters and Setters
        public String getNomeApi() {
            return nomeApi;
        }
        
        public void setNomeApi(String nomeApi) {
            this.nomeApi = nomeApi;
        }
        
        public Long getQtdRequisicoes() {
            return qtdRequisicoes;
        }
        
        public void setQtdRequisicoes(Long qtdRequisicoes) {
            this.qtdRequisicoes = qtdRequisicoes;
        }
        
        public Long getTempoMedio() {
            return tempoMedio;
        }
        
        public void setTempoMedio(Long tempoMedio) {
            this.tempoMedio = tempoMedio;
        }
        
        public Long getTempoMinimo() {
            return tempoMinimo;
        }
        
        public void setTempoMinimo(Long tempoMinimo) {
            this.tempoMinimo = tempoMinimo;
        }
        
        public Long getTempoMaximo() {
            return tempoMaximo;
        }
        
        public void setTempoMaximo(Long tempoMaximo) {
            this.tempoMaximo = tempoMaximo;
        }
        
        public BigDecimal getPercentualSucesso() {
            return percentualSucesso;
        }
        
        public void setPercentualSucesso(BigDecimal percentualSucesso) {
            this.percentualSucesso = percentualSucesso;
        }
    }
}
