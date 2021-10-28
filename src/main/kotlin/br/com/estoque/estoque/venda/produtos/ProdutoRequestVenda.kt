package br.com.estoque.estoque.venda.produtos

import java.math.BigDecimal
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive
import javax.validation.constraints.PositiveOrZero
import javax.validation.constraints.Size
import kotlin.math.min

data class ProdutoRequestVenda(
    @field:NotNull val idProduto: Long,
    @field:NotNull @field:Positive val quantidadeVendido: Long,
    @field:NotNull @field:PositiveOrZero val valorDeCadaProduto: BigDecimal,
)
