package entity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Immagine {
    private long id;
    private BufferedImage image;
    private long codiceProdotto;

    public Immagine(File file) throws IOException{
            this.image = ImageIO.read(file);
    }

    public Immagine(long id, BufferedImage image, long codiceProdotto) {
        this.id = id;
        this.image = image;
        this.codiceProdotto = codiceProdotto;
    }

    @Override
    public String toString() {
        return "Immagine{" +
                "id=" + this.getId() +
                ", codiceProdotto=" + this.getCodiceProdotto() +
                "}";
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

    public long getCodiceProdotto() {
        return codiceProdotto;
    }

    public void setCodiceProdotto(long codiceProdotto) {
        this.codiceProdotto = codiceProdotto;
    }
}
