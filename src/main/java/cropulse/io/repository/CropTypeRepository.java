package cropulse.io.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import cropulse.io.entity.CropType;
@Repository
public interface CropTypeRepository extends MongoRepository<CropType, String> {
}
