package br.com.estoque.estoque.cliente

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ClienteRepository: JpaRepository<Cliente, Long> {
    fun findAllByEmpresaId(paginacao: Pageable, empresaId: Long): Page<Cliente>

}
