package br.gov.caixa.hackathon2025.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import br.gov.caixa.hackathon2025.dto.*;
import br.gov.caixa.hackathon2025.entity.Produto;
import br.gov.caixa.hackathon2025.entity.Simulacao;
import br.gov.caixa.hackathon2025.exception.BaseException;
import br.gov.caixa.hackathon2025.exception.EntradaInvalidaException;
import br.gov.caixa.hackathon2025.exception.CalculoException;
import br.gov.caixa.hackathon2025.exception.ConsultaInvalidaException;
import br.gov.caixa.hackathon2025.exception.ProdutoNaoEncontradoException;
import br.gov.caixa.hackathon2025.repository.ProdutoRepository;
import br.gov.caixa.hackathon2025.repository.SimulacaoRepository;
import br.gov.caixa.hackathon2025.service.emprestimo.CalculadorEmprestimo;
import br.gov.caixa.hackathon2025.service.emprestimo.CalculadorPrice;
import br.gov.caixa.hackathon2025.service.emprestimo.CalculadorSac;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class SimulacaoService {
    private static final Logger log = LoggerFactory.getLogger(SimulacaoService.class);
    
    private final ProdutoRepository produtoRepository;
    private final SimulacaoRepository simulacaoRepository;
    private final EventHubService eventHubService;
    
    @Inject
    public SimulacaoService(ProdutoRepository produtoRepository, 
                           SimulacaoRepository simulacaoRepository,
                           EventHubService eventHubService) {
        this.produtoRepository = produtoRepository;
        this.simulacaoRepository = simulacaoRepository;
        this.eventHubService = eventHubService;
    }
    
    @Transactional
    public SimulacaoResponse simularEmprestimo(SimulacaoRequest request) {
        long inicioTempo = System.currentTimeMillis();
        
        validarParametrosSimulacao(request);
        
        Optional<Produto> produtoOpt = produtoRepository.findProdutoValido(request.getValorDesejado(), request.getPrazo());
        
        if (produtoOpt.isEmpty()) {
            throw new ProdutoNaoEncontradoException("Nenhum produto encontrado para os parâmetros informados: valor=" +
                request.getValorDesejado() + ", prazo=" + request.getPrazo() + " meses");
        }
        
        Produto produto = produtoOpt.get();
        
        try {
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
            
            List<ParcelaDto> parcelasSac = calculadorSac.calcularParcelas();
            List<ParcelaDto> parcelasPrice = calculadorPrice.calcularParcelas();
            
            if (parcelasSac.isEmpty() || parcelasPrice.isEmpty()) {
                throw new CalculoException("Erro ao calcular as parcelas do empréstimo");
            }
            
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
            
            SimulacaoResponse response = new SimulacaoResponse();
            response.setIdSimulacao(simulacao.getIdSimulacao());
            response.setCodigoProduto(produto.getCodigoProduto());
            response.setDescricaoProduto(produto.getNomeProduto());
            response.setTaxaJuros(produto.getPercentualTaxaJuros());
            
            List<ResultadoSimulacaoDto> resultados = new ArrayList<>();
            resultados.add(new ResultadoSimulacaoDto("SAC", parcelasSac));
            resultados.add(new ResultadoSimulacaoDto("PRICE", parcelasPrice));
            response.setResultadoSimulacao(resultados);
            
            // EventHub (assíncrono) - fire and forget
            eventHubService.enviarSimulacao(response)
                .whenComplete((result, throwable) -> {
                    if (throwable != null) {
                        log.warn("Erro ao enviar simulação para EventHub: {}", throwable.getMessage());
                    } else {
                        log.debug("Simulação enviada para EventHub com sucesso - ID: {}", response.getIdSimulacao());
                    }
                });
            
            return response;
            
        } catch (Exception e) {
            if (e instanceof BaseException) {
                throw e;
            }
            throw new CalculoException("Erro interno ao processar a simulação: " + e.getMessage(), e);
        }
    }
    
    private void validarParametrosSimulacao(SimulacaoRequest request) {
        if (request.getValorDesejado() == null || request.getValorDesejado().compareTo(BigDecimal.ZERO) <= 0) {
            throw new EntradaInvalidaException("Valor desejado deve ser maior que zero");
        }
        
        if (request.getPrazo() == null || request.getPrazo() <= 0) {
            throw new EntradaInvalidaException("Prazo deve ser maior que zero");
        }
        
        if (request.getValorDesejado().compareTo(new BigDecimal("999999999999999")) > 0) {
            throw new EntradaInvalidaException("Valor desejado não pode ser superior a R$ 999.999.999.999.999,00");
        }
        
        if (request.getPrazo() > 480) {
            throw new EntradaInvalidaException("Prazo não pode ser superior a 480 meses");
        }
    }
    
    public ListaSimulacaoResponse listarSimulacoes(Integer pagina, Integer tamanho) {
        if (pagina != null && pagina < 1) {
            throw new ConsultaInvalidaException("Número da página deve ser maior que zero");
        }

        if (tamanho != null && tamanho < 1) {
            throw new ConsultaInvalidaException("Tamanho da página deve ser maior que zero");
        }
        
        if (tamanho != null && tamanho > 1000) {
            throw new ConsultaInvalidaException("Tamanho da página não pode ser superior a 1000 registros");
        }
        
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
