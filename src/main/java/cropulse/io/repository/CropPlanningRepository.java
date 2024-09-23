package cropulse.io.repository;




import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import cropulse.io.entity.CropPlanning;

@Repository
public interface CropPlanningRepository extends MongoRepository<CropPlanning, String> {
}
