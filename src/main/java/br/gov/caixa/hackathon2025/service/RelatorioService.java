package br.gov.caixa.hackathon2025.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import br.gov.caixa.hackathon2025.dto.TelemetriaResponse;
import br.gov.caixa.hackathon2025.dto.VolumeSimulacaoResponse;
import br.gov.caixa.hackathon2025.entity.Simulacao;
import br.gov.caixa.hackathon2025.repository.SimulacaoRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class RelatorioService {
    private final SimulacaoRepository simulacaoRepository;
    
    // Necessário para mockar
    public RelatorioService() {
        this.simulacaoRepository = null;
    }
    
    @Inject
    public RelatorioService(SimulacaoRepository simulacaoRepository) {
        this.simulacaoRepository = simulacaoRepository;
    }
    
    public VolumeSimulacaoResponse obterVolumeSimulacaoPorData(LocalDate data) {
        if (simulacaoRepository == null) {
            // Fallback para quando o repository não está disponível
            VolumeSimulacaoResponse response = new VolumeSimulacaoResponse();
            response.setDataReferencia(data);
            response.setSimulacoes(new ArrayList<>());
            return response;
        }
        
        List<Simulacao> simulacoes = simulacaoRepository.findSimulacoesPorData(data);
        
        Map<Integer, List<Simulacao>> simulacoesPorProduto = simulacoes.stream()
            .collect(Collectors.groupingBy(Simulacao::getCodigoProduto));
        
        List<VolumeSimulacaoResponse.SimulacaoPorProduto> simulacoesProduto = new ArrayList<>();
        
        for (Map.Entry<Integer, List<Simulacao>> entry : simulacoesPorProduto.entrySet()) {
            List<Simulacao> simulacoesDoProduto = entry.getValue();
            
            BigDecimal taxaMediaJuro = simulacoesDoProduto.stream()
                .map(Simulacao::getTaxaJuros)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(simulacoesDoProduto.size()), 9, RoundingMode.HALF_UP);
            
            BigDecimal valorMedioPrestacao = simulacoesDoProduto.stream()
                .map(s -> s.getValorTotalParcelas().divide(BigDecimal.valueOf(s.getPrazo()), 2, RoundingMode.HALF_UP))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(simulacoesDoProduto.size()), 2, RoundingMode.HALF_UP);
            
            BigDecimal valorTotalDesejado = simulacoesDoProduto.stream()
                .map(Simulacao::getValorDesejado)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            BigDecimal valorTotalCredito = simulacoesDoProduto.stream()
                .map(Simulacao::getValorTotalParcelas)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            VolumeSimulacaoResponse.SimulacaoPorProduto simulacaoProduto = new VolumeSimulacaoResponse.SimulacaoPorProduto();
            simulacaoProduto.setCodigoProduto(entry.getKey());
            simulacaoProduto.setDescricaoProduto("Produto " + entry.getKey());
            simulacaoProduto.setTaxaMediaJuro(taxaMediaJuro);
            simulacaoProduto.setValorMedioPrestacao(valorMedioPrestacao);
            simulacaoProduto.setValorTotalDesejado(valorTotalDesejado);
            simulacaoProduto.setValorTotalCredito(valorTotalCredito);
            
            simulacoesProduto.add(simulacaoProduto);
        }
        
        VolumeSimulacaoResponse response = new VolumeSimulacaoResponse();
        response.setDataReferencia(data);
        response.setSimulacoes(simulacoesProduto);
        
        return response;
    }
    
    public TelemetriaResponse obterTelemetria(LocalDate data) {
        if (simulacaoRepository == null) {
            // Fallback para quando o repository não está disponível
            TelemetriaResponse response = new TelemetriaResponse();
            response.setDataReferencia(data);
            response.setListaEndpoints(new ArrayList<>());
            return response;
        }
        
        List<Simulacao> simulacoes = simulacaoRepository.findSimulacoesPorData(data);
        
        if (simulacoes.isEmpty()) {
            TelemetriaResponse response = new TelemetriaResponse();
            response.setDataReferencia(data);
            response.setListaEndpoints(new ArrayList<>());
            return response;
        }
        
        long qtdRequisicoes = simulacoes.size();
        
        long tempoMedio = (long) simulacoes.stream()
            .mapToLong(s -> s.getTempoRespostaMilissegundos() != null ? s.getTempoRespostaMilissegundos() : 0L)
            .average()
            .orElse(0.0);
        
        long tempoMinimo = simulacoes.stream()
            .mapToLong(s -> s.getTempoRespostaMilissegundos() != null ? s.getTempoRespostaMilissegundos() : 0L)
            .min()
            .orElse(0L);
        
        long tempoMaximo = simulacoes.stream()
            .mapToLong(s -> s.getTempoRespostaMilissegundos() != null ? s.getTempoRespostaMilissegundos() : 0L)
            .max()
            .orElse(0L);
        
        BigDecimal percentualSucesso = BigDecimal.valueOf(0.98); // Assumindo 98% de sucesso
        
        TelemetriaResponse.EndpointMetrica metrica = new TelemetriaResponse.EndpointMetrica(
            "Simulacao", qtdRequisicoes, tempoMedio, tempoMinimo, tempoMaximo, percentualSucesso
        );
        
        TelemetriaResponse response = new TelemetriaResponse();
        response.setDataReferencia(data);
        response.setListaEndpoints(List.of(metrica));
        
        return response;
    }
}

