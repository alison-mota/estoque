package br.com.estoque.estoque.venda

import br.com.estoque.estoque.empresa.Empresa
import br.com.estoque.estoque.produto.Produto
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.PositiveOrZero
import javax.validation.constraints.Size

@Entity
class Venda(
    @field:NotNull @field:PositiveOrZero val valorDosProdutos: BigDecimal,
    @field:NotNull @field:PositiveOrZero val valorFinal: BigDecimal,
    val desconto: Long,
    @OneToMany
    @field:NotNull @field:Size(min = 1) val produtos: List<Produto> = ArrayList(),
    @ManyToOne
    @field:NotNull val empresa: Empresa
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, unique = true)
    val id: Long? = null

    @Column(updatable = false)
    val dataTransacao: LocalDateTime = LocalDateTime.now()
    val ativo: Boolean = true
}