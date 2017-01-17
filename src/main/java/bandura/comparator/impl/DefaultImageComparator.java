package bandura.comparator.impl;

import bandura.comparator.ImageComparator;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Optional;

/**
 * Created by vitalii on 17.01.17.
 */
@Service
public class DefaultImageComparator implements ImageComparator {

    @Override
    public Optional<BufferedImage> getDifference(BufferedImage image1, BufferedImage image2) {
        dimensionCheck(image1, image2);
        BufferedImage resultImage = copyImage(image2);
        Graphics2D resultGraphics = resultImage.createGraphics();
        resultGraphics.setColor(Color.RED);

        int columnQuantity = getNumberOfColumns(resultImage);
        int rowQuantity = getNumberOfRows(resultImage);
        int columnWidth = resultImage.getWidth() / columnQuantity;
        int rowHeight = resultImage.getHeight() / rowQuantity;

        boolean isDifferent = false;
        for (int row = 0; row < rowQuantity; row++) {
            for (int col = 0; col < columnQuantity; col++) {
                BufferedImage blockImage1 = image1.getSubimage(col * columnWidth, row * rowHeight, columnWidth, rowHeight);
                BufferedImage blockImage2 = image2.getSubimage(col * columnWidth, row * rowHeight, columnWidth, rowHeight);

                int averageRGB1 = getAverageRGB(blockImage1);
                int averageRGB2 = getAverageRGB(blockImage2);
                double differenceDelta = getDifferenceDelta(averageRGB1, averageRGB2);

                if (Math.abs(averageRGB2 - averageRGB1) > differenceDelta) {
                    isDifferent = true;
                    resultGraphics.drawRect(col * columnWidth, row * rowHeight, columnWidth, rowHeight);
                }
            }
        }
        if (isDifferent) {
            return Optional.of(resultImage);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public int compare(BufferedImage image1, BufferedImage image2) {
        return getDifference(image1, image2).isPresent() ? 1 : 0;
    }

    private void dimensionCheck(BufferedImage image1, BufferedImage image2) {
        if (image1.getWidth() != image2.getWidth() || image1.getHeight() != image2.getHeight()) {
            throw new IllegalArgumentException("Dimension of images are different.");
        }
    }

    public BufferedImage copyImage(BufferedImage image) {
        BufferedImage copy = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = copy.createGraphics();
        graphics.drawImage(image, null, null);
        return copy;
    }

    private int getNumberOfRows(BufferedImage resultImage) {
        return (int) (resultImage.getHeight() / (resultImage.getHeight() * 0.1));
    }

    private int getNumberOfColumns(BufferedImage resultImage) {
        return (int) (resultImage.getWidth() / (resultImage.getWidth() * 0.2));
    }

    private int getAverageRGB(BufferedImage image) {
        int average = 0;
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                average += image.getRGB(j, i);
            }
        }
        average = average / (image.getWidth() * image.getHeight());
        return average;
    }

    private double getDifferenceDelta(int average1, int average2) {
        double delta;
        if (Math.abs(average1) > Math.abs(average2)) {
            delta = average1 * 0.01;
        } else {
            delta = average2 * 0.01;
        }
        return Math.abs(delta);
    }
}
