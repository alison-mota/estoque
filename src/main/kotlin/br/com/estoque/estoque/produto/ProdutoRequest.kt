package br.com.estoque.estoque.produto

import br.com.estoque.estoque.compartilhado.validadores.ExistsById
import br.com.estoque.estoque.empresa.Empresa
import br.com.estoque.estoque.produto.grupo.Grupo
import java.math.BigDecimal
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.PositiveOrZero

data class ProdutoRequest(
    @field:NotBlank val nome: String,
    val descricao: String,
    @field:NotNull @field:PositiveOrZero val preco: BigDecimal,
    @field:NotNull @field:PositiveOrZero val quantidadeEstoque: Long,
    @field:NotNull val controlaEstoque: Boolean,
    @field:NotNull @field:ExistsById(fieldName = "id", entityClass = Grupo::class) val grupo: Long,
    @field:NotNull @field:ExistsById(fieldName = "id", entityClass = Empresa::class) val empresa: Long
) {
    fun toModel(empresa: Empresa, grupo: Grupo): Produto {
        return Produto(nome, descricao, preco, quantidadeEstoque, controlaEstoque, grupo, empresa)
    }
}