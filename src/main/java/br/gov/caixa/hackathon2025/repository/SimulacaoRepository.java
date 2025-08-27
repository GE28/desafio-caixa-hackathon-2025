package br.gov.caixa.hackathon2025.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import br.gov.caixa.hackathon2025.entity.Simulacao;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class SimulacaoRepository implements PanacheRepository<Simulacao> {
    
    public List<Simulacao> findSimulacoesPorData(LocalDate data) {
        LocalDateTime inicio = data.atStartOfDay();
        LocalDateTime fim = data.plusDays(1).atStartOfDay();
        return find("dataSimulacao >= ?1 and dataSimulacao < ?2", inicio, fim).list();
    }
    
    public List<Simulacao> findAllWithPagination(int page, int size) {
        return find("ORDER BY dataSimulacao DESC").page(page, size).list();
    }
    
    public long countAll() {
        return count();
    }
}
