package school.hei.sary.endpoint.rest.controller;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Optional;
import javax.imageio.ImageIO;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import school.hei.sary.file.BucketComponent;
import school.hei.sary.repository.ImageTransformationRepository;
import school.hei.sary.service.ImageTransformationService;

@RestController
@AllArgsConstructor
public class ImageTransformationController {
  private BucketComponent bucketComponent;
  ImageTransformationRepository iTR;
  @Autowired private ImageTransformationService iTS;

  public static final String directory = "image/";

  @RequestMapping(
      path = "/grayscale/{id}",
      method = RequestMethod.PUT,
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<String> toGrayscale(
      @PathVariable(name = "id") String id, @RequestBody MultipartFile image) throws IOException {
    String fileSuffix = "." + FilenameUtils.getExtension(image.getOriginalFilename());
    String filePrefix = id + "-original";
    String transformedFilePrefix = id + "-grayscale";
    String fileBucketKey = directory + filePrefix + fileSuffix;
    String transformedFileBucketKey = directory + transformedFilePrefix + fileSuffix;

    File fileToUpload = File.createTempFile(filePrefix, fileSuffix);
    File tempFile = File.createTempFile(transformedFilePrefix, ".jpg");
    ImageIO.write(iTS.Grayscale(id, image), "jpg", tempFile);

    bucketComponent.upload(fileToUpload, fileBucketKey);
    bucketComponent.upload(tempFile, transformedFileBucketKey);

    return ResponseEntity.of(
        Optional.of(
            bucketComponent.presign(transformedFileBucketKey, Duration.ofMinutes(18)).toString()));
  }

  @GetMapping("/grayscale/{id}")
  public String getGrayscaleImage(@PathVariable String id) {
    String getImage = String.valueOf(iTR.findById(id));

    return id;
  }

  @RequestMapping(
      path = "/resize/{id}",
      method = RequestMethod.PUT,
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<String> toResize(
      @PathVariable(name = "id") String id,
      @RequestPart(value = "file") MultipartFile image,
      @RequestParam(value = "w") int width,
      @RequestParam(value = "h") int height)
      throws IOException {
    String fileSuffix = "." + FilenameUtils.getExtension(image.getOriginalFilename());
    String filePrefix = id + "-original";
    String resizedFilePrefix = id + "-resize";
    String fileBucketKey = directory + filePrefix + fileSuffix;
    String resizedFileBucketKey = directory + resizedFilePrefix + fileSuffix;

    File fileToUpload = File.createTempFile(filePrefix, fileSuffix);
    File tempFile = File.createTempFile(resizedFilePrefix, ".jpg");
    ImageIO.write(
        iTS.Resize(id, ImageIO.read(image.getInputStream()), width, height), "jpg", tempFile);

    bucketComponent.upload(fileToUpload, fileBucketKey);
    bucketComponent.upload(tempFile, resizedFileBucketKey);

    return ResponseEntity.of(
        Optional.of(
            bucketComponent.presign(resizedFileBucketKey, Duration.ofMinutes(18)).toString()));
  }

  @GetMapping("/resize/{id}")
  public String getResizedImage(@PathVariable String id) {
    return id;
  }
}
