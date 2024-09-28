package cropulse.io.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import cropulse.io.entity.SensorData;

public interface SensorDataRepository extends MongoRepository<SensorData, String> {
}
