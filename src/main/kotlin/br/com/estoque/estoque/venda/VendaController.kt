package br.com.estoque.estoque.venda

import br.com.estoque.estoque.cliente.Cliente
import br.com.estoque.estoque.cliente.ClienteRepository
import br.com.estoque.estoque.empresa.Empresa
import br.com.estoque.estoque.empresa.EmpresaRepository
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
@RequestMapping("/api/venda")
class VendaController(
    private val vendaService: VendaService,
    private val empresaRepository: EmpresaRepository,
    private val clienteRepository: ClienteRepository
) {

    @PostMapping
    fun nova(
        @Valid @RequestBody vendaRequest: VendaRequest,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<URI> {

        val empresa: Empresa = empresaRepository.findById(vendaRequest.empresa).orElseThrow()
        val cliente: Cliente = clienteRepository.findById(vendaRequest.cliente).orElseThrow()
        val venda: Venda = vendaService.processa(vendaRequest, empresa, cliente)

        val location: URI = uriBuilder
            .path("/api/venda/{id}")
            .buildAndExpand(venda.id)
            .toUri()

        return ResponseEntity.created(location).build()
    }
}