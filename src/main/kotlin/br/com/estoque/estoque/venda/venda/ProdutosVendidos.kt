package br.com.estoque.estoque.venda.venda

import br.com.estoque.estoque.empresa.Empresa
import br.com.estoque.estoque.produto.grupo.Grupo
import br.com.estoque.estoque.venda.Venda
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.PositiveOrZero

@Entity
class ProdutosVendidos(
    @field:NotBlank val nome: String,
    @field:NotNull @field:PositiveOrZero val valor: BigDecimal,
    @field:NotNull @field:PositiveOrZero val valorVendido: BigDecimal,
    @field:NotNull @field:PositiveOrZero val quantidadeVendida: Long,
    @field:NotNull val produtoAtivo: Boolean,
    @ManyToOne
    @field:NotNull val grupo: Grupo,
    @ManyToOne
    @field:NotNull val empresa: Empresa,
    @ManyToOne
    @field:NotNull val venda: Venda,
    val descricao: String?
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, unique = true)
    val id: Long? = null

    @Column(updatable = false)
    val dataTransacao: LocalDateTime = LocalDateTime.now()

}
