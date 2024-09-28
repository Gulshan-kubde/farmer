package cropulse.io.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cropulse.io.dto.CropTypeDTO;
import cropulse.io.entity.CropType;
import cropulse.io.repository.CropTypeRepository;
import cropulse.io.service.CropTypeService;

@Service
public class CropTypeServiceImpl implements CropTypeService {

    private static final Logger logger = LoggerFactory.getLogger(CropTypeServiceImpl.class);

    @Autowired
    private CropTypeRepository cropTypeRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    private static final String CROPTYPE_WITH_ID = "CropType with ID ";
    
    @Override
    public String addCropType(CropTypeDTO cropTypeDTO) {
        logger.info("Entering method: addCropType with data: {}", cropTypeDTO);
        validateCropType(cropTypeDTO);
        CropType cropType = modelMapper.map(cropTypeDTO, CropType.class);
        
        cropTypeRepository.save(cropType);
        logger.info("Exiting method: addCropType. CropType created successfully.");
        return "CropType created successfully.";
    }
    
    @Override
    public List<CropType> getAllCropTypes() {
        logger.info("Entering method: getAllCropTypes");
        List<CropType> cropTypes = cropTypeRepository.findAll();
        logger.info("Exiting method: getAllCropTypes with result size: {}", cropTypes.size());
        return cropTypes;
    }

    @Override
    public Optional<CropType> getCropTypeById(String cropTypeId) {
        logger.info("Entering method: getCropTypeById with ID: {}", cropTypeId);
        Optional<CropType> cropType = cropTypeRepository.findById(cropTypeId);
        
        if (cropType.isPresent()) {
            logger.info("CropType found with ID: {}", cropTypeId);
        } else {
            logger.warn("No CropType found with ID: {}", cropTypeId);
        }
        
        logger.info("Exiting method: getCropTypeById");
        return cropType;
    }

    @Override
    public String updateCropType(String cropTypeId, CropTypeDTO cropTypeDTO) {
        logger.info("Entering method: update (): {} and data: {}", CROPTYPE_WITH_ID, cropTypeId, cropTypeDTO);
        
        Optional<CropType> existingCropType = cropTypeRepository.findById(cropTypeId);

        if (!existingCropType.isPresent()) {
            logger.error("{} {} does not exist",CROPTYPE_WITH_ID, cropTypeId);
            throw new IllegalArgumentException(CROPTYPE_WITH_ID + cropTypeId + " does not exist.");
        }

        validateCropType(cropTypeDTO);
        CropType cropType = modelMapper.map(cropTypeDTO, CropType.class);
        
        cropType.setCropTypeId(cropTypeId);
        cropTypeRepository.save(cropType);
        
        logger.info("Exiting method: updateCropType. CropType updated successfully with ID: {}", cropTypeId);
        return "CropType updated successfully.";
    }

    @Override
    public String deleteCropType(String cropTypeId) {
        logger.info("Entering method: delete {} : {}",CROPTYPE_WITH_ID, cropTypeId);
        
        Optional<CropType> existingCropType = cropTypeRepository.findById(cropTypeId);

        if (!existingCropType.isPresent()) {
            logger.error("{} {} does not exist",CROPTYPE_WITH_ID, cropTypeId);
            throw new IllegalArgumentException(CROPTYPE_WITH_ID + cropTypeId + " does not exist.");
        }

        cropTypeRepository.deleteById(cropTypeId);
        logger.info("Exiting method: deleteCropType. {} {} deleted successfully",CROPTYPE_WITH_ID, cropTypeId);
        return CROPTYPE_WITH_ID + cropTypeId + " deleted successfully.";
    }

    private void validateCropType(CropTypeDTO cropType) {
        logger.debug("Validating CropType: {}", cropType);
        String cropName = cropType.getCropName();
        if (cropName == null || cropName.trim().isEmpty()) {
            logger.error("Validation error: CropName cannot be null or empty.");
            throw new IllegalArgumentException("CropName cannot be null or empty.");
        }
        logger.debug("Validation completed successfully for CropType: {}", cropType);
    }
}
