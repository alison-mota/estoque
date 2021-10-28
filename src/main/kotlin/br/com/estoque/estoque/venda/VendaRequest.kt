package br.com.estoque.estoque.venda

import br.com.estoque.estoque.cliente.Cliente
import br.com.estoque.estoque.compartilhado.validadores.ExistsById
import br.com.estoque.estoque.empresa.Empresa
import br.com.estoque.estoque.produto.Produto
import br.com.estoque.estoque.venda.produtos.ProdutoRequestVenda
import br.com.estoque.estoque.venda.produtos.ProdutoVendido
import java.math.BigDecimal
import javax.validation.constraints.NotNull
import javax.validation.constraints.PositiveOrZero
import javax.validation.constraints.Size

data class VendaRequest(
    @field:NotNull @field:PositiveOrZero val valorDosProdutos: BigDecimal,
    @field:NotNull @field:PositiveOrZero val valorFinal: BigDecimal,
    @field:NotNull @field:Size(min = 1) val produtos: Set<ProdutoRequestVenda> = hashSetOf(),
    @field:NotNull @field:ExistsById(fieldName = "id", entityClass = Empresa::class) val empresa: Long,
    @field:NotNull @field:ExistsById(fieldName = "id", entityClass = Cliente::class) val cliente: Long

) {
    fun toModel(produtos: List<ProdutoVendido>, empresa: Empresa, cliente: Cliente): Venda {
        return Venda(valorDosProdutos, valorFinal, produtos, empresa, cliente)
    }
}
