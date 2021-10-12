package br.com.estoque.estoque.cliente

import br.com.estoque.estoque.compartilhado.validadores.ExistsById
import br.com.estoque.estoque.compartilhado.validadores.Unico
import br.com.estoque.estoque.empresa.Empresa
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class ClienteRequest(
    @field:NotBlank val nome: String,
    @field:NotBlank @field:Email @field:Unico(fieldName = "email", entityClass = Cliente::class) val email: String,
    @field:NotNull @field:ExistsById(fieldName = "id", entityClass = Empresa::class) val empresaId: Long
) {
    fun toModel(empresa: Empresa): Cliente {
        return Cliente(nome, email, empresa)
    }
}
