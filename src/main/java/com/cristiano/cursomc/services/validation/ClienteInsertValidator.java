package com.cristiano.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.cristiano.cursomc.domain.enums.TipoCliente;
import com.cristiano.cursomc.dto.ClienteNewDTO;
import com.cristiano.cursomc.resources.exceptions.FieldMessage;
import com.cristiano.cursomc.services.validation.utils.DocumentUtil;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDTO, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		// inclua os testes aqui, inserindo erros na lista
		if(objDTO.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !DocumentUtil.isValidCPF(objDTO.getCpfOuCnpj())) {
			list.add(new FieldMessage("CpfOuCnpj", "CPF invalido"));
		}
		
		if(objDTO.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !DocumentUtil.isValidCNPJ(objDTO.getCpfOuCnpj())) {
			list.add(new FieldMessage("CpfOuCnpj", "CNPJ invalido"));
		}
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}

}
