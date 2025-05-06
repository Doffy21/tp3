import java.awt.Color;
import java.awt.image.BufferedImage;

public class GaussianContourExtractorFilter implements IFilter {

    private final int margin = 5;

    @Override
    public int getMargin() {
        return margin;
    }

    @Override
    public void applyFilterAtPoint(int x, int y, BufferedImage imgIn, BufferedImage imgOut) {
        double dx = 0.0;
        double dy = 0.0;

        for (int dyOffset = -margin; dyOffset <= margin; dyOffset++) {
            for (int dxOffset = -margin; dxOffset <= margin; dxOffset++) {
                int px = x + dxOffset;
                int py = y + dyOffset;
                Color color = new Color(imgIn.getRGB(px, py));
                double val = color.getBlue(); // gray image: R = G = B

                double weight = Math.exp(-(dxOffset * dxOffset + dyOffset * dyOffset) / 4.0);

                dx += Math.signum(dxOffset) * val * weight;
                dy += Math.signum(dyOffset) * val * weight;
            }
        }

        double norm = Math.sqrt(dx * dx + dy * dy);
        int intensity = (int) Math.max(0, 255 - 0.5 * norm);
        Color contour = new Color(intensity, intensity, intensity);
        imgOut.setRGB(x - margin, y - margin, contour.getRGB());
    }
}
