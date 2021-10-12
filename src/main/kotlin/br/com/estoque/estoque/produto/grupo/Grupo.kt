package br.com.estoque.estoque.produto.grupo

import br.com.estoque.estoque.empresa.Empresa
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
class Grupo(
    @field:NotBlank val nome: String,
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