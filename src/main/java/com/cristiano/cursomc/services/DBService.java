package com.cristiano.cursomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cristiano.cursomc.domain.Categoria;
import com.cristiano.cursomc.domain.Cidade;
import com.cristiano.cursomc.domain.Cliente;
import com.cristiano.cursomc.domain.Endereco;
import com.cristiano.cursomc.domain.Estado;
import com.cristiano.cursomc.domain.ItemPedido;
import com.cristiano.cursomc.domain.Pagamento;
import com.cristiano.cursomc.domain.PagamentoComBoleto;
import com.cristiano.cursomc.domain.PagamentoComCartao;
import com.cristiano.cursomc.domain.Pedido;
import com.cristiano.cursomc.domain.Produto;
import com.cristiano.cursomc.domain.enums.EstadoPagamento;
import com.cristiano.cursomc.domain.enums.TipoCliente;
import com.cristiano.cursomc.repositories.CategoriaRepository;
import com.cristiano.cursomc.repositories.CidadeRepository;
import com.cristiano.cursomc.repositories.ClienteRepository;
import com.cristiano.cursomc.repositories.EnderecoRepository;
import com.cristiano.cursomc.repositories.EstadoRepository;
import com.cristiano.cursomc.repositories.ItemPedidoRepository;
import com.cristiano.cursomc.repositories.PagamentoRepository;
import com.cristiano.cursomc.repositories.PedidoRepository;
import com.cristiano.cursomc.repositories.ProdutoRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class DBService {

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private BCryptPasswordEncoder pe;

    public void instantiateTestDatabase() throws ParseException {
        Categoria cat1 = new Categoria(null, "Informatica");
        Categoria cat2 = new Categoria(null, "Escritorio");
        Categoria cat3 = new Categoria(null, "Cama Mesa e banho");
        Categoria cat4 = new Categoria(null, "Eletronicos");
        Categoria cat5 = new Categoria(null, "Jardinagem");
        Categoria cat6 = new Categoria(null, "Decoração");
        Categoria cat7 = new Categoria(null, "Perfunaria");

        Produto p1 = new Produto(null, "Computador", 2000.00);
        Produto p2 = new Produto(null, "Impressora", 800.00);
        Produto p3 = new Produto(null, "Mouse", 80.00);
        Produto p4 = new Produto(null, "Mesa escritorio", 300.00);
        Produto p5 = new Produto(null, "Toalha", 50.00);
        Produto p6 = new Produto(null, "Colcha", 200.00);
        Produto p7 = new Produto(null, "TV true color", 1200.00);
        Produto p8 = new Produto(null, "Roçadeira", 800.00);
        Produto p9 = new Produto(null, "Abajour", 100.00);
        Produto p10 = new Produto(null, "Pendente", 180.00);
        Produto p11 = new Produto(null, "Shampoo", 90.00);

        cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
        cat2.getProdutos().addAll(Arrays.asList(p2, p4));
        cat3.getProdutos().addAll(Arrays.asList(p5, p6));
        cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
        cat5.getProdutos().addAll(Arrays.asList(p8));
        cat6.getProdutos().addAll(Arrays.asList(p9, p10));
        cat7.getProdutos().addAll(Arrays.asList(p11));

        p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
        p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
        p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
        p4.getCategorias().addAll(Arrays.asList(cat2));
        p5.getCategorias().addAll(Arrays.asList(cat3));
        p6.getCategorias().addAll(Arrays.asList(cat3));
        p7.getCategorias().addAll(Arrays.asList(cat4));
        p8.getCategorias().addAll(Arrays.asList(cat5));
        p9.getCategorias().addAll(Arrays.asList(cat6));
        p10.getCategorias().addAll(Arrays.asList(cat6));
        p11.getCategorias().addAll(Arrays.asList(cat7));

        Estado est1 = new Estado(null, "Minas Gerais");
        Estado est2 = new Estado(null, "São Paulo");

        Cidade c1 = new Cidade(null, "Uberlandia", est1);
        Cidade c2 = new Cidade(null, "São Paulo", est2);
        Cidade c3 = new Cidade(null, "Campinas", est2);

        est1.getCidades().addAll(Arrays.asList(c1));
        est2.getCidades().addAll(Arrays.asList(c2, c3));

        categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
        produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
        estadoRepository.saveAll(Arrays.asList(est1, est2));
        cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

        Cliente cli1 = new Cliente(null, "Maria Silva", "cristianotiago@outlook.com", "12527069847", TipoCliente.PESSOAFISICA, pe.encode("123"));
        cli1.getTelefones().addAll(Arrays.asList("9999-9999", "7777-7777"));

        Endereco e1 = new Endereco(null, "Rua Mixira", "2", "COMPLEMENTO", "Itaim Paulista", "00000-000", cli1, c1);
        Endereco e2 = new Endereco(null, "Rua Minas", "2", "COMPLEMENTO", "Pão de Queijo", "00000-000", cli1, c2);

        cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

        Cliente cli2 = new Cliente(null, "Sirlei Silva", "cristiano_ufo@hotmail.com", "12527069847", TipoCliente.PESSOAJURIDICA,pe.encode("123"));
        cli2.getTelefones().addAll(Arrays.asList("7777-7777", "8888-8888"));

        Endereco e3 = new Endereco(null, "Rua Mixira", "2", "COMPLEMENTO", "Itaim Paulista", "00000-000", cli2, c2);
        Endereco e4 = new Endereco(null, "Rua Mixira", "3", "COMPLEMENTO", "Itaim Paulista", "00000-000", cli2, c2);
        cli2.getEnderecos().addAll(Arrays.asList(e3, e4));

        clienteRepository.save(cli1);
        enderecoRepository.saveAll(Arrays.asList(e1, e2));
        clienteRepository.save(cli2);
        enderecoRepository.saveAll(Arrays.asList(e3, e4));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Pedido ped1 = new Pedido(null, sdf.parse("27/09/2019 10:32"), cli1, e1);
        Pedido ped2 = new Pedido(null, sdf.parse("27/10/2019 19:35"), cli1, e2);
        cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
        Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
        ped1.setPagamento(pagto1);
        Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("27/09/2019 00:00"),
                null);
        ped2.setPagamento(pagto2);

        pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
        pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

        ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2.000);
        ItemPedido ip2 = new ItemPedido(ped2, p3, 0.00, 2, 80.00);
        ItemPedido ip3 = new ItemPedido(ped1, p2, 100.00, 1, 800.00);

        ped1.getItens().addAll(Arrays.asList(ip1, ip2));
        ped2.getItens().addAll(Arrays.asList(ip3));

        p1.getItens().addAll(Arrays.asList(ip1));
        p2.getItens().addAll(Arrays.asList(ip3));
        p3.getItens().addAll(Arrays.asList(ip2));

        itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
    }

}