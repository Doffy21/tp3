package src;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import src.filter.IFilter;
import src.filter.IImageFilteringEngine;

public class SingleThreadedImageFilteringEngine implements IImageFilteringEngine {

    private BufferedImage img;

    public void loadImage(String inputImage) throws Exception {
        img = ImageIO.read(new File(inputImage));
    }

    public void writeOutPngImage(String outFile) throws Exception {
        ImageIO.write(img, "png", new File(outFile));
    }

    public void setImg(BufferedImage newImg) {
        this.img = newImg;
    }

    public BufferedImage getImg() {
        return img;
    }

    public void applyFilter(IFilter someFilter) {
        int m = someFilter.getMargin();
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage result = new BufferedImage(w - 2 * m, h - 2 * m, BufferedImage.TYPE_INT_RGB);

        for (int y = m; y < h - m; y++) {
            for (int x = m; x < w - m; x++) {
                someFilter.applyFilterAtPoint(x, y, img, result);
            }
        }
        img = result;
    }
}
