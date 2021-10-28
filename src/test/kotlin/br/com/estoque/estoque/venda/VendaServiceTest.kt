package br.com.estoque.estoque.venda

import br.com.estoque.estoque.cliente.ClienteRepository
import br.com.estoque.estoque.compartilhado.auxiliar.*
import br.com.estoque.estoque.empresa.EmpresaRepository
import br.com.estoque.estoque.produto.ProdutoRepository
import br.com.estoque.estoque.produto.grupo.GrupoRepository
import br.com.estoque.estoque.venda.produtos.ProdutosVendidosRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.server.ResponseStatusException

@SpringBootTest
internal class VendaServiceTest(
    @Autowired private val vendaRepository: VendaRepository,
    @Autowired private val empresaRepository: EmpresaRepository,
    @Autowired private val clienteRepository: ClienteRepository,
    @Autowired private val grupoRepository: GrupoRepository,
    @Autowired private val produtoRepository: ProdutoRepository,
    @Autowired private val produtosVendidosRepository: ProdutosVendidosRepository,
) {

    private val vendaService: VendaService = VendaService(produtoRepository, produtosVendidosRepository)

    private val empresa = empresaRepository.save(instanciaEmpresa())
    private val cliente = clienteRepository.save(instanciaCliente(empresa))
    private val grupo = grupoRepository.save(instanciaGrupo(empresa))
    private val produto = produtoRepository.save(instanciaProduto(empresa, grupo))

    @Test
    internal fun `deve retornar true quando a requisicao for valida`() {
        val vendaValidada =
            vendaService.validaVenda(empresa, cliente, instanciaVendaRequest(setOf(instanciaProdutoRequestVenda())))
        assertTrue(vendaValidada)
    }

    @Test
    internal fun `deve retornar 400 com uma resposta quando a empresa nao estiver ativa`() {

        val excecao = assertThrows(ResponseStatusException::class.java) {
            empresa.ativo = false
            vendaService.validaVenda(empresa, cliente, instanciaVendaRequest(setOf(instanciaProdutoRequestVenda())))
        }

        assertEquals("Esta empresa não está ativa.", excecao.reason)
    }

    @Test
    internal fun `deve retornar 400 com uma resposta quando o cliente nao estiver ativo`() {

        val excecao = assertThrows(ResponseStatusException::class.java) {
            cliente.ativo = false
            vendaService.validaVenda(empresa, cliente, instanciaVendaRequest(setOf(instanciaProdutoRequestVenda())))
        }

        assertEquals("Este cliente não está ativo.", excecao.reason)
    }

    @Test
    internal fun `deve retornar 400 com uma resposta quando o cliente for de outra empresa`() {

        val excecao = assertThrows(ResponseStatusException::class.java) {
            val empresa2 = empresaRepository.save(instanciaEmpresa())
            vendaService.validaVenda(empresa2, cliente, instanciaVendaRequest(setOf(instanciaProdutoRequestVenda())))
        }

        assertEquals("Este cliente não é desta empresa.", excecao.reason)
    }
}