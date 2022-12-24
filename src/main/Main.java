/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.UIManager;
import model.Model;
import model.AuthModel;
import controllers.AuthController;
import controllers.Controller;
import view.ViewManage;
import view.ViewLogin;
import view.ViewMain;

/**
 *
 * @author Long
 */
public class Main {
    public static void main(String[] args) {
        setTheme();
        runAppLogin();
    }

    private static void setTheme() {
        try {
            FlatLaf theme = new FlatLightLaf();
            // UIManager.put("Button.arc", 999);
            UIManager.setLookAndFeel(theme);
        } catch (Exception e) {
        }
    }

    private static void runAppMain() {

        ViewMain main = new ViewMain();
        new Controller(main, new Model());

        main.getBtnLogout().addActionListener(handleLogout(main));
    }

    private static ActionListener handleLogout(ViewMain main) {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                main.dispose();
                runAppLogin();
            }

        };
    }

    private static void runAppLogin() {
        ViewLogin login = new ViewLogin();
        new AuthController(login, new AuthModel());

        login.addWindowListener(new WindowListener() {

            @Override
            public void windowClosed(WindowEvent e) {
                if (login.getCheckLogin()) {
                    runAppMain();
                }
            }

            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }

        });

    }

}
