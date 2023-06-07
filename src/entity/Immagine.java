package entity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Immagine {
    private long id;
    private BufferedImage image;
    private Prodotto prodotto;

    public Immagine(File file, Prodotto prodotto) throws IOException{
            this.image = ImageIO.read(file);
            this.prodotto = prodotto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Prodotto getProdotto() {
        return prodotto;
    }

    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }
}
