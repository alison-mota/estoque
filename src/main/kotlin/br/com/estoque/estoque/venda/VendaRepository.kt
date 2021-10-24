package br.com.estoque.estoque.venda

import org.springframework.data.jpa.repository.JpaRepository

interface VendaRepository : JpaRepository<Venda, Long> {

}
