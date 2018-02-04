/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.a2.service.impl;

import com.general.a1.controller.Login;
import com.general.a1.controller.User;
import com.general.interfac.service.UserService;
import java.io.Serializable;

/**
 *
 * @author emoreno
 */
public class UserServiceImpl implements Serializable, UserService {

    
    @Override
    public User validateUser(Login login) {
        return new User();
    }
    
}
