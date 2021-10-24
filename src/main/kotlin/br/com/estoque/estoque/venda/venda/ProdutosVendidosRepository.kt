package br.com.estoque.estoque.venda.venda

import org.springframework.data.jpa.repository.JpaRepository

interface ProdutosVendidosRepository: JpaRepository<ProdutosVendidos, Long> {

}
