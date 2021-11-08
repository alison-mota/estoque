package br.com.estoque.estoque.venda

import br.com.estoque.estoque.cliente.Cliente
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
    fun processa(vendaRequest: VendaRequest, empresa: Empresa, cliente: Cliente): Venda {
        validaVenda(empresa, cliente, vendaRequest)
        val produtos = localizaProdutosDaEmpresa(vendaRequest.produtos, empresa.id!!)
        produtos.map { it.atualizaEstoque(vendaRequest.produtos) }
        val produtosVendidos: List<ProdutoVendido> = instanciaProdutosVendidos(produtos, vendaRequest.produtos)
        val venda = vendaRequest.toModel(produtosVendidos, empresa, cliente)
        setVendaDeCadaProduto(produtosVendidos, venda)

        return venda
    }

    fun validaVenda(
        empresa: Empresa,
        cliente: Cliente,
        vendaRequest: VendaRequest,
    ): Boolean {
        when {
            !empresa.ativo -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Esta empresa não está ativa.")
            !cliente.ativo -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Este cliente não está ativo.")
            cliente.empresa != empresa -> throw ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Este cliente não é desta empresa."
            )
            !validaValorDaVendaIgualValorDosProdutosVendidos(vendaRequest) -> throw ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Houve diferença entre o valor da venda com a soma do valor dos produtos."
            )
        }
        return true
    }

    private fun validaValorDaVendaIgualValorDosProdutosVendidos(vendaRequest: VendaRequest): Boolean {
        var valorVendido: BigDecimal = BigDecimal.ZERO
        vendaRequest.produtos.map {
            valorVendido += (it.valorDeCadaProduto.multiply(it.quantidadeVendido.toBigDecimal()))
        }
        return vendaRequest.valorFinal == valorVendido
    }

    private fun localizaProdutosDaEmpresa(
        produtosRequestVenda: Set<ProdutoRequestVenda>,
        empresaId: Long
    ): List<Produto> {

        val produtos: List<Produto> = produtosRequestVenda.map {
            produtoRepository.findByIdAndEmpresaId(it.idProduto, empresaId).orElseGet {
                throw ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Produto com id ${it.idProduto} não encontrado na empresa $empresaId."
                )
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
                    produtoRequestVenda.valorDeCadaProduto,
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
