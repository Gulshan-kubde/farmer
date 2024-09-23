package cropulse.io.service;

import java.util.List;
import java.util.Optional;

import cropulse.io.dto.CropPlanningDTO;
import cropulse.io.entity.CropPlanning;

public interface CropPlanningService {

    String addCropPlanning(CropPlanningDTO cropPlanning);

    List<CropPlanning> getAllCropPlannings();

    Optional<CropPlanning> getCropPlanningById(String id);

    String updateCropPlanning(String id, CropPlanningDTO cropPlanningDTO);

    String deleteCropPlanning(String id);

}
