/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cristiano.cursomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 *
 * @author cristiano.modesto
 */
public class SmtpEmailService extends AbstractEmailService{

    private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);
    
    
    @Autowired
    private MailSender mailSender;
    @Override
    public void sendMail(SimpleMailMessage msg) {
        LOG.info("Enviando email...");
        mailSender.send(msg);
         LOG.info("Email enviado");
    }
    
}
