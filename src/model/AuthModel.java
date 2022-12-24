/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import dao.AuthDAO;
import entities.User;

/**
 *
 * @author Long
 */
public class AuthModel {
    public boolean addUser(User user) throws Exception {
        return AuthDAO.addUser(user);
    }

    public boolean login(User user) throws Exception {
        return AuthDAO.login(user);
    }
}
