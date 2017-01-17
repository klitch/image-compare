package bandura.comparator.impl;

import bandura.comparator.ImageComparator;
import bandura.comparator.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * Created by vitalii on 17.01.17.
 */
@Service
public class DefaultImageService implements ImageService {

    @Autowired
    private ImageComparator defaultImageComparator;

    @Override
    public Optional<BufferedImage> getDifferenceBetweenImages(BufferedImage image1, BufferedImage image2) {
        return defaultImageComparator.getDifference(image1, image2);
    }

    @Override
    public void saveImage(BufferedImage image, String path) {
        try {
            ImageIO.write(image, "png", new File(path));
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    @Override
    public BufferedImage loadImage(InputStream stream) {
        try {
            return ImageIO.read(stream);
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    public BufferedImage loadImage(byte[] bytes) {
        try {
            return ImageIO.read(new ByteArrayInputStream(bytes));
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }
}
