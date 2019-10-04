package com.cristiano.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.cristiano.cursomc.domain.Pedido;
import javax.mail.internet.MimeMessage;

public interface EmailService {

    void sendOrderConfirmationEmail(Pedido obj);

    void sendMail(SimpleMailMessage msg);

    void sendOrderConfirmationHtmlEmail(Pedido obj);

    void sendHtmlEmail(MimeMessage msg);

}
