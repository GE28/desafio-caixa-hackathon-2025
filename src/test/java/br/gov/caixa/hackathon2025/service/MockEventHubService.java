package br.gov.caixa.hackathon2025.service;

import io.quarkus.test.Mock;
import jakarta.enterprise.context.ApplicationScoped;
import br.gov.caixa.hackathon2025.dto.SimulacaoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

@Mock
@ApplicationScoped
public class MockEventHubService extends EventHubService {
    private static final Logger log = LoggerFactory.getLogger(MockEventHubService.class);
    
    // Construtor sem argumentos para o mock
    public MockEventHubService() {
        super();
    }
    
    @Override
    public CompletableFuture<Void> enviarSimulacao(SimulacaoResponse simulacao) {
        log.info("Mock: Simulação enviada para EventHub - ID: {}", simulacao.getIdSimulacao());
        // Mock implementation - apenas log da simulação para testes
        return CompletableFuture.completedFuture(null);
    }
}
