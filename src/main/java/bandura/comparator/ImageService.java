package bandura.comparator;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Optional;

/**
 * Created by vitalii on 17.01.17.
 */
public interface ImageService {
    Optional<BufferedImage> getDifferenceBetweenImages(BufferedImage image1, BufferedImage image2);

    void saveImage(BufferedImage image, String path);

    BufferedImage loadImage(InputStream stream);
    BufferedImage loadImage(byte[] bytes);
}
