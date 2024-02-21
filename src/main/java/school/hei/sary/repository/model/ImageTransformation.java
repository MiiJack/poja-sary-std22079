package school.hei.sary.repository.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class ImageTransformation {
  @Id private String id;

  @Column(name = "transformation_status")
  String transformationStatus;

  @Column(name = "transformation_timestamp")
  Timestamp transformationTimestamp;
}
