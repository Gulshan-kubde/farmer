package cropulse.io.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cropulse.io.dto.CropPlanningDTO;
import cropulse.io.entity.CropPlanning;
import cropulse.io.repository.CropPlanningRepository;
import cropulse.io.service.CropPlanningService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CropPlanningServiceImpl implements CropPlanningService {

    @Autowired
    private CropPlanningRepository cropPlanningRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(CropPlanningServiceImpl.class);
    private static final String CROPPLANNING_WITH_ID = "CropPlanning with ID ";
    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public String addCropPlanning(CropPlanningDTO cropPlanningDTO) {
        logger.info("Entering method: addCropPlanning with parameters: {}", cropPlanningDTO);
        validateCropPlanning(cropPlanningDTO);
        
        CropPlanning cropPlanning = modelMapper.map(cropPlanningDTO, CropPlanning.class);
        cropPlanningRepository.save(cropPlanning);
        
        logger.info("Exiting method: addCropPlanning. CropPlanning created successfully.");
        return "CropPlanning created successfully.";
    }

    @Override
    public List<CropPlanning> getAllCropPlannings() {
        logger.info("Entering method: getAllCropPlannings");
        List<CropPlanning> cropPlannings = cropPlanningRepository.findAll();
        logger.info("Exiting method: getAllCropPlannings with result size: {}", cropPlannings.size());
        return cropPlannings;
    }
    
    @Override
    public Optional<CropPlanning> getCropPlanningById(String id) {
        logger.info("Entering method: getCropPlanningById with ID: {}", id);
        Optional<CropPlanning> cropPlanning = cropPlanningRepository.findById(id);
        
        if (cropPlanning.isPresent()) {
            logger.info("CropPlanning found with ID: {}", id);
        } else {
            logger.warn("No CropPlanning found with ID: {}", id);
        }
        
        logger.info("Exiting method: getCropPlanningById");
        return cropPlanning;
    }

    @Override
    public String updateCropPlanning(String id, CropPlanningDTO cropPlanningDTO) {
        logger.info("Entering method: updateCropPlanning with ID: {} and data: {}", id, cropPlanningDTO);
        
        Optional<CropPlanning> existingCropPlanning = cropPlanningRepository.findById(id);

        if (!existingCropPlanning.isPresent()) {
            logger.error("{} {} does not exist", CROPPLANNING_WITH_ID,id);
            throw new IllegalArgumentException(CROPPLANNING_WITH_ID + id + " does not exist.");
        }

        validateCropPlanning(cropPlanningDTO);
        
        CropPlanning cropPlanning = modelMapper.map(cropPlanningDTO, CropPlanning.class);
        cropPlanning.setId(id);
        cropPlanningRepository.save(cropPlanning);
        
        logger.info("Exiting method: updateCropPlanning. CropPlanning updated successfully with ID: {}", id);
        return "CropPlanning updated successfully.";
    }

    @Override
    public String deleteCropPlanning(String id) {
        logger.info("Entering method: deleteCropPlanning with ID: {}", id);
        
        Optional<CropPlanning> existingCropPlanning = cropPlanningRepository.findById(id);

        if (!existingCropPlanning.isPresent()) {
            logger.error("{} {} does not exist",CROPPLANNING_WITH_ID,id);
            throw new IllegalArgumentException(CROPPLANNING_WITH_ID + id + " does not exist.");
        }

        cropPlanningRepository.deleteById(id);
        logger.info("Exiting method: deleteCropPlanning. {} {} deleted successfully",CROPPLANNING_WITH_ID, id);
        return CROPPLANNING_WITH_ID + id + " deleted successfully.";
    }

    private void validateCropPlanning(CropPlanningDTO cropPlanning) {
        logger.debug("Validating CropPlanning: {}", cropPlanning);
        if (cropPlanning.getPlot() == null || cropPlanning.getPlot().trim().isEmpty()) {
            logger.error("Validation error: Plot cannot be null or empty.");
            throw new IllegalArgumentException("Plot cannot be null or empty.");
        }
        
       
        logger.debug("Validation completed successfully for CropPlanning: {}", cropPlanning);
    }
}
