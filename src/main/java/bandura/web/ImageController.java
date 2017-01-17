package bandura.web;

import bandura.comparator.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by vitalii on 17.01.17.
 */
@Controller
public class ImageController {
    @Autowired
    private ImageService imageService;

    @ResponseBody
    @RequestMapping(value = "/compare", method = RequestMethod.POST, produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] compareImages(@RequestParam MultipartFile image1, @RequestParam MultipartFile image2) throws IOException {
        BufferedImage uploadedImage1 = imageService.loadImage(image1.getBytes());
        BufferedImage uploadedImage2 = imageService.loadImage(image2.getBytes());
        Optional<BufferedImage> differenceImage = imageService.getDifferenceBetweenImages(uploadedImage1, uploadedImage2);
        if (differenceImage.isPresent()) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(differenceImage.get(), "png", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        }
        return new byte[0];
    }
}
