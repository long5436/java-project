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
                    int check = model.login(newUser);
                    String message = "";

                    switch (check) {
                        case 1:
                            message = "Đăng nhập thành công";
                            break;
                        case 2:
                            message = "Đăng nhập thất bại, mật khẩu không đúng";
                            break;
                        case 3:
                            message = "Đăng nhập thất bại, tài khoản không tồn tại";
                            break;
                        case 4:
                            message = "Đăng nhập thất bại, không thể kết nối với máy chủ";
                            break;
                        default:
                            break;
                    }

                    JOptionPane.showMessageDialog(view, message);

                    if (check == 1) {
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
