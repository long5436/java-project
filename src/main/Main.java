/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

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
import java.awt.Insets;
import java.util.Collections;
import view.ViewLogin;
import view.ViewMain;

import utils.MethodUtil;

/**
 *
 * @author Long
 */
public class Main {

    public static void main(String[] args) {
        setTheme();
        runAppLogin();
        // runAppMain();

    }

    private static void setTheme() {
        try {
            FlatLightLaf.setGlobalExtraDefaults(Collections.singletonMap("@accentColor", "#9999FF")); // đổi màu chủ đạo
            // UIManager.setLookAndFeel(theme);
            FlatLightLaf.setup();
            UIManager.put("TextComponent.arc", 4);
            UIManager.put("TabbedPane.selectedBackground", "#FFF");
            UIManager.put("Component.innerFocusWidth", 1);
            UIManager.put("ScrollBar.thumbArc", 999);
            UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));// theme
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
                int messageConfirm = MethodUtil.showMessageConfirm("Bạn có muốn đăng xuất không?");
                if (messageConfirm == 0) {
                    main.dispose();
                    runAppLogin();

                }

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
