package br.com.estoque.estoque.produto

import br.com.estoque.estoque.empresa.Empresa
import br.com.estoque.estoque.empresa.EmpresaRepository
import br.com.estoque.estoque.produto.grupo.GrupoRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import javax.validation.Valid

@RestController
@RequestMapping("/api/produto")
class ProdutoController(
    private val produtoRepository: ProdutoRepository,
    private val empresaRepository: EmpresaRepository,
    private val grupoRepository: GrupoRepository
) {

    @PostMapping
    fun novo(
        @Valid @RequestBody produtoRequest: ProdutoRequest,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<Any> {
        val empresa: Empresa = empresaRepository.findById(produtoRequest.empresa).get()
        val possivelGrupo = grupoRepository.findByIdAndEmpresaId(produtoRequest.grupo, produtoRequest.empresa)
        if(possivelGrupo.isEmpty) throw ResponseStatusException(HttpStatus.NOT_FOUND, "Grupo não encontrado nesta empresa")

        val produto: Produto = produtoRequest.toModel(empresa, possivelGrupo.get())
        produtoRepository.save(produto)

        val location: URI = uriBuilder
            .path("/api/produto/{id}")
            .buildAndExpand(produto.id)
            .toUri()

        return ResponseEntity.created(location).build()
    }
}