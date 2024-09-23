package cropulse.io.repository;



import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import cropulse.io.entity.FarmLand;

@Repository
public interface FarmLandRepository extends MongoRepository<FarmLand, String> {
}
