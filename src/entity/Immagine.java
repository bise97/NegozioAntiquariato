package entity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;

public class Immagine {
    private long id;
    private BufferedImage image;
    private long codiceProdotto;
    private String path;

    public Immagine(File file) throws IOException{
            this.image = ImageIO.read(file);
            this.path = file.getPath();
    }

    public Immagine(long id, BufferedImage image, long codiceProdotto, String path) {
        this.id = id;
        this.image = image;
        this.codiceProdotto = codiceProdotto;
        this.path = path;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Immagine)) return false;
        if((!Objects.equals(((Immagine) obj).getPath(), path))) return false;
        return true;
    }
}
