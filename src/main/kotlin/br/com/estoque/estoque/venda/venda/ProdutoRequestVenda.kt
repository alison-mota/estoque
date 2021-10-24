package br.com.estoque.estoque.venda.venda

import java.math.BigDecimal
import javax.validation.constraints.NotNull

data class ProdutoRequestVenda(
    @field:NotNull val idProduto: Long,
    @field:NotNull val quantidadeVendido: Long,
    @field:NotNull val valorVendido: BigDecimal,

)
