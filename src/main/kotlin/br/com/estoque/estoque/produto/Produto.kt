package br.com.estoque.estoque.produto

import br.com.estoque.estoque.empresa.Empresa
import br.com.estoque.estoque.produto.grupo.Grupo
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.PositiveOrZero

@Entity
class Produto(
    @field:NotBlank val nome: String,
    val descricao: String? = null,
    @field:NotNull @field:PositiveOrZero val preco: BigDecimal,
    @field:NotNull @field:PositiveOrZero val quantidadeEstoque: Long,
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
}