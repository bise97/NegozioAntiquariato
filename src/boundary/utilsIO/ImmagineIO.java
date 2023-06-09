package boundary.utilsIO;

import entity.Immagine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImmagineIO {
    public static void display(Immagine immagine){
        BufferedImage image = immagine.getImage();
        JLabel picLabel = new JLabel(new ImageIcon(image));
        JPanel jPanel = new JPanel();
        jPanel.add(picLabel);
        JFrame f = new JFrame();
        f.setSize(new Dimension(image.getWidth(), image.getHeight()));
        f.add(jPanel);
        f.setVisible(true);
    }
}
