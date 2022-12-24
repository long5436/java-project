/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package customcontrol;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 *
 * @author long
 */
public class FlatSvgButton extends JButton {
    private FlatSVGIcon icon;

    public void createButton(String iconPath) {
        icon = new FlatSVGIcon(iconPath, 20, 20);
        setIcon(icon);
        setVerticalTextPosition(SwingConstants.BOTTOM);
        setHorizontalTextPosition(SwingConstants.CENTER);
        setBackground(new java.awt.Color(238, 238, 238));
        setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        setMargin(new java.awt.Insets(2, 2, 3, 2));
    }

    public void setButtonText(String text) {
        setText(text);
    }

}