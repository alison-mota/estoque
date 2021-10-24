package br.com.estoque.estoque.produto.venda

import org.springframework.data.jpa.repository.JpaRepository

interface ProdutosVendidosRepository: JpaRepository<ProdutosVendidos, Long> {

}
