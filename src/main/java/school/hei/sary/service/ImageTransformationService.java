package school.hei.sary.service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageTransformationService {

  public BufferedImage Grayscale(String id, MultipartFile imageToTransform) throws IOException {
    String transformedFilePrefix = id + "-grayscale";
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

    File tempFile = File.createTempFile(transformedFilePrefix, ".jpg");
    ImageIO.write(grayscaleImage, "jpg", tempFile);
    return grayscaleImage;
  }

  public BufferedImage Resize(
      String id, BufferedImage imageToTransform, int targetWidth, int targetHeight)
      throws IOException {
    String transformedFilePrefix = id + "-resize";
    Image resultingImage =
        imageToTransform.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
    BufferedImage outputImage =
        new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
    outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);

    File tempFile = File.createTempFile(transformedFilePrefix, ".jpg");
    ImageIO.write(outputImage, "jpg", tempFile);
    return outputImage;
  }
}
