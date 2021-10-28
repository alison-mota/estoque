package br.com.estoque.estoque.produto

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ProdutoRepository: JpaRepository<Produto, Long> {
    fun findByIdAndEmpresaId(idProduto: Long, empresaId: Long): Optional<Produto>

}
