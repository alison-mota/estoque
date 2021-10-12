package br.com.estoque.estoque.produto.grupo

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface GrupoRepository: JpaRepository<Grupo, Long> {
    fun findAllByEmpresaId(empresaId: Long): List<Grupo>
    fun findByIdAndEmpresaId(grupoId: Long, empresaId: Long): Optional<Grupo>

}
