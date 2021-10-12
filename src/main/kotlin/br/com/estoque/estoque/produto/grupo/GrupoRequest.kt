package br.com.estoque.estoque.produto.grupo

import br.com.estoque.estoque.compartilhado.validadores.ExistsById
import br.com.estoque.estoque.empresa.Empresa
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class GrupoRequest(
    @field:NotBlank val nome: String,
    @field:NotNull @field:ExistsById(fieldName = "id", entityClass = Empresa::class) val empresaId: Long
) {
    fun toModel(empresa: Empresa): Grupo {
        return Grupo(nome, empresa)
    }

}
