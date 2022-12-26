/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Model;
import model.WarehouseModel;
import view.ViewPanelManage;
import view.ViewPanelWarehouse;
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

        JPanel mainPanel = view.getPanelMain();

        ViewPanelManage viewManage = new ViewPanelManage();
        mainPanel.add(viewManage);
        new CategoryController(viewManage.getViewPanelCategory(), model);
        new ProductController(viewManage.getViewPanelProduct(), model);

        view.getBtnManage().setButtonContentColor(Color.decode("#9999FF"));

        view.getBtnWareHouse().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ViewPanelWarehouse warehouse = new ViewPanelWarehouse();
                mainPanel.removeAll();
                mainPanel.add(warehouse);
                mainPanel.revalidate();
                new WarehouseController(warehouse, new WarehouseModel());

                view.getBtnWareHouse().setButtonContentColor(Color.decode("#9999FF"));
                view.getBtnManage().setButtonDefaultContentColor();
            }
        });

        view.getBtnManage().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ViewPanelManage viewManage = new ViewPanelManage();
                mainPanel.removeAll();
                mainPanel.add(viewManage);
                mainPanel.revalidate();
                new CategoryController(viewManage.getViewPanelCategory(), model);
                new ProductController(viewManage.getViewPanelProduct(), model);

                view.getBtnManage().setButtonContentColor(Color.decode("#9999FF"));
                view.getBtnWareHouse().setButtonDefaultContentColor();

            };

        });

    }

}
