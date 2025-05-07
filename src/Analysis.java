package src;

import src.filter.GrayLevelFilter;
import src.filter.GaussianContourExtractorFilter;
import src.filter.IImageFilteringEngine;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Analysis {

  public static void main(String[] args) throws Exception {
    //first();
    second();
  }

  static void first() throws Exception {
    String inputImagePath = "TEST_IMAGES/15226222451_5fd668d81a_c.jpg";
    int numRuns = 5;

    System.out.println("Sequential Engine:");
    IImageFilteringEngine sequentialEngine = new SingleThreadedImageFilteringEngine();
    measurePerformance(sequentialEngine, inputImagePath, numRuns);

    System.out.println("\nParallel Engine:");
    for (int numThreads = 1; numThreads <= 10; numThreads++) {
      System.out.println("Threads: " + numThreads);
      IImageFilteringEngine parallelEngine = new MultiThreadedImageFilteringEngine(numThreads);
      measurePerformance(parallelEngine, inputImagePath, numRuns);
    }
  }

  static void second() throws Exception {
    String[] imagePaths = {
        "TEST_IMAGES/15226222451_75d515f540_o.jpg",
        "TEST_IMAGES/15226222451_a49b1a624b_h.jpg",
        "TEST_IMAGES/15226222451_5fd668d81a_c.jpg",
        "TEST_IMAGES/15226222451_5fd668d81a_z.jpg",
        "TEST_IMAGES/15226222451_5fd668d81a.jpg",
        "TEST_IMAGES/15226222451_5fd668d81a_n.jpg",
        "TEST_IMAGES/15226222451_5fd668d81a_m.jpg",
        "TEST_IMAGES/15226222451_5fd668d81a_t.jpg",
    };
    int numRuns = 5;
    int numThreads = 4;

    System.out.println("Parallel Engine with 4 Threads:");
    IImageFilteringEngine parallelEngine = new MultiThreadedImageFilteringEngine(numThreads);

    for (String imagePath : imagePaths) {
      System.out.println("\nProcessing image: " + imagePath);
      BufferedImage img = ImageIO.read(new File(imagePath));
      int imageSize = img.getWidth() * img.getHeight();
      System.out.println("Image size: " + img.getWidth() + "x" + img.getHeight() + " (" + imageSize + " pixels)");
      measurePerformance(parallelEngine, imagePath, numRuns);
    }
  }

  private static void measurePerformance(IImageFilteringEngine engine, String inputImagePath, int numRuns)
      throws Exception {
    double grayFilterTime = 0;
    double gaussianFilterTime = 0;

    for (int i = 0; i < numRuns; i++) {
      engine.loadImage(inputImagePath);

      long startTime = System.currentTimeMillis();
      engine.applyFilter(new GrayLevelFilter());
      long endTime = System.currentTimeMillis();
      grayFilterTime += endTime - startTime;


      startTime = System.currentTimeMillis();
      engine.applyFilter(new GaussianContourExtractorFilter());
      endTime = System.currentTimeMillis();
      gaussianFilterTime += endTime - startTime;
    }

    grayFilterTime /= numRuns;
    gaussianFilterTime /= numRuns;

    System.out.printf("GrayLevelFilter: %.2f ms\n", grayFilterTime);
    System.out.printf("GaussianContourExtractorFilter: %.2f ms\n", gaussianFilterTime);
  }
}