package br.com.estoque.estoque.cliente

import br.com.estoque.estoque.empresa.Empresa
import org.springframework.data.domain.Page

data class ClienteResponse(
    val nome: String,
    val email: String,
    val empresa: Empresa
) {

    companion object {
        fun of(clientes: Page<Cliente>): Page<ClienteResponse> =
            clientes.map { ClienteResponse(nome = it.nome, email = it.email, empresa = it.empresa) }
    }
}
