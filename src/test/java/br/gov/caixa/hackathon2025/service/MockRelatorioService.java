package br.gov.caixa.hackathon2025.service;

import io.quarkus.test.Mock;
import jakarta.enterprise.context.ApplicationScoped;
import br.gov.caixa.hackathon2025.dto.TelemetriaResponse;
import br.gov.caixa.hackathon2025.dto.VolumeSimulacaoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Mock
@ApplicationScoped
public class MockRelatorioService extends RelatorioService {
    private static final Logger log = LoggerFactory.getLogger(MockRelatorioService.class);
    
    public MockRelatorioService() {
        super();
    }
    
    @Override
    public VolumeSimulacaoResponse obterVolumeSimulacaoPorData(LocalDate data) {
        log.info("Mock: Obtendo volume de simulações para a data: {}", data);
        
        // Mock data - simulando alguns dados de volume
        VolumeSimulacaoResponse.SimulacaoPorProduto simulacaoMock = new VolumeSimulacaoResponse.SimulacaoPorProduto();
        simulacaoMock.setCodigoProduto(1001);
        simulacaoMock.setDescricaoProduto("Produto Mock 1001");
        simulacaoMock.setTaxaMediaJuro(new BigDecimal("0.15000"));
        simulacaoMock.setValorMedioPrestacao(new BigDecimal("200.00"));
        simulacaoMock.setValorTotalDesejado(new BigDecimal("5000.00"));
        simulacaoMock.setValorTotalCredito(new BigDecimal("6000.00"));
        
        VolumeSimulacaoResponse response = new VolumeSimulacaoResponse();
        response.setDataReferencia(data);
        response.setSimulacoes(List.of(simulacaoMock));
        
        return response;
    }
    
    @Override
    public TelemetriaResponse obterTelemetria(LocalDate data) {
        log.info("Mock: Obtendo telemetria para a data: {}", data);
        
        // Mock data - simulando dados de telemetria
        TelemetriaResponse.EndpointMetrica metricaMock = new TelemetriaResponse.EndpointMetrica(
            "Simulacao Mock", 
            50L,  // qtdRequisicoes
            120L, // tempoMedio em ms
            80L,  // tempoMinimo em ms
            200L, // tempoMaximo em ms
            new BigDecimal("0.99") // percentualSucesso
        );
        
        TelemetriaResponse response = new TelemetriaResponse();
        response.setDataReferencia(data);
        response.setListaEndpoints(List.of(metricaMock));
        
        return response;
    }
}
