import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.image.BufferedImage;

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

    
    
}
