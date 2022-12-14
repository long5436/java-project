/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import model.Model;
import controllers.Controller;
import view.ViewMain;

/**
 *
 * @author Long
 */
public class Main {
    public static void main(String[] args) {
        new Controller(new ViewMain(), new Model());
    }
}
