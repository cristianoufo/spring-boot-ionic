/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cristiano.cursomc.services;

import com.cristiano.cursomc.security.UserSS;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author cristiano.modesto
 */
public class UserService {

    public static UserSS getAuthenticated() {
        try {
            return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }

    }

}
