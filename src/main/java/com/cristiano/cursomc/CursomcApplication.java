package com.cristiano.cursomc;

import java.text.SimpleDateFormat;
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

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	


	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
	}

}
