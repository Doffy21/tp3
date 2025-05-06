import java.awt.Color;
import java.awt.image.BufferedImage;

public class GrayLevelFilter implements IFilter {

    @Override
    public int getMargin() {
        return 0;
    }

    @Override
    public void applyFilterAtPoint(int x, int y, BufferedImage imgIn, BufferedImage imgOut) {
        Color color = new Color(imgIn.getRGB(x, y));
        int avg = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
        Color gray = new Color(avg, avg, avg);
        imgOut.setRGB(x, y, gray.getRGB());
    }
}
