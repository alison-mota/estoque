package br.com.estoque.estoque.compartilhado.auxiliar

import br.com.estoque.estoque.cliente.Cliente
import br.com.estoque.estoque.empresa.Empresa
import br.com.estoque.estoque.produto.Produto
import br.com.estoque.estoque.produto.grupo.Grupo
import br.com.estoque.estoque.venda.Venda
import br.com.estoque.estoque.venda.VendaRequest
import br.com.estoque.estoque.venda.produtos.ProdutoRequestVenda
import br.com.estoque.estoque.venda.produtos.ProdutoVendido
import java.io.FileNotFoundException
import java.math.BigDecimal

fun loadResource(nome: String): String {
    return object {}.javaClass.classLoader?.getResource(nome)?.readText() ?: throw FileNotFoundException()
}

fun instanciaEmpresa(): Empresa = Empresa(
    "Nome Fantasia",
    "Razão Social",
    "36.116.557/0001-30",
    "743.777.145/5844"
)

fun instanciaCliente(empresa: Empresa): Cliente = Cliente(
    "Cliente",
    "cliente@email.com",
    empresa
)

fun instanciaGrupo(empresa: Empresa): Grupo = Grupo(
    "Grupo",
    empresa
)

fun instanciaProduto(empresa: Empresa, grupo: Grupo): Produto =
    Produto(
        "Produto",
        "Descrição",
        BigDecimal(100),
        100,
        true,
        grupo,
        empresa
    )

fun instanciaProdutoVendido(empresa: Empresa, grupo: Grupo): ProdutoVendido = ProdutoVendido(
    "Produto",
    BigDecimal(100),
    BigDecimal(100),
    1,
    true,
    grupo,
    empresa,
    "Descrição"
)

fun instanciaVenda(produtoVendido: ProdutoVendido, empresa: Empresa, cliente: Cliente): Venda = Venda(
    BigDecimal(100),
    BigDecimal(100),
    arrayListOf(produtoVendido),
    empresa,
    cliente
)

fun instanciaVendaRequest(produtos: Set<ProdutoRequestVenda>): VendaRequest = VendaRequest(
    BigDecimal(100),
    BigDecimal(100),
    produtos,
    1,
    1)

fun instanciaProdutoRequestVenda(): ProdutoRequestVenda = ProdutoRequestVenda(1, 1, BigDecimal(100))

fun instanciaVenda201Request(): String = loadResource("payload/venda/cria_venda_201_request.json")
fun instanciaVenda400Request(): String = loadResource("payload/venda/cria_venda_400_request.json")