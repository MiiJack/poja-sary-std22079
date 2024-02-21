package school.hei.sary.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import school.hei.sary.file.BucketComponent;
import school.hei.sary.repository.ImageTransformationRepository;

@Service
public class ImageTransformationService {
  private final BucketComponent bucketComponent;
  private final ImageTransformationRepository imageTransformationRepository;

  public ImageTransformationService(
      BucketComponent bucketComponent,
      ImageTransformationRepository imageTransformationRepository) {
    this.bucketComponent = bucketComponent;
    this.imageTransformationRepository = imageTransformationRepository;
  }

  private static final String directory = "image/";

  public String Grayscale(String id, MultipartFile imageToTransform) throws IOException {
    // Read the image from the MultipartFile into a BufferedImage
    BufferedImage img = ImageIO.read(imageToTransform.getInputStream());
    int width = img.getWidth();
    int height = img.getHeight();

    // Convert the image to grayscale
    BufferedImage grayscaleImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        int rgb = img.getRGB(i, j);
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        int gray = (r + g + b) / 3;
        grayscaleImage.setRGB(i, j, (gray << 16) | (gray << 8) | gray);
      }
    }

    File tempFile = File.createTempFile(id, "_grayscale");
    ImageIO.write(grayscaleImage, "jpg", tempFile);
    String bucketKey = directory + id + grayscaleImage.toString();
    bucketComponent.upload(tempFile, bucketKey);
    return bucketComponent.presign(bucketKey, Duration.ofMinutes(7)).toString();
  }
}
