/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import javax.swing.JFrame;
import view.ViewManage;
import model.Model;
import view.ViewMain;

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
        view.setTitle("Quản lý hàng hoá");
        view.setLocationRelativeTo(null);

        init();

    }

    private void init() {
        // new CategoryController(view.getViewPanelCategory1(), model);
        // new ProductController(view.getViewPanelProduct1(), model);

    }

}
