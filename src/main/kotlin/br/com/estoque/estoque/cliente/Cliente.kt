package br.com.estoque.estoque.cliente

import br.com.estoque.estoque.empresa.Empresa
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
class Cliente(
    @field:NotBlank val nome: String,
    @field:NotBlank @field:Email val email: String,
    @ManyToOne
    @field:NotNull val empresa: Empresa
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, unique = true)
    val id: Long? = null
    @Column(updatable = false)
    val dataCadastro: LocalDateTime = LocalDateTime.now()
    var ativo: Boolean = true
}