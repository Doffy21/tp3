package src;

import src.filter.GaussianContourExtractorFilter;
import src.filter.GrayLevelFilter;
import src.filter.IImageFilteringEngine;

public class App {
    public static void main(String[] args) {
        try {
            IImageFilteringEngine im = new SingleThreadedImageFilteringEngine();
            im.loadImage("TEST_IMAGES/FourCircles.png");
            im.applyFilter(new GrayLevelFilter());
            im.applyFilter(new GaussianContourExtractorFilter());
            im.writeOutPngImage("TEST_IMAGES/FourCircles_gaussian_contour.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
