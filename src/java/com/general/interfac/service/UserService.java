/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.interfac.service;

import com.general.a1.controller.Login;
import com.general.a1.controller.User;

/**
 *
 * @author emoreno
 */
public interface UserService {
     
    public User validateUser(Login login);
    
}
