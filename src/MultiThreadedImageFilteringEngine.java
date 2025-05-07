package src;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import javax.imageio.ImageIO;

import src.filter.IFilter;
import src.filter.IImageFilteringEngine;

public class MultiThreadedImageFilteringEngine implements IImageFilteringEngine {
    private BufferedImage img;
    private int numThreads;

    public MultiThreadedImageFilteringEngine(int numThreads) {
        this.numThreads = numThreads;
    }

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

        CyclicBarrier barrier = new CyclicBarrier(numThreads);
        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            final int threadIndex = i;
            threads[i] = new Thread(() -> {
                for (int y = m + threadIndex; y < h - m; y += numThreads) {
                    for (int x = m; x < w - m; x++) {
                        someFilter.applyFilterAtPoint(x, y, img, result);
                    }
                }
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        img = result;
    }
}
