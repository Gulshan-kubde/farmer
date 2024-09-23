package cropulse.io.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import cropulse.io.entity.Plot;
@Repository
public interface PlotRepository extends MongoRepository<Plot, String> {
}

