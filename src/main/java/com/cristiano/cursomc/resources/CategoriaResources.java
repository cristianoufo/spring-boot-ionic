package com.cristiano.cursomc.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cristiano.cursomc.domain.Categoria;
import com.cristiano.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResources {
	@Autowired
	private CategoriaService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {

		Categoria c1 = new Categoria(1, "Informatica");
		Categoria c2 = new Categoria(2, "Escritorio");
		List<Categoria> lista = new ArrayList<Categoria>();
		lista.add(c1);
		lista.add(c2);
		Categoria obj = service.buscar(id);
		return ResponseEntity.ok(obj);
	}
}
