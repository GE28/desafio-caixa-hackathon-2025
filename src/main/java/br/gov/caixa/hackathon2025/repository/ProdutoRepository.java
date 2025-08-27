package br.gov.caixa.hackathon2025.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import br.gov.caixa.hackathon2025.entity.Produto;
import java.math.BigDecimal;
import java.util.Optional;

@ApplicationScoped
public class ProdutoRepository implements PanacheRepository<Produto> {
    
    public Optional<Produto> findProdutoValido(BigDecimal valorDesejado, Integer prazo) {
        return find("valorMinimo <= ?1 and (valorMaximo is null or valorMaximo >= ?1) " +
                   "and numeroMinimoMeses <= ?2 and (numeroMaximoMeses is null or numeroMaximoMeses >= ?2)",
                   valorDesejado, prazo)
                .firstResultOptional();
    }
}
