package cropulse.io.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cropulse.io.dto.CultivationDTO;
import cropulse.io.entity.Cultivation;
import cropulse.io.repository.CultivationRepository;
import cropulse.io.service.CultivationService;

@Service
public class CultivationServiceImpl implements CultivationService {

    private static final Logger logger = LoggerFactory.getLogger(CultivationServiceImpl.class);

    @Autowired
    private CultivationRepository cultivationRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String addCultivation(CultivationDTO cultivationDTO) {
        logger.info("Entering method: addCultivation with data: {}", cultivationDTO);
        validateCultivation(cultivationDTO);
        
        Cultivation cultivation = modelMapper.map(cultivationDTO, Cultivation.class);
        
        cultivationRepository.save(cultivation);
        logger.info("Exiting method: addCultivation. Cultivation created successfully.");
        return "Cultivation created successfully.";
    }

    @Override
    public List<Cultivation> getAllCultivations() {
        logger.info("Entering method: getAllCultivations");
        List<Cultivation> cultivations = cultivationRepository.findAll();
        logger.info("Exiting method: getAllCultivations with result size: {}", cultivations.size());
        return cultivations;
    }

    @Override
    public Optional<Cultivation> getCultivationById(String cultivationId) {
        logger.info("Entering method: getCultivationById with ID: {}", cultivationId);
        Optional<Cultivation> cultivation = cultivationRepository.findById(cultivationId);
        
        if (cultivation.isPresent()) {
            logger.info("Cultivation found with ID: {}", cultivationId);
        } else {
            logger.warn("No Cultivation found with ID: {}", cultivationId);
        }
        
        logger.info("Exiting method: getCultivationById");
        return cultivation;
    }

    @Override
    public String updateCultivation(String cultivationId, CultivationDTO cultivationDTO) {
        logger.info("Entering method: updateCultivation with ID: {} and data: {}", cultivationId, cultivationDTO);
        
        Optional<Cultivation> existingCultivation = cultivationRepository.findById(cultivationId);

        if (!existingCultivation.isPresent()) {
            logger.error("Cultivation with ID {} does not exist", cultivationId);
            throw new IllegalArgumentException("Cultivation with ID " + cultivationId + " does not exist.");
        }

        validateCultivation(cultivationDTO);
        Cultivation cultivation = modelMapper.map(cultivationDTO, Cultivation.class);
        
        cultivation.setCultivationId(cultivationId);
        cultivationRepository.save(cultivation);

        logger.info("Exiting method: updateCultivation. Cultivation updated successfully with ID: {}", cultivationId);
        return "Cultivation updated successfully.";
    }

    @Override
    public String deleteCultivation(String cultivationId) {
        logger.info("Entering method: deleteCultivation with ID: {}", cultivationId);
        
        Optional<Cultivation> existingCultivation = cultivationRepository.findById(cultivationId);

        if (!existingCultivation.isPresent()) {
            logger.error("Cultivation with ID {} does not exist", cultivationId);
            throw new IllegalArgumentException("Cultivation with ID " + cultivationId + " does not exist.");
        }

        cultivationRepository.deleteById(cultivationId);
        logger.info("Exiting method: deleteCultivation. Cultivation with ID {} deleted successfully", cultivationId);
        return "Cultivation with ID " + cultivationId + " deleted successfully.";
    }

    private void validateCultivation(CultivationDTO cultivation) {
        logger.debug("Validating Cultivation: {}", cultivation);
        String cultivationName = cultivation.getCultivationName();
        if (cultivationName == null || cultivationName.trim().isEmpty()) {
            logger.error("Validation error: Cultivation name cannot be null or empty.");
            throw new IllegalArgumentException("Cultivation name cannot be null or empty.");
        }
        logger.debug("Validation completed successfully for Cultivation: {}", cultivation);
    }
}
