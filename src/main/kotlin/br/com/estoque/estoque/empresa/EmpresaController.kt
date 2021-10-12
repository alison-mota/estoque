package br.com.estoque.estoque.empresa

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import javax.validation.Valid

@RestController
@RequestMapping("/api/empresa")
class EmpresaController(private val empresaRepository: EmpresaRepository) {

    @PostMapping
    fun cria(
        @Valid @RequestBody empresaRequest: EmpresaRequest,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<Any> {
        val empresa = empresaRequest.toModel()
        empresaRepository.save(empresa)

        val location: URI = uriBuilder
            .path("/api/empresa/{id}")
            .buildAndExpand(empresa.id)
            .toUri()

        return ResponseEntity.created(location).build()
    }
}