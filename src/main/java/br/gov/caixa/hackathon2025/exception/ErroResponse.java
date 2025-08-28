package br.gov.caixa.hackathon2025.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDateTime;

/** Classe padrão para respostas de erro da API. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Resposta de erro padrão da API")
public class ErroResponse {
    
    @JsonProperty("status")
    @Schema(description = "Código de status HTTP", example = "400")
    private int status;
    
    @JsonProperty("mensagem")
    @Schema(description = "Mensagem de erro", example = "Valor desejado é obrigatório")
    private String mensagem;
    
    @JsonProperty("timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Timestamp do erro", example = "2025-01-15T10:30:45")
    private LocalDateTime timestamp;
    
    @JsonProperty("path")
    @Schema(description = "Path da requisição que gerou o erro", example = "/simulacao")
    private String path;

    public ErroResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ErroResponse(int status, String mensagem) {
        this();
        this.status = status;
        this.mensagem = mensagem;
    }

    public ErroResponse(int status, String mensagem, LocalDateTime timestamp) {
        this.status = status;
        this.mensagem = mensagem;
        this.timestamp = timestamp != null ? timestamp : LocalDateTime.now();
    }

    public ErroResponse(int status, String mensagem, LocalDateTime timestamp, String path) {
        this.status = status;
        this.mensagem = mensagem;
        this.timestamp = timestamp != null ? timestamp : LocalDateTime.now();
        this.path = path;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
