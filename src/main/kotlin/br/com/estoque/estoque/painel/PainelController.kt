package br.com.estoque.estoque.painel

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/painel")
class PainelController {

    @RequestMapping("/")
    fun index(): String {
        return "index"
    }

}