import bandura.comparator.ImageComparator;
import bandura.comparator.ImageService;
import bandura.comparator.impl.DefaultImageComparator;
import bandura.comparator.impl.DefaultImageService;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Optional;

/**
 * Created by vitalii on 17.01.17.
 */
public class Demo {
    private static InputStream IMAGE1 = Demo.class.getClassLoader().getResourceAsStream("image1.png");
    private static InputStream IMAGE2 = Demo.class.getClassLoader().getResourceAsStream("image2.png");
    private static InputStream IMAGE3 = Demo.class.getClassLoader().getResourceAsStream("image3.png");

    public static void main(String[] args) {
        ImageService imageService = new DefaultImageService();
        BufferedImage image1 = imageService.loadImage(IMAGE1);
        BufferedImage image2 = imageService.loadImage(IMAGE2);
        BufferedImage image3 = imageService.loadImage(IMAGE3);
        ImageComparator imageComparator = new DefaultImageComparator();
        Optional<BufferedImage> differenceImage = imageComparator.getDifference(image1, image2);
        if(differenceImage.isPresent()){
            imageService.saveImage(differenceImage.get(), "difference1.png");
        }
    }
}
