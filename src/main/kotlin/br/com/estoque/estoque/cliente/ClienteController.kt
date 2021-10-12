package br.com.estoque.estoque.cliente

import br.com.estoque.estoque.empresa.EmpresaRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import javax.validation.Valid

@RestController
@RequestMapping("/api/cliente")
class ClienteController(
    private val clienteRepository: ClienteRepository,
    private val empresaRepository: EmpresaRepository
) {

    @PostMapping
    fun novo(
        @Valid @RequestBody clienteRequest: ClienteRequest,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<Any> {

        val empresa = empresaRepository.findById(clienteRequest.empresaId).get()
        val cliente: Cliente = clienteRequest.toModel(empresa)
        clienteRepository.save(cliente)

        val location: URI = uriBuilder
            .path("/api/cliente/{id}")
            .buildAndExpand(cliente.id)
            .toUri()

        return ResponseEntity.created(location).build()
    }

    @GetMapping
    fun lista(paginacao: Pageable): ResponseEntity<Page<ClienteResponse>> {

        return ResponseEntity.ok(
            ClienteResponse
                .of(clienteRepository.findAll(paginacao))
        )
    }
}