package test;

import org.junit.Test;

import src.SingleThreadedImageFilteringEngine;
import src.filter.GaussianContourExtractorFilter;
import src.filter.GrayLevelFilter;
import src.filter.IImageFilteringEngine;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class FilterTests {
    private static String normal_gray[] = { "TEST_IMAGES/15226222451_5fd668d81a_c.jpg",
            "TEST_IMAGES/15226222451_5fd668d81a_c_gray.png" };
    private static String normal_gray_contour[] = { "TEST_IMAGES/15226222451_5fd668d81a_c.jpg",
            "TEST_IMAGES/output_gray_contour.png" };
    private static String four_circle_gray[] = { "TEST_IMAGES/FourCircles.png",
            "TEST_IMAGES/FourCircles_gray.png" };
    private static String four_circle_gray_contour[] = { "TEST_IMAGES/FourCircles.png",
            "TEST_IMAGES/FourCircles_gaussian_contour.png" };

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

    @Test
    public void testGrayFilterOnImage() throws Exception {
        boolean same_image = true;
        IImageFilteringEngine engine = new SingleThreadedImageFilteringEngine();
        engine.loadImage(four_circle_gray[0]);
        engine.applyFilter(new GrayLevelFilter());
        BufferedImage result = engine.getImg();
        BufferedImage verifImage = ImageIO.read(new File(four_circle_gray[1]));
        for (int x = 0; x < result.getWidth(); x++) {
            for (int y = 0; y < result.getHeight(); y++) {
                if (result.getRGB(x, y) != verifImage.getRGB(x, y)) {
                    same_image = false;
                    break;
                }
            }
        }
        assertTrue(same_image);
    }

    @Test
    public void testGaussianContourOnImage() throws Exception {
        boolean same_image = true;
        IImageFilteringEngine engine = new SingleThreadedImageFilteringEngine();
        engine.loadImage(four_circle_gray_contour[0]);
        engine.applyFilter(new GrayLevelFilter());
        engine.applyFilter(new GaussianContourExtractorFilter());
        BufferedImage result = engine.getImg();
        BufferedImage verifImage = ImageIO.read(new File(four_circle_gray_contour[1]));
        for (int x = 0; x < result.getWidth(); x++) {
            for (int y = 0; y < result.getHeight(); y++) {
                if (result.getRGB(x, y) != verifImage.getRGB(x, y)) {
                    same_image = false;
                    break;
                }
            }
        }
        assertTrue(same_image);
    }

}
