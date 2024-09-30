package cropulse.io.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cropulse.io.dto.FarmLandDTO;
import cropulse.io.entity.FarmLand;
import cropulse.io.repository.FarmLandRepository;
import cropulse.io.service.FarmLandService;

@Service
public class FarmLandServiceImpl implements FarmLandService {

    private static final Logger logger = LoggerFactory.getLogger(FarmLandServiceImpl.class);

    @Autowired
    private FarmLandRepository farmLandRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    private static final String FARMLAND_WITH_ID = "FarmLand with Farmer ID ";
    @Override
    public String addFarmLand(FarmLandDTO farmLandDTO) {
        logger.info("Entering method: addFarmLand with data: {}", farmLandDTO);

        validateFarmLand(farmLandDTO);
        FarmLand farmLand = modelMapper.map(farmLandDTO, FarmLand.class);
        
        double acreToSquareFeet = 43560; // 1 acre = 43,560 square feet
        double centToSquareFeet = 435.6; // 1 cent = 435.6 square feet
        int cents = farmLand.getCents();
        int acres =farmLand.getApproxAcre();
        
        double totalAreaInSquareFeet = (acres * acreToSquareFeet) + (cents * centToSquareFeet);
        farmLand.setArea(totalAreaInSquareFeet);
        farmLandRepository.save(farmLand);

        logger.info("Exiting method: addFarmLand. FarmLand created successfully.");
        return "FarmLand created successfully";
    }

    @Override
    public List<FarmLand> getAllFarmLands() {
        logger.info("Entering method: getAllFarmLands");
        List<FarmLand> farmLands = farmLandRepository.findAll();
        logger.info("Exiting method: getAllFarmLands with result size: {}", farmLands.size());
        return farmLands;
    }

    @Override
    public Optional<FarmLand> getFarmLandById(String farmerId) {
        logger.info("Entering method: getFarmLandById with Farmer ID: {}", farmerId);
        Optional<FarmLand> farmLand = farmLandRepository.findById(farmerId);
        
        if (farmLand.isPresent()) {
            logger.info("FarmLand found with Farmer ID: {}", farmerId);
        } else {
            logger.warn("No FarmLand found with Farmer ID: {}", farmerId);
        }

        logger.info("Exiting method: getFarmLandById");
        return farmLand;
    }

    @Override
    public String updateFarmLand(String farmerId, FarmLandDTO farmLandDTO) {
        logger.info("Entering method: update {}: {} and data: {}",FARMLAND_WITH_ID, farmerId, farmLandDTO);

        Optional<FarmLand> existingFarmLand = farmLandRepository.findById(farmerId);

        if (!existingFarmLand.isPresent()) {
            logger.error("{} {} does not exist",FARMLAND_WITH_ID, farmerId);
            throw new IllegalArgumentException(FARMLAND_WITH_ID + farmerId + " does not exist.");
        }

        validateFarmLand(farmLandDTO);
        FarmLand farmLand = modelMapper.map(farmLandDTO, FarmLand.class);
        farmLand.setFarmerId(farmerId);
        double acreToSquareFeet = 43560; // 1 acre = 43,560 square feet
        double centToSquareFeet = 435.6; // 1 cent = 435.6 square feet
        int cents = farmLand.getCents();
        int acres =farmLand.getApproxAcre();
        
        double totalAreaInSquareFeet = (acres * acreToSquareFeet) + (cents * centToSquareFeet);
        farmLand.setArea(totalAreaInSquareFeet);
        farmLandRepository.save(farmLand);

        logger.info("Exiting method: updateFarmLand. FarmLand updated successfully with Farmer ID: {}", farmerId);
        return "FarmLand updated successfully";
    }

    @Override
    public String deleteFarmLand(String farmerId) {
        logger.info("Entering method: delete {}: {}",FARMLAND_WITH_ID, farmerId);

        Optional<FarmLand> existingFarmLand = farmLandRepository.findById(farmerId);

        if (!existingFarmLand.isPresent()) {
            logger.error("{} {} does not exist",FARMLAND_WITH_ID, farmerId);
            throw new IllegalArgumentException(FARMLAND_WITH_ID + farmerId + " does not exist.");
        }

        farmLandRepository.deleteById(farmerId);
        logger.info("Exiting method: deleteFarmLand. {} {} deleted successfully",FARMLAND_WITH_ID, farmerId);
        return FARMLAND_WITH_ID + farmerId + " deleted successfully";
    }

    private void validateFarmLand(FarmLandDTO farmLand) {
        logger.debug("Validating FarmLand: {}", farmLand);
        
        String name = farmLand.getName();
        if (name == null || name.trim().isEmpty()) {
            logger.error("Validation error: Name cannot be null or empty.");
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }

        Integer approxAcre = farmLand.getApproxAcre();
        if (approxAcre == null || approxAcre <= 0) {
            logger.error("Validation error: Approx Acre cannot be null or less than or equal to zero.");
            throw new IllegalArgumentException("Approx Acre cannot be null or less than or equal to zero.");
        }

        Integer cents = farmLand.getCents();
        if (cents == null || cents < 0) {
            logger.error("Validation error: Cents cannot be null or less than zero.");
            throw new IllegalArgumentException("Cents cannot be null or less than zero.");
        }

        logger.debug("Validation completed successfully for FarmLand: {}", farmLand);
    }
}
