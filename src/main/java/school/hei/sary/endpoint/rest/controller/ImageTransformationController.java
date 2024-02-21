package school.hei.sary.endpoint.rest.controller;

import java.io.IOException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import school.hei.sary.service.ImageTransformationService;

public class ImageTransformationController {
  ImageTransformationService imageTransformationService;

  @RequestMapping(
      path = "/grayscale/{id}",
      method = RequestMethod.PUT,
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public String toGrayscale(@PathVariable(name= "id") String id, @RequestPart MultipartFile image)
      throws IOException {
    return imageTransformationService.Grayscale(id, image);
  }

  @GetMapping("/grayscale/{id}")
  public String getGrayscaledImage(@PathVariable String id) {
    return id + "is grayscaled";
  }
}
