package school.hei.sary.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.hei.sary.repository.model.ImageTransformation;

@Repository
public interface ImageTransformationRepository extends JpaRepository<ImageTransformation, String> {
  @Override
  Optional<ImageTransformation> findById(String id);
}
