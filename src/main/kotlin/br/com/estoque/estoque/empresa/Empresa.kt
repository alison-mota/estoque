package br.com.estoque.estoque.empresa

import org.hibernate.validator.constraints.br.CNPJ
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
class Empresa (
    @field:NotBlank val nomeFantasia: String,
    @field:NotBlank val razaoSocial: String,
    @field:CNPJ val cnpj: String,
    val inscricaoEstadual: Long
        ){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, unique = true)
    val id: Long? = null
    @Column(updatable = false)
    val dataCadastro: LocalDateTime = LocalDateTime.now()
    val ativo: Boolean = true
}