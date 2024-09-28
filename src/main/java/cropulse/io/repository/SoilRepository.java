package cropulse.io.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import cropulse.io.entity.Soil;

@Repository
public interface SoilRepository extends MongoRepository<Soil, String> {
}
