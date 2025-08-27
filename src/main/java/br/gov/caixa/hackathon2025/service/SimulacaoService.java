package br.gov.caixa.hackathon2025.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import br.gov.caixa.hackathon2025.dto.*;
import br.gov.caixa.hackathon2025.entity.Produto;
import br.gov.caixa.hackathon2025.entity.Simulacao;
import br.gov.caixa.hackathon2025.repository.ProdutoRepository;
import br.gov.caixa.hackathon2025.repository.SimulacaoRepository;
import br.gov.caixa.hackathon2025.service.emprestimo.CalculadorEmprestimo;
import br.gov.caixa.hackathon2025.service.emprestimo.CalculadorPrice;
import br.gov.caixa.hackathon2025.service.emprestimo.CalculadorSac;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class SimulacaoService {
    
    @Inject
    ProdutoRepository produtoRepository;
    
    @Inject
    SimulacaoRepository simulacaoRepository;
    
    @Inject
    EventHubService eventHubService;
    
    @Transactional
    public SimulacaoResponse simularEmprestimo(SimulacaoRequest request) {
        long inicioTempo = System.currentTimeMillis();
        
        // Buscar produto válido
        Optional<Produto> produtoOpt = produtoRepository.findProdutoValido(request.getValorDesejado(), request.getPrazo());
        
        if (produtoOpt.isEmpty()) {
            throw new IllegalArgumentException("Nenhum produto encontrado para os parâmetros informados");
        }
        
        Produto produto = produtoOpt.get();
        
        // Criar calculadoras SAC e PRICE
        CalculadorEmprestimo calculadorSac = new CalculadorSac(
            request.getValorDesejado(), 
            produto.getPercentualTaxaJuros(), 
            request.getPrazo()
        );
        
        CalculadorEmprestimo calculadorPrice = new CalculadorPrice(
            request.getValorDesejado(), 
            produto.getPercentualTaxaJuros(), 
            request.getPrazo()
        );
        
        // Calcular parcelas
        List<ParcelaDto> parcelasSac = calculadorSac.calcularParcelas();
        List<ParcelaDto> parcelasPrice = calculadorPrice.calcularParcelas();
        
        // Calcular valor total das parcelas (usando SAC como base)
        BigDecimal valorTotalParcelas = parcelasSac.stream()
            .map(ParcelaDto::getValorPrestacao)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // Persistir simulação
        Simulacao simulacao = new Simulacao();
        simulacao.setCodigoProduto(produto.getCodigoProduto());
        simulacao.setValorDesejado(request.getValorDesejado());
        simulacao.setPrazo(request.getPrazo());
        simulacao.setTaxaJuros(produto.getPercentualTaxaJuros());
        simulacao.setValorTotalParcelas(valorTotalParcelas);
        simulacao.setTempoRespostaMilissegundos(System.currentTimeMillis() - inicioTempo);
        
        simulacaoRepository.persist(simulacao);
        
        // Criar response
        SimulacaoResponse response = new SimulacaoResponse();
        response.setIdSimulacao(simulacao.getIdSimulacao());
        response.setCodigoProduto(produto.getCodigoProduto());
        response.setDescricaoProduto(produto.getNomeProduto());
        response.setTaxaJuros(produto.getPercentualTaxaJuros());
        
        List<ResultadoSimulacaoDto> resultados = new ArrayList<>();
        resultados.add(new ResultadoSimulacaoDto("SAC", parcelasSac));
        resultados.add(new ResultadoSimulacaoDto("PRICE", parcelasPrice));
        response.setResultadoSimulacao(resultados);
        
        // Enviar para EventHub (assíncrono)
        eventHubService.enviarSimulacao(response);
        
        return response;
    }
    
    public ListaSimulacaoResponse listarSimulacoes(Integer pagina, Integer tamanho) {
        if (pagina == null || pagina < 1) pagina = 1;
        if (tamanho == null || tamanho < 1) tamanho = 200;
        
        List<Simulacao> simulacoes = simulacaoRepository.findAllWithPagination(pagina - 1, tamanho);
        long total = simulacaoRepository.countAll();
        
        List<ListaSimulacaoResponse.SimulacaoResumo> resumos = simulacoes.stream()
            .map(s -> new ListaSimulacaoResponse.SimulacaoResumo(
                s.getIdSimulacao(),
                s.getValorDesejado(),
                s.getPrazo(),
                s.getValorTotalParcelas()
            ))
            .toList();
        
        ListaSimulacaoResponse response = new ListaSimulacaoResponse();
        response.setPagina(pagina);
        response.setQtdRegistros(total);
        response.setQtdRegistrosPagina(resumos.size());
        response.setRegistros(resumos);
        
        return response;
    }
}
