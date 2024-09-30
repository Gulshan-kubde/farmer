package cropulse.io.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import cropulse.io.entity.SensorData;
@Repository
public interface SensorDataRepository extends MongoRepository<SensorData, String> {
}
