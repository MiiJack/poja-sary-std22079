package school.hei.sary.endpoint.rest.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import school.hei.sary.service.ImageTransformationService;

@RestController
public class ImageTransformationController {
  @Autowired private ImageTransformationService imageTransformationService;

  @RequestMapping(
      path = "/grayscale/{id}",
      method = RequestMethod.PUT,
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public String toGrayscale(@PathVariable(name = "id") String id, @RequestPart MultipartFile image)
      throws IOException {
    String grayscaled = imageTransformationService.Grayscale(id, image).toString();
    System.out.println(grayscaled);
    return null;
  }

  @GetMapping("/grayscale/{id}")
  public String getGrayscaledImage(@PathVariable String id) {
    return id + "is grayscaled";
  }
}
