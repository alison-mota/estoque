package br.com.estoque.estoque.venda

import br.com.estoque.estoque.cliente.ClienteRepository
import br.com.estoque.estoque.compartilhado.auxiliar.*
import br.com.estoque.estoque.empresa.EmpresaRepository
import br.com.estoque.estoque.produto.ProdutoRepository
import br.com.estoque.estoque.produto.grupo.GrupoRepository
import br.com.estoque.estoque.venda.produtos.ProdutosVendidosRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
class VendaControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val vendaRepository: VendaRepository,
    @Autowired private val empresaRepository: EmpresaRepository,
    @Autowired private val clienteRepository: ClienteRepository,
    @Autowired private val grupoRepository: GrupoRepository,
    @Autowired private val produtoRepository: ProdutoRepository,
    @Autowired private val produtosVendidosRepository: ProdutosVendidosRepository,
) {

    private val empresa = empresaRepository.save(instanciaEmpresa())
    private val cliente = clienteRepository.save(instanciaCliente(empresa))
    private val grupo = grupoRepository.save(instanciaGrupo(empresa))
    private val produto = produtoRepository.save(instanciaProduto(empresa, grupo))

    @Test
    fun `deve criar uma venda quando os dados da requisicao estiverem corretos`() {

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/venda")
                .accept(MediaType.APPLICATION_JSON)
                .content(instanciaVenda201Request())
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
    }

    @Test
    fun `nao deve criar uma venda quando os dados da requisicao estiverem invalidos`() {

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/venda")
                .accept(MediaType.APPLICATION_JSON)
                .content(instanciaVenda400Request())
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }
}