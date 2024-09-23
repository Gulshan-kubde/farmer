package cropulse.io.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import cropulse.io.entity.Seed;

@Repository
public interface SeedRepository extends MongoRepository<Seed, String> {
}

