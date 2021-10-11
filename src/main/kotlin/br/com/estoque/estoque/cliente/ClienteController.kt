package br.com.estoque.estoque.cliente

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/cliente")
class ClienteController {

    @PostMapping
    fun novo(@Valid @RequestBody clienteRequest: ClienteRequest){

    }
}