package br.gov.caixa.hackathon2025.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class EventHubConfig {
    private static final Logger log = LoggerFactory.getLogger(EventHubConfig.class);
    
    @ConfigProperty(name = "eventhub.connection-string")
    String connectionString;
    
    @ConfigProperty(name = "eventhub.entity-path", defaultValue = "simulacoes")
    String entityPath;
    
    @Produces
    @ApplicationScoped
    public EventHubConnectionInfo createEventHubConnectionInfo() {
        return new EventHubConnectionInfo(connectionString, entityPath);
    }
    
    public static class EventHubConnectionInfo {
        private final String connectionString;
        private final String entityPath;
        
        public EventHubConnectionInfo(String connectionString, String entityPath) {
            this.connectionString = connectionString;
            this.entityPath = entityPath;
        }
        
        public String getConnectionString() {
            return connectionString;
        }
        
        public String getEntityPath() {
            return entityPath;
        }
    }
}
