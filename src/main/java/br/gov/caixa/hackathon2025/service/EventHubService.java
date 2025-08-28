package br.gov.caixa.hackathon2025.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import br.gov.caixa.hackathon2025.dto.SimulacaoResponse;
import br.gov.caixa.hackathon2025.config.EventHubConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class EventHubService {
    private static final Logger log = LoggerFactory.getLogger(EventHubService.class);
    
    private final ObjectMapper objectMapper;
    private final EventHubConfig.EventHubConnectionInfo connectionInfo;
    private Object producer; // Usando Object para evitar dependência direta
    
    // Necessário para mockar nos testes
    public EventHubService() {
        this.objectMapper = null;
        this.connectionInfo = null;
    }
    
    @Inject
    public EventHubService(ObjectMapper objectMapper, EventHubConfig.EventHubConnectionInfo connectionInfo) {
        this.objectMapper = objectMapper;
        this.connectionInfo = connectionInfo;
    }
    
    @PostConstruct
    public void init() {
        try {
            log.info("Inicializando EventHub Producer Client...");
            
            // Verifica se as classes do Azure EventHub estão no classpath
            Class.forName("com.azure.messaging.eventhubs.EventHubClientBuilder");
            
            // Usando reflection para evitar dependência direta em tempo de compilação
            Class<?> builderClass = Class.forName("com.azure.messaging.eventhubs.EventHubClientBuilder");
            Object builder = builderClass.getDeclaredConstructor().newInstance();
            
            // Chama connectionString(String)
            builder = builderClass.getMethod("connectionString", String.class)
                    .invoke(builder, connectionInfo.getConnectionString());
            
            // Chama buildProducerClient()
            producer = builderClass.getMethod("buildProducerClient").invoke(builder);
            
            log.info("EventHub Producer Client inicializado com sucesso");
        } catch (ClassNotFoundException e) {
            log.warn("Azure EventHub SDK não encontrado no classpath, usando modo fallback");
            producer = null;
        } catch (Exception e) {
            log.error("Erro ao inicializar EventHub Producer Client", e);
            producer = null;
        }
    }
    
    @PreDestroy
    public void cleanup() {
        if (producer != null) {
            try {
                // Chama close() usando reflection
                producer.getClass().getMethod("close").invoke(producer);
                log.info("EventHub Producer Client fechado");
            } catch (Exception e) {
                log.warn("Erro ao fechar EventHub Producer Client", e);
            }
        }
    }
    
    public CompletableFuture<Void> enviarSimulacao(SimulacaoResponse simulacao) {
        try {
            if (objectMapper == null) {
                log.warn("ObjectMapper não disponível, usando fallback");
                return enviarSimulacaoFallback(simulacao);
            }
            
            String json = objectMapper.writeValueAsString(simulacao);
            
            if (producer != null) {
                return enviarParaEventHub(json, String.valueOf(simulacao.getIdSimulacao()));
            } else {
                log.warn("EventHub Producer não disponível, usando fallback");
                return enviarSimulacaoFallback(simulacao);
            }
            
        } catch (Exception e) {
            log.error("Erro ao processar simulação para envio", e);
            return CompletableFuture.failedFuture(e);
        }
    }
    
    private CompletableFuture<Void> enviarParaEventHub(String json, String idSimulacao) {
        return CompletableFuture.runAsync(() -> {
            try {
                // Usando reflection para criar EventData
                Class<?> eventDataClass = Class.forName("com.azure.messaging.eventhubs.EventData");
                Object eventData = eventDataClass.getConstructor(String.class).newInstance(json);
                
                // Adiciona propriedades ao evento
                Object properties = eventDataClass.getMethod("getProperties").invoke(eventData);
                properties.getClass().getMethod("put", Object.class, Object.class)
                        .invoke(properties, "idSimulacao", idSimulacao);
                properties.getClass().getMethod("put", Object.class, Object.class)
                        .invoke(properties, "timestamp", System.currentTimeMillis());
                properties.getClass().getMethod("put", Object.class, Object.class)
                        .invoke(properties, "source", "simulador-emprestimo");
                
                // Envia para o EventHub
                Class<?> listClass = java.util.List.class;
                java.util.List<Object> events = java.util.Collections.singletonList(eventData);
                producer.getClass().getMethod("send", listClass).invoke(producer, events);
                
                log.info("Simulação enviada para EventHub com sucesso - ID: {}", idSimulacao);
                
            } catch (Exception e) {
                log.error("Erro ao enviar simulação para EventHub - ID: {}", idSimulacao, e);
                throw new RuntimeException(e);
            }
        });
    }
    
    private CompletableFuture<Void> enviarSimulacaoFallback(SimulacaoResponse simulacao) {
        return CompletableFuture.runAsync(() -> {
            log.info("Fallback: Simulação processada - ID: {}, Produto: {}, Taxa: {}%", 
                    simulacao.getIdSimulacao(), 
                    simulacao.getDescricaoProduto(), 
                    simulacao.getTaxaJuros());
        });
    }
}
