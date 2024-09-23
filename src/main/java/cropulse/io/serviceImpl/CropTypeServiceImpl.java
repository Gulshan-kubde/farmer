package cropulse.io.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cropulse.io.dto.CropTypeDTO;
import cropulse.io.entity.CropType;
import cropulse.io.repository.CropTypeRepository;
import cropulse.io.service.CropTypeService;

@Service
public class CropTypeServiceImpl implements CropTypeService {

    @Autowired
    private CropTypeRepository cropTypeRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String addCropType(CropTypeDTO cropTypeDTO) {
        validateCropType(cropTypeDTO);
        CropType cropType = modelMapper.map(cropTypeDTO, CropType.class);
        
        
        cropTypeRepository.save(cropType);
        return "CropType created successfully.";
    }
    @Override
    public List<CropType> getAllCropTypes() {
        return cropTypeRepository.findAll();
    }

    
    @Override
    public Optional<CropType> getCropTypeById(String cropTypeId) {
        return cropTypeRepository.findById(cropTypeId);
    }

    @Override
    public String updateCropType(String cropTypeId, CropTypeDTO cropTypeDTO) {
        Optional<CropType> existingCropType = cropTypeRepository.findById(cropTypeId);

        if (!existingCropType.isPresent()) {
            throw new IllegalArgumentException("CropType with ID " + cropTypeId + " does not exist.");
        }

        validateCropType(cropTypeDTO);
        CropType cropType = modelMapper.map(cropTypeDTO, CropType.class);
        
        cropType.setCropTypeId(cropTypeId);
        cropTypeRepository.save(cropType);

        return "CropType updated successfully.";
    }
    @Override
    public String deleteCropType(String cropTypeId) {
        Optional<CropType> existingCropType = cropTypeRepository.findById(cropTypeId);

        if (!existingCropType.isPresent()) {
            throw new IllegalArgumentException("CropType with ID " + cropTypeId + " does not exist.");
        }

        cropTypeRepository.deleteById(cropTypeId);
        return "CropType with ID " + cropTypeId + " deleted successfully.";
    }

    private void validateCropType(CropTypeDTO cropType) {
        String cropName = cropType.getCropName();
        if (cropName == null || cropName.trim().isEmpty()) {
            throw new IllegalArgumentException("CropName cannot be null or empty.");
        }
    }
}
