package com.cristiano.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cristiano.cursomc.domain.Cidade;
import com.cristiano.cursomc.domain.Cliente;
import com.cristiano.cursomc.domain.Endereco;
import com.cristiano.cursomc.domain.enums.Perfil;
import com.cristiano.cursomc.domain.enums.TipoCliente;
import com.cristiano.cursomc.dto.ClienteDTO;
import com.cristiano.cursomc.dto.ClienteNewDTO;
import com.cristiano.cursomc.repositories.CidadeRepository;
import com.cristiano.cursomc.repositories.ClienteRepository;
import com.cristiano.cursomc.repositories.EnderecoRepository;
import com.cristiano.cursomc.security.UserSS;
import com.cristiano.cursomc.services.exceptions.AuthorizationException;
import com.cristiano.cursomc.services.exceptions.DataIntegrityException;
import com.cristiano.cursomc.services.exceptions.ObjectNotFoundException;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.nio.Buffer;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ImageService imageService;
	
	@Value("${img.prefix.client.profile}")
	private String prefix;
	
	@Value("${img.profile.size}")
	private Integer size;
	

	public Cliente find(Integer id) {

		UserSS user = UserService.getAuthenticated();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}

	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(obj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException(
					"Não é possivel excluir uma Cliente que tem produtos(Entidades relacionadas)");
		}
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	public Cliente findByEmail(String email) {
		UserSS user = UserService.getAuthenticated();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado");
		}
	
		Cliente obj = repo.findByEmail(email);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Cliente.class.getName());
		}
		return obj;
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null, null);

	}

	public Cliente fromDTO(ClienteNewDTO objDTO) {
		Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(),
				TipoCliente.toEnum(objDTO.getTipo()), pe.encode(objDTO.getSenha()));

		Cidade cid = new Cidade(objDTO.getCidadeId(), null, null);

		// Cidade cid = cidadeRepository.findById(objDTO.getId()).get();
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(),
				objDTO.getBairro(), objDTO.getCep(), cli, cid);

		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDTO.getTelefone1());
		if (objDTO.getTelefone2() != null) {
			cli.getTelefones().add(objDTO.getTelefone2());
		}
		if (objDTO.getTelefone3() != null) {
			cli.getTelefones().add(objDTO.getTelefone3());
		}

		return cli;

	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

	public URI uploadProfilePicture(MultipartFile multipartFile) {
		UserSS user = UserService.getAuthenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		
		//URI uri = s3Service.uploadFile(multipartFile);
		//Cliente cli = repo.findById(user.getId()).get();
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		String fileName = prefix + user.getId() +".jpg";
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);
		//cli.setImageUrl(uri.toString());
		//repo.save(cli);
		//return s3Service.uploadFile(multipartFile);
		
		return s3Service.uploadFile(imageService.getInputStream(jpgImage,"jpg"),fileName,"image");
	}
	
	
}
