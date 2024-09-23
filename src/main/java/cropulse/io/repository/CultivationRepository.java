package cropulse.io.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import cropulse.io.entity.Cultivation;

@Repository
public interface CultivationRepository extends MongoRepository<Cultivation, String> {
}

