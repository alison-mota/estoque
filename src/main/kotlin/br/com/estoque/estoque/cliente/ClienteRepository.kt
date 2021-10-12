package br.com.estoque.estoque.cliente

import org.springframework.data.jpa.repository.JpaRepository

interface ClienteRepository: JpaRepository<Cliente, Long> {

}
