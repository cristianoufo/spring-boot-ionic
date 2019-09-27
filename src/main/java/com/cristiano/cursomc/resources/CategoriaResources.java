package com.cristiano.cursomc.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cristiano.cursomc.domain.Categoria;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResources {

	@RequestMapping(method=RequestMethod.GET)
	public List<Categoria> listar() {
		Categoria c1 = new Categoria(1,"Informatica");
		Categoria c2 = new Categoria(2,"Escritorio");
		List<Categoria> lista = new ArrayList<Categoria>();
		lista.add(c1);
		lista.add(c2);
		return lista;
	}
}
