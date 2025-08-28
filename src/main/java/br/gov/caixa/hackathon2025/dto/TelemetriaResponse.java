package br.gov.caixa.hackathon2025.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Schema(description = "Dados de telemetria dos endpoints da API")
public class TelemetriaResponse {
    @JsonProperty("dataReferencia")
    @Schema(description = "Data de referência", example = "2025-01-15")
    private LocalDate dataReferencia;
    
    @JsonProperty("listaEndpoints")
    @Schema(description = "Métricas de cada endpoint")
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
    
    @Schema(description = "Métricas de um endpoint")
    public static class EndpointMetrica {
        @JsonProperty("nomeApi")
        @Schema(description = "Nome do endpoint", example = "/simulacao")
        private String nomeApi;
        
        @JsonProperty("qtdRequisicoes")
        @Schema(description = "Quantidade de requisições", example = "1500")
        private Long qtdRequisicoes;
        
        @JsonProperty("tempoMedio")
        @Schema(description = "Tempo médio de resposta em ms", example = "250")
        private Long tempoMedio;
        
        @JsonProperty("tempoMinimo")
        @Schema(description = "Tempo mínimo de resposta em ms", example = "50")
        private Long tempoMinimo;
        
        @JsonProperty("tempoMaximo")
        @Schema(description = "Tempo máximo de resposta em ms", example = "1200")
        private Long tempoMaximo;
        
        @JsonProperty("percentualSucesso")
        @Schema(description = "Percentual de sucesso", example = "99.8")
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
