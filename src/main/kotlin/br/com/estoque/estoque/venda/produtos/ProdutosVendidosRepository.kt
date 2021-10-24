package br.com.estoque.estoque.venda.produtos

import org.springframework.data.jpa.repository.JpaRepository

interface ProdutosVendidosRepository: JpaRepository<ProdutosVendidos, Long> {

}
