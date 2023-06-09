package boundary.utilsIO;

import entity.Immagine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ImmagineIO {

    private static ArrayList<JFrame> windows;

    public static void display(Immagine immagine){
        windows = new ArrayList<>();
        BufferedImage image = immagine.getImage();
        JLabel picLabel = new JLabel(new ImageIcon(image));
        JPanel jPanel = new JPanel();
        jPanel.add(picLabel);
        JFrame f = new JFrame();
        f.setSize(new Dimension(image.getWidth(), image.getHeight()));
        f.add(jPanel);
        f.setTitle("Prod: "+immagine.getCodiceProdotto() + " - ID: " + immagine.getId());
        f.setVisible(true);
        windows.add(f);
    }

    public static void closeAll(){
        for(JFrame w : windows){
            w.setVisible(false);
            w.dispose();
            windows.remove(w);
        }
    }
}
