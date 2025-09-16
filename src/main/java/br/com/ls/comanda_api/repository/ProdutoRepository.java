package br.com.ls.comanda_api.repository;

import br.com.ls.comanda_api.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
