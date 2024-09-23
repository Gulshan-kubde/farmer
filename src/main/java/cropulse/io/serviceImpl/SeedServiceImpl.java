package cropulse.io.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cropulse.io.dto.SeedDTO;
import cropulse.io.entity.Seed;
import cropulse.io.repository.SeedRepository;
import cropulse.io.service.SeedService;

@Service
public class SeedServiceImpl implements SeedService{

    @Autowired
    private SeedRepository seedRepository;
    
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public String addSeed(SeedDTO seedDTO) {
        validateSeed(seedDTO);
        Seed seed = modelMapper.map(seedDTO, Seed.class);
        
        seedRepository.save(seed);
        return "Seed created successfully.";
    }

    @Override
    public List<Seed> getAllSeeds() {
        return seedRepository.findAll();
    }

    @Override
    public Optional<Seed> getSeedById(String seedId) {
        return seedRepository.findById(seedId);
    }

    @Override
    public String updateSeed(String seedId, SeedDTO seedDTO) {
        Optional<Seed> existingSeed = seedRepository.findById(seedId);

        if (!existingSeed.isPresent()) {
            throw new IllegalArgumentException("Seed with ID " + seedId + " does not exist.");
        }

        validateSeed(seedDTO);
        Seed seed = modelMapper.map(seedDTO, Seed.class);
        
        seed.setSeedId(seedId);
        seedRepository.save(seed);

        return "Seed updated successfully.";
    }

    @Override
    public String deleteSeed(String seedId) {
        Optional<Seed> existingSeed = seedRepository.findById(seedId);

        if (!existingSeed.isPresent()) {
            throw new IllegalArgumentException("Seed with ID " + seedId + " does not exist.");
        }

        seedRepository.deleteById(seedId);
        return "Seed with ID " + seedId + " deleted successfully.";
    }

  
    private void validateSeed(SeedDTO seed) {
        String seedName = seed.getSeedName();
        if (seedName == null || seedName.trim().isEmpty()) {
            throw new IllegalArgumentException("Seed name cannot be null or empty.");
        }
    }
}
