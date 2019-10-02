package com.cristiano.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.cristiano.cursomc.domain.Pedido;

public interface EmailService {
	void sendOrderConfirmationEmail(Pedido obj);
	void sendMail(SimpleMailMessage msg);

}
