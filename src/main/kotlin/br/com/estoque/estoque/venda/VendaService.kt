package br.com.estoque.estoque.venda

import br.com.estoque.estoque.empresa.Empresa
import br.com.estoque.estoque.produto.Produto
import br.com.estoque.estoque.produto.ProdutoRepository
import br.com.estoque.estoque.venda.produtos.ProdutoRequestVenda
import br.com.estoque.estoque.venda.produtos.ProdutoVendido
import br.com.estoque.estoque.venda.produtos.ProdutosVendidosRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.math.BigDecimal

@Service
class VendaService(
    private val produtoRepository: ProdutoRepository,
    private val produtosVendidosRepository: ProdutosVendidosRepository
) {

    @Transactional
    fun processa(vendaRequest: VendaRequest, empresa: Empresa): Venda {
        if(!validaValorDaVenda(vendaRequest))
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "O valor da venda não condiz com o valor total dos produtos.")
        val produtos = localizaProdutos(vendaRequest.produtos)
        produtos.map { it.atualizaEstoque(vendaRequest.produtos) }
        val produtosVendidos: List<ProdutoVendido> = instanciaProdutosVendidos(produtos, vendaRequest.produtos)
        val venda = vendaRequest.toModel(produtosVendidos, empresa)
        setVendaDeCadaProduto(produtosVendidos, venda)

        return venda
    }

    private fun validaValorDaVenda(vendaRequest: VendaRequest): Boolean {
        var valorVendido: BigDecimal = BigDecimal.ZERO
        vendaRequest.produtos.map {
            valorVendido += (it.valorVendido.multiply(it.quantidadeVendido.toBigDecimal()))
        }
        return vendaRequest.valorFinal == valorVendido
    }

    private fun localizaProdutos(produtosRequestVenda: Set<ProdutoRequestVenda>): List<Produto> {

        val produtos: List<Produto> = produtosRequestVenda.map {
            produtoRepository.findById(it.idProduto).orElseGet {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "Produto com id ${it.idProduto} não encontrado.")
            }
        }.toList()

        if (produtos.isEmpty()) throw ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "É preciso informar ao menos 1 produto"
        )

        return produtos
    }

    private fun instanciaProdutosVendidos(
        produtos: List<Produto>,
        produtosRequestVenda: Set<ProdutoRequestVenda>
    ): List<ProdutoVendido> {
        return produtos.map {
            val idProduto = it.id
            val produtoRequestVenda = produtosRequestVenda.filter { it.idProduto == idProduto }[0]
            produtosVendidosRepository.save(
                ProdutoVendido(
                    it.nome,
                    it.preco,
                    produtoRequestVenda.valorVendido,
                    produtoRequestVenda.quantidadeVendido,
                    it.ativo,
                    it.grupo,
                    it.empresa,
                    it.descricao
                )
            )
        }
    }

    private fun setVendaDeCadaProduto(produtosVendidos: List<ProdutoVendido>, venda: Venda){
        produtosVendidos.map { it.venda = venda }
    }

}
