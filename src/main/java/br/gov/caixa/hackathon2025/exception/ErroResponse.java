package br.gov.caixa.hackathon2025.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

/** Classe padrão para respostas de erro da API. */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErroResponse {
    
    @JsonProperty("status")
    private int status;
    
    @JsonProperty("mensagem")
    private String mensagem;
    
    @JsonProperty("timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;
    
    @JsonProperty("path")
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
