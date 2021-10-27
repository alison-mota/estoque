package br.com.estoque.estoque.produto

import br.com.estoque.estoque.empresa.Empresa
import br.com.estoque.estoque.produto.grupo.Grupo
import br.com.estoque.estoque.venda.produtos.ProdutoRequestVenda
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive
import javax.validation.constraints.PositiveOrZero

@Entity
class Produto(
    @field:NotBlank val nome: String,
    val descricao: String? = null,
    @field:NotNull @field:PositiveOrZero val preco: BigDecimal,
    @field:NotNull @field:Positive var quantidadeEstoque: Long,
    @field:NotNull val controlaEstoque: Boolean,
    @ManyToOne
    @field:NotNull val grupo: Grupo,
    @ManyToOne
    @field:NotNull val empresa: Empresa
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, unique = true)
    val id: Long? = null

    @Column(updatable = false)
    val dataCadastro: LocalDateTime = LocalDateTime.now()
    val ativo: Boolean = true

    fun atualizaEstoque(produtos: Set<ProdutoRequestVenda>) {
        produtos.map {
            if (this.controlaEstoque) {
                val idProduto = this.id
                val produtoRequestVenda = produtos.filter { it.idProduto == idProduto }[0]

                if (this.quantidadeEstoque < it.quantidadeVendido || this.quantidadeEstoque.equals(0)) {
                    throw ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "O produto ${this.nome} não possui estoque suficiente."
                    )
                } else this.quantidadeEstoque = quantidadeEstoque - produtoRequestVenda.quantidadeVendido
            }
        }
    }
}