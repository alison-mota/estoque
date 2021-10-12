package br.com.estoque.estoque.produto

import org.springframework.data.jpa.repository.JpaRepository

interface ProdutoRepository: JpaRepository<Produto, Long> {

}
