package test;

import org.junit.Test;

import src.SingleThreadedImageFilteringEngine;
import src.filter.GrayLevelFilter;
import src.filter.IImageFilteringEngine;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FilterTests {

    @Test
    public void testGrayFilterOnWhiteImage() {
        BufferedImage img = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < 256; y++)
            for (int x = 0; x < 256; x++)
                img.setRGB(x, y, new Color(255, 255, 255).getRGB());

        IImageFilteringEngine engine = new SingleThreadedImageFilteringEngine();
        engine.setImg(img);
        engine.applyFilter(new GrayLevelFilter());
        BufferedImage result = engine.getImg();

        for (int y = 0; y < 256; y++)
            for (int x = 0; x < 256; x++) {
                Color c = new Color(result.getRGB(x, y));
                assertEquals(255, c.getRed());
                assertEquals(255, c.getGreen());
                assertEquals(255, c.getBlue());
            }
    }

    @Test
    public void testGrayFilterOnBlackImage() {
        BufferedImage img = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < 256; y++)
            for (int x = 0; x < 256; x++)
                img.setRGB(x, y, new Color(0, 0, 0).getRGB());

        IImageFilteringEngine engine = new SingleThreadedImageFilteringEngine();
        engine.setImg(img);
        engine.applyFilter(new GrayLevelFilter());
        BufferedImage result = engine.getImg();

        for (int y = 0; y < 256; y++)
            for (int x = 0; x < 256; x++) {
                Color c = new Color(result.getRGB(x, y));
                assertEquals(0, c.getRed());
                assertEquals(0, c.getGreen());
                assertEquals(0, c.getBlue());
            }
    }

    @Test
    public void testGrayFilterOnRedGreenBlueImage() {
        BufferedImage img = new BufferedImage(768, 256, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < 256; y++)
            for (int x = 0; x < 256; x++)
                img.setRGB(x, y, new Color(255, 0, 0).getRGB());

        for (int y = 0; y < 256; y++)
            for (int x = 256; x < 512; x++)
                img.setRGB(x, y, new Color(0, 255, 0).getRGB());
        for (int y = 0; y < 256; y++)
            for (int x = 512; x < 768; x++)
                img.setRGB(x, y, new Color(0, 0, 255).getRGB());
        IImageFilteringEngine engine = new SingleThreadedImageFilteringEngine();
        engine.setImg(img);
        engine.applyFilter(new GrayLevelFilter());
        BufferedImage result = engine.getImg();
        for (int y = 0; y < 256; y++)
            for (int x = 0; x < 768; x++) {
                Color c = new Color(result.getRGB(x, y));
                assertEquals(85, c.getRed());
                assertEquals(85, c.getGreen());
                assertEquals(85, c.getBlue());
            }
    }

}
