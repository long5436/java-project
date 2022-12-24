/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import view.ViewLogin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.AuthModel;
import entities.User;

/**
 *
 * @author long
 */
public class AuthController {

    private ViewLogin view;
    private AuthModel model;

    public AuthController(ViewLogin view, AuthModel model) {
        this.view = view;
        this.model = model;

        init();
    }

    private void init() {
        view.setTitle("Đăng nhập hệ thống");
        view.setLocationRelativeTo(null);
        view.setVisible(true);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        view.getRootPane().setDefaultButton(view.getBtnLogin());

        view.getBtnLogin().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String userName = view.getTxtUsername().getText();
                String passWord = String.valueOf(view.getTxtPassword().getPassword());

                User newUser = new User(userName, passWord);

                try {
                    boolean check = model.login(newUser);
                    JOptionPane.showMessageDialog(view,
                            check ? "Đăng nhập thành công" : "Đăng nhập thất bại, tài khoản hoặc mật khẩu không đúng");

                    if (check) {
                        view.setCheckLogin(true);
                        view.dispose();
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Đăng nhập lỗi");
                }

            }
        });
    }
}
