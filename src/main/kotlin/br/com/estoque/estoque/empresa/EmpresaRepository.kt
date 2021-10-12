package br.com.estoque.estoque.empresa

import org.springframework.data.jpa.repository.JpaRepository

interface EmpresaRepository: JpaRepository<Empresa, Long> {

}
