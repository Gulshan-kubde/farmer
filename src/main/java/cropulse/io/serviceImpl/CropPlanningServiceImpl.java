package cropulse.io.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cropulse.io.dto.CropPlanningDTO;
import cropulse.io.entity.CropPlanning;
import cropulse.io.repository.CropPlanningRepository;
import cropulse.io.service.CropPlanningService;
import org.modelmapper.ModelMapper;
@Service
public class CropPlanningServiceImpl implements CropPlanningService {

    @Autowired
    private CropPlanningRepository cropPlanningRepository;
    
    
    @Autowired
    private ModelMapper modelMapper;
    
    
    @Override
    public String addCropPlanning(CropPlanningDTO cropPlanningDTO) {
        validateCropPlanning(cropPlanningDTO);
        
        CropPlanning cropPlanning = modelMapper.map(cropPlanningDTO, CropPlanning.class);
       
        cropPlanningRepository.save(cropPlanning);
        
        return "CropPlanning created successfully.";
    }

    @Override
    public List<CropPlanning> getAllCropPlannings() {
        return cropPlanningRepository.findAll();
    }
    @Override
    public Optional<CropPlanning> getCropPlanningById(String id) {
        return cropPlanningRepository.findById(id);
    }

    @Override
    public String updateCropPlanning(String id, CropPlanningDTO cropPlanningDTO) {
        Optional<CropPlanning> existingCropPlanning = cropPlanningRepository.findById(id);

        if (!existingCropPlanning.isPresent()) {
            throw new IllegalArgumentException("CropPlanning with ID " + id + " does not exist.");
        }

        validateCropPlanning(cropPlanningDTO);
        
        CropPlanning cropPlanning = modelMapper.map(cropPlanningDTO, CropPlanning.class);
        
        cropPlanning.setId(id);
        cropPlanningRepository.save(cropPlanning);

        return "CropPlanning updated successfully.";
    }

    @Override
    public String deleteCropPlanning(String id) {
        Optional<CropPlanning> existingCropPlanning = cropPlanningRepository.findById(id);

        if (!existingCropPlanning.isPresent()) {
            throw new IllegalArgumentException("CropPlanning with ID " + id + " does not exist.");
        }

        cropPlanningRepository.deleteById(id);
        return "CropPlanning with ID " + id + " deleted successfully.";
    }

   
    private void validateCropPlanning(CropPlanningDTO cropPlanning) {
        if (cropPlanning.getPlot() == null || cropPlanning.getPlot().trim().isEmpty()) {
            throw new IllegalArgumentException("Plot cannot be null or empty.");
        }

        if (cropPlanning.getCropType() == null || cropPlanning.getCropType().trim().isEmpty()) {
            throw new IllegalArgumentException("CropType cannot be null or empty.");
        }

        if (cropPlanning.getSowingDate() == null) {
            throw new IllegalArgumentException("Sowing Date cannot be null.");
        }

        if (cropPlanning.getYield() == null || cropPlanning.getYield().trim().isEmpty()) {
            throw new IllegalArgumentException("Yield cannot be null or empty.");
        }

        if (cropPlanning.getSeedsUsed() == null || cropPlanning.getSeedsUsed().trim().isEmpty()) {
            throw new IllegalArgumentException("Seeds Used cannot be null or empty.");
        }

        if (cropPlanning.getRevenue() == null || cropPlanning.getRevenue().trim().isEmpty()) {
            throw new IllegalArgumentException("Revenue cannot be null or empty.");
        }

        if (cropPlanning.getCultivation() == null || cropPlanning.getCultivation().trim().isEmpty()) {
            throw new IllegalArgumentException("Cultivation cannot be null or empty.");
        }
    }
}
