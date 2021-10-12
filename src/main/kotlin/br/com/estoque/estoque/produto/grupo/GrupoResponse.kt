package br.com.estoque.estoque.produto.grupo

data class GrupoResponse(
    val nome: String
){
    companion object {
        fun of(livros: List<Grupo>): List<GrupoResponse> =
            livros.map { GrupoResponse(nome = it.nome) }
    }
}
