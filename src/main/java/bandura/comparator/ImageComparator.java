package bandura.comparator;

import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.Optional;

/**
 * Created by vitalii on 17.01.17.
 */
public interface ImageComparator extends Comparator<BufferedImage> {
    Optional<BufferedImage> getDifference(BufferedImage image1, BufferedImage image2);
}
