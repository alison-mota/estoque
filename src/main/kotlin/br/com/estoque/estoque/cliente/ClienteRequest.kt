package br.com.estoque.estoque.cliente

import javax.validation.constraints.NotBlank

data class ClienteRequest(
        @field:NotBlank val nome: String,
        @field:NotBlank val email: String,
) {

}
