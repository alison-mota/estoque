package br.com.estoque.estoque.empresa

import br.com.estoque.estoque.compartilhado.validadores.Unico
import org.hibernate.validator.constraints.br.CNPJ
import javax.validation.constraints.NotBlank

data class EmpresaRequest (
    @field:NotBlank val nomeFantasia: String,
    @field:NotBlank val razaoSocial: String,
    @field:CNPJ @field:Unico(fieldName = "cnpj", entityClass = Empresa::class) val cnpj: String,
    val inscricaoEstadual: String
        ){
    fun toModel(): Empresa {
        return Empresa(nomeFantasia, razaoSocial, cnpj, inscricaoEstadual)
    }
}
