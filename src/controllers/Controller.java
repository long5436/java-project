/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import javax.swing.JFrame;
import view.ViewMain;
import model.Model;

/**
 *
 * @author Long
 */
public class Controller {

    private ViewMain view;
    private Model model;

    public Controller(ViewMain view, Model model) {
        this.view = view;
        this.model = model;

        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setVisible(true);

        init();

    }

    private void init() {

    }

}
