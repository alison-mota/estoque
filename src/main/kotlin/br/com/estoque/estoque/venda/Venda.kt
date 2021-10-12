package br.com.estoque.estoque.venda

import br.com.estoque.estoque.produto.Produto
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
class Venda(
    val valorSemDesconto: BigDecimal,
    val valorComDesconto: BigDecimal,
    val desconto: Long,
    @OneToMany
    @field:NotNull @field:Size(min = 1) val produtos: Set<Produto> = hashSetOf(),
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, unique = true)
    val id: Long? = null
    @Column(updatable = false)
    val dataCadastro: LocalDateTime = LocalDateTime.now()
    val ativo: Boolean = true
}