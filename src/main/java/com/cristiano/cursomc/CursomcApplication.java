package com.cristiano.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cristiano.cursomc.domain.Categoria;
import com.cristiano.cursomc.domain.Cidade;
import com.cristiano.cursomc.domain.Cliente;
import com.cristiano.cursomc.domain.Endereco;
import com.cristiano.cursomc.domain.Estado;
import com.cristiano.cursomc.domain.Produto;
import com.cristiano.cursomc.domain.enums.TipoCliente;
import com.cristiano.cursomc.repositories.CategoriaRepository;
import com.cristiano.cursomc.repositories.CidadeRepository;
import com.cristiano.cursomc.repositories.ClienteRepository;
import com.cristiano.cursomc.repositories.EnderecoRepository;
import com.cristiano.cursomc.repositories.EstadoRepository;
import com.cristiano.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

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

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");

		Produto p1 = new Produto(null, "Computador", 2.000);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		Estado est1 = new Estado(null,"Minas Gerais");
		Estado est2 = new Estado(null,"São Paulo");
		
		Cidade c1 = new Cidade(null,"Uberlandia",est1);
		Cidade c2 = new Cidade(null,"São Paulo",est2);
		Cidade c3 = new Cidade(null,"Campinas",est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2,p3));
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "12527069847", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("9999-9999","7777-7777"));
		
		Endereco e1 = new Endereco(null, "Rua Mixira", "2", "COMPLEMENTO", "Itaim Paulista", "00000-000", cli1,c1);
		Endereco e2 = new Endereco(null, "Rua Minas", "2", "COMPLEMENTO", "Pão de Queijo", "00000-000", cli1,c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		Cliente cli2 = new Cliente(null, "Sirlei Silva", "sirlei@gmail.com", "12527069847", TipoCliente.PESSOAJURIDICA);
		cli2.getTelefones().addAll(Arrays.asList("7777-7777","8888-8888"));
		
		Endereco e3 = new Endereco(null, "Rua Mixira", "2", "COMPLEMENTO", "Itaim Paulista", "00000-000", cli2,c2);
		Endereco e4 = new Endereco(null, "Rua Mixira", "3", "COMPLEMENTO", "Itaim Paulista", "00000-000", cli2,c2);
		cli2.getEnderecos().addAll(Arrays.asList(e3, e4));
		
		clienteRepository.save(cli1);
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		clienteRepository.save(cli2);
		enderecoRepository.saveAll(Arrays.asList(e3, e4));
	}

}
