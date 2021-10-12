package br.com.estoque.estoque.produto.grupo

import br.com.estoque.estoque.empresa.EmpresaRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import javax.validation.Valid

@RestController
@RequestMapping("/api/produto/grupo")
class GrupoController(
    private val grupoRepository: GrupoRepository,
    private val empresaRepository: EmpresaRepository
) {

    @PostMapping
    fun novo(
        @Valid @RequestBody grupoRequest: GrupoRequest,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<Any> {
        val empresa = empresaRepository.findById(grupoRequest.empresaId).get()
        val grupo: Grupo = grupoRequest.toModel(empresa)
        grupoRepository.save(grupo)

        val location: URI = uriBuilder
            .path("/api/grupo/{id}")
            .buildAndExpand(grupo.id)
            .toUri()

        return ResponseEntity.created(location).build()
    }

    @GetMapping()
    fun listaGruposDaEmpresa(@RequestParam empresaId: Long): ResponseEntity<List<GrupoResponse>>{

        val grupos: List<Grupo> = grupoRepository.findAllByEmpresaId(empresaId)
        if(grupos.isEmpty()) throw ResponseStatusException(HttpStatus.NOT_FOUND, "Não existem grupos cadastrados para essa empresa")

        return ResponseEntity.ok(GrupoResponse.of(grupos))
    }
}