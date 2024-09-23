package cropulse.io.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import cropulse.io.entity.Sensor;

@Repository
public interface SensorRepository extends MongoRepository<Sensor, String> {
}

