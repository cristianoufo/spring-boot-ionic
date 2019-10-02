package com.cristiano.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cristiano.cursomc.domain.ItemPedido;
import com.cristiano.cursomc.domain.PagamentoComBoleto;
import com.cristiano.cursomc.domain.Pedido;
import com.cristiano.cursomc.domain.Produto;
import com.cristiano.cursomc.domain.enums.EstadoPagamento;
import com.cristiano.cursomc.repositories.ItemPedidoRepository;
import com.cristiano.cursomc.repositories.PagamentoRepository;
import com.cristiano.cursomc.repositories.PedidoRepository;
import com.cristiano.cursomc.repositories.ProdutoRepository;
import com.cristiano.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);;
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto,obj.getInstante() );
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for(ItemPedido ip : obj.getItens() ) {
			ip.setDesconto(0.0);
			Optional<Produto> aThing = produtoRepository.findById(ip.getProduto().getId());
			ip.setPreco(aThing.get().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		return obj;
	}
}
