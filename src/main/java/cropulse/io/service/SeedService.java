package cropulse.io.service;

import java.util.List;
import java.util.Optional;

import cropulse.io.dto.SeedDTO;
import cropulse.io.entity.Seed;

public interface SeedService {

    String addSeed(SeedDTO seedDTO);

    List<Seed> getAllSeeds();

    Optional<Seed> getSeedById(String seedId);

    String updateSeed(String seedId, SeedDTO seedDTO);

    String deleteSeed(String seedId);
}
