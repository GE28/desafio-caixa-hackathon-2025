package br.gov.caixa.hackathon2025.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import br.gov.caixa.hackathon2025.dto.SimulacaoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class EventHubService {
    private static final Logger log = LoggerFactory.getLogger(EventHubService.class);
    
    private final ObjectMapper objectMapper;
    
    // Necessário para mockar
    public EventHubService() {
        this.objectMapper = null;
    }
    
    @Inject
    public EventHubService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    
    public void enviarSimulacao(SimulacaoResponse simulacao) {
        try {
            if (objectMapper != null) {
                String json = objectMapper.writeValueAsString(simulacao);
                log.info("Enviando simulação para EventHub: {}", json);
            } else {
                log.info("Enviando simulação para EventHub - ID: {}", simulacao.getIdSimulacao());
            }
            // TODO: Implementar integração real com EventHub quando disponível
            // Por enquanto, apenas log da simulação
        } catch (Exception e) {
            log.error("Erro ao enviar simulação para EventHub", e);
        }
    }
}
