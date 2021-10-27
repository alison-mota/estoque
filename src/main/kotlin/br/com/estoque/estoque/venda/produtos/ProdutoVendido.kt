package br.com.estoque.estoque.venda.produtos

import br.com.estoque.estoque.empresa.Empresa
import br.com.estoque.estoque.produto.grupo.Grupo
import br.com.estoque.estoque.venda.Venda
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive
import javax.validation.constraints.PositiveOrZero

@Entity
class ProdutoVendido(
    @field:NotBlank val nome: String,
    @field:NotNull @field:PositiveOrZero val valorDoProduto: BigDecimal,
    @field:NotNull @field:PositiveOrZero val valorVendidoDeCadaProduto: BigDecimal,
    @field:NotNull @field:Positive val quantidadeVendida: Long,
    @field:NotNull val produtoAtivo: Boolean,
    @ManyToOne
    @field:NotNull val grupo: Grupo,
    @ManyToOne
    @field:NotNull val empresa: Empresa,
    val descricao: String?
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, unique = true)
    val id: Long? = null

    @Column(updatable = false)
    val dataTransacao: LocalDateTime = LocalDateTime.now()

    @ManyToOne(cascade = [CascadeType.ALL])
    var venda: Venda? = null

    @Column(updatable = false)
    val valorTotalDosProdutos: BigDecimal = this.valorDoProduto.multiply(BigDecimal(this.quantidadeVendida))
}
