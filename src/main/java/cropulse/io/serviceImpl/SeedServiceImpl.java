package cropulse.io.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cropulse.io.dto.SeedDTO;
import cropulse.io.entity.Seed;
import cropulse.io.repository.SeedRepository;
import cropulse.io.service.SeedService;

@Service
public class SeedServiceImpl implements SeedService {

    private static final Logger logger = LoggerFactory.getLogger(SeedServiceImpl.class);

    @Autowired
    private SeedRepository seedRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    private static final String SEED_WITH_ID = "Seed with ID ";
    @Override
    public String addSeed(SeedDTO seedDTO) {
        logger.info("Entering method: addSeed with data: {}", seedDTO);
        
        validateSeed(seedDTO);
        Seed seed = modelMapper.map(seedDTO, Seed.class);
        seedRepository.save(seed);
        
        logger.info("Exiting method: addSeed. Seed created successfully.");
        return "Seed created successfully.";
    }

    @Override
    public List<Seed> getAllSeeds() {
        logger.info("Entering method: getAllSeeds");
        List<Seed> seeds = seedRepository.findAll();
        logger.info("Exiting method: getAllSeeds with result size: {}", seeds.size());
        return seeds;
    }

    @Override
    public Optional<Seed> getSeedById(String seedId) {
        logger.info("Entering method: getSeedById with Seed ID: {}", seedId);
        Optional<Seed> seed = seedRepository.findById(seedId);

        if (seed.isPresent()) {
            logger.info("Seed found with ID: {}", seedId);
        } else {
            logger.warn("No Seed found with ID: {}", seedId);
        }

        logger.info("Exiting method: getSeedById");
        return seed;
    }

    @Override
    public String updateSeed(String seedId, SeedDTO seedDTO) {
        logger.info("Entering method: updateSeed with Seed ID: {} and data: {}", seedId, seedDTO);
        
        Optional<Seed> existingSeed = seedRepository.findById(seedId);

        if (!existingSeed.isPresent()) {
            logger.error("{} {} does not exist",SEED_WITH_ID, seedId);
            throw new IllegalArgumentException(SEED_WITH_ID + seedId + " does not exist.");
        }

        validateSeed(seedDTO);
        Seed seed = modelMapper.map(seedDTO, Seed.class);
        seed.setSeedId(seedId);
        seedRepository.save(seed);

        logger.info("Exiting method: updateSeed. Seed updated successfully with ID: {}", seedId);
        return "Seed updated successfully.";
    }

    @Override
    public String deleteSeed(String seedId) {
        logger.info("Entering method: deleteSeed with Seed ID: {}", seedId);

        Optional<Seed> existingSeed = seedRepository.findById(seedId);

        if (!existingSeed.isPresent()) {
            logger.error("{} {} does not exist",SEED_WITH_ID, seedId);
            throw new IllegalArgumentException(SEED_WITH_ID + seedId + " does not exist.");
        }

        seedRepository.deleteById(seedId);
        logger.info("Exiting method: deleteSeed. {} {} deleted successfully",SEED_WITH_ID, seedId);
        return SEED_WITH_ID + seedId + " deleted successfully.";
    }

    private void validateSeed(SeedDTO seedDTO) {
        logger.debug("Validating Seed: {}", seedDTO);

        String seedName = seedDTO.getSeedName();
        if (seedName == null || seedName.trim().isEmpty()) {
            logger.error("Validation error: Seed name cannot be null or empty.");
            throw new IllegalArgumentException("Seed name cannot be null or empty.");
        }

        logger.debug("Validation completed successfully for Seed: {}", seedDTO);
    }
}
