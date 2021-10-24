package br.com.estoque.estoque.venda

import br.com.estoque.estoque.empresa.Empresa
import br.com.estoque.estoque.produto.Produto
import br.com.estoque.estoque.produto.ProdutoRepository
import br.com.estoque.estoque.venda.venda.ProdutoRequestVenda
import br.com.estoque.estoque.venda.venda.ProdutosVendidos
import br.com.estoque.estoque.venda.venda.ProdutosVendidosRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class VendaService(
    private val produtoRepository: ProdutoRepository,
    private val produtosVendidosRepository: ProdutosVendidosRepository,
    private val vendaRepository: VendaRepository
) {

    fun processa(vendaRequest: VendaRequest, empresa: Empresa): Venda {
        val produtos = localizaProdutos(vendaRequest.produtos)
        val venda = vendaRepository.save(vendaRequest.toModel(produtos, empresa))
        instanciaProdutosVendidos(produtos, vendaRequest.produtos, venda)
        return venda
    }

    private fun localizaProdutos(produtosRequestVenda: Set<ProdutoRequestVenda>): List<Produto> {

        val produtos: List<Produto> = produtosRequestVenda.map {
            produtoRepository.findById(it.idProduto).orElseGet {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "Produto com id $it não encontrado.")
            }
        }.toList()

        if (produtos.isEmpty()) throw ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "É preciso informar ao menos 1 produto"
        )

        return produtos
    }

    private fun instanciaProdutosVendidos(produtos: List<Produto>, produtosRequestVenda: Set<ProdutoRequestVenda>, venda: Venda) {
        produtos.map {
            val idProduto = it.id
            val produtoRequestVenda = produtosRequestVenda.filter { it.idProduto == idProduto }[0]
            produtosVendidosRepository.save(
                ProdutosVendidos(
                    it.nome,
                    it.preco,
                    produtoRequestVenda.valorVendido,
                    produtoRequestVenda.quantidadeVendido,
                    it.ativo,
                    it.grupo,
                    it.empresa,
                    venda,
                    it.descricao
                )
            )
        }
    }
}
